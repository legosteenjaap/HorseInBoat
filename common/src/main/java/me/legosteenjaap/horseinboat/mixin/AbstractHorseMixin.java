package me.legosteenjaap.horseinboat.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Animal {

    //@Shadow public abstract void travel(Vec3 movementInput);

    protected AbstractHorseMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public AABB getBoundingBox() {
        if (this.isPassenger() && this.getVehicle() instanceof Boat) {
            if (this.getVehicle().getPassengers().size() == 1) {
                AABB box = super.getBoundingBox();
                return box.setMinY(box.minY + this.getVehicle().getBbHeight());
            } else {
                AABB box = super.getBoundingBox();
                AABB changedBox = box.setMinY(box.minY + this.getVehicle().getBbHeight());
                changedBox = changedBox.setMaxX(box.maxX - 0.30);
                changedBox = changedBox.setMinX(box.minX + 0.30);
                changedBox = changedBox.setMaxZ(box.maxZ - 0.30);
                changedBox = changedBox.setMinZ(box.minZ + 0.30);
                changedBox = changedBox.setMaxY(box.maxY + 1.1);
                return changedBox;
            }
        }
        return super.getBoundingBox();
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        if (this.isPassenger() && this.getVehicle() instanceof Boat ) {
            if (this.getVehicle().getPassengers().size() == 1) {
                EntityDimensions dim = super.getDimensions(pose);
                return EntityDimensions.scalable(dim.width, dim.height - this.getVehicle().getBbHeight());
            } else {
                EntityDimensions dim = super.getDimensions(pose);
                return EntityDimensions.scalable(dim.width - 0.60f, dim.height - this.getVehicle().getBbHeight());
            }
        }
        return super.getDimensions(pose);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (this.isPassenger() && this.isPassenger() && this.getVehicle() instanceof Boat && this.getVehicle().getPassengers().size() == 2) {
            this.ejectPassengers();
        }
    }

    @Inject(method= "doPlayerRide", at = @At("HEAD"), cancellable = true)
    protected void doPlayerRide(Player player, CallbackInfo ci) {
        if (this.isPassenger() && this.getVehicle() instanceof Boat && this.getVehicle().getPassengers().size() == 2) ci.cancel();
    }

    @Inject(method = "handleStartJump", at = @At("HEAD"))
    public void handleStartJump(int height, CallbackInfo ci) {
        if (this.isPassenger()) this.stopRiding();
    }

    @Inject(method = "isStanding", at = @At("RETURN"), cancellable = true)
    public void isStanding(CallbackInfoReturnable<Boolean> cir) {
        if (this.isPassenger() && this.getVehicle() instanceof Boat) cir.setReturnValue(false);
    }

    @Inject(method = "isEating", at = @At("RETURN"), cancellable = true)
    public void isEating(CallbackInfoReturnable<Boolean> cir) {
        if (this.isPassenger() && this.getVehicle() instanceof Boat) cir.setReturnValue(false);
    }

    @Override
    public float getEyeHeight() {
        if (this.isPassenger() && this.getVehicle() instanceof Boat && this.getVehicle().getPassengers().size() == 2) return super.getEyeHeight() + 1f;
        return super.getEyeHeight();
    }

}
