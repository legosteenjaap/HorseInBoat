package me.legosteenjaap.horseinboat.mixin;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(HorseBaseEntity.class)
public abstract class HorseBaseEntityMixin extends AnimalEntity {

    @Shadow public abstract boolean isEatingGrass();

    protected HorseBaseEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Box getBoundingBox() {
        if (this.hasVehicle() && this.getVehicle() instanceof BoatEntity ) {
            if (this.getVehicle().getPassengerList().size() == 1) {
                Box box = super.getBoundingBox();
                Box changedBox = box.withMinY(box.minY + this.getVehicle().getHeight());
                return changedBox;
            } else {
                Box box = super.getBoundingBox();
                Box changedBox = box.withMinY(box.minY + this.getVehicle().getHeight());
                changedBox = changedBox.withMaxX(box.maxX - 0.30);
                changedBox = changedBox.withMinX(box.minX + 0.30);
                changedBox = changedBox.withMaxZ(box.maxZ - 0.30);
                changedBox = changedBox.withMinZ(box.minZ + 0.30);
                changedBox = changedBox.withMaxY(box.maxY + 1.1);
                return changedBox;
            }
        }
        return super.getBoundingBox();
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void tick(CallbackInfo ci) {
        if (this.hasPassengers() && this.hasVehicle() && this.getVehicle() instanceof BoatEntity && this.getVehicle().getPassengerList().size() == 2) this.removeAllPassengers();
    }

    @Inject(method= "putPlayerOnBack", at = @At("HEAD"), cancellable = true)
    protected void putPlayerOnBack(PlayerEntity player, CallbackInfo ci) {
        if (this.hasVehicle() && this.getVehicle() instanceof BoatEntity && this.getVehicle().getPassengerList().size() == 2) ci.cancel();
    }

    @Inject(method = "startJumping", at = @At("HEAD"))
    public void startJumping(int height, CallbackInfo ci) {
        if (this.hasVehicle()) this.stopRiding();
    }

    @Inject(method = "isAngry", at = @At("RETURN"), cancellable = true)
    public void isAngry(CallbackInfoReturnable<Boolean> cir) {
        if (this.hasVehicle() && this.getVehicle() instanceof BoatEntity) cir.setReturnValue(false);
    }

    @Override
    public float getStandingEyeHeight() {
        if (this.hasVehicle() && this.getVehicle() instanceof BoatEntity && this.getVehicle().getPassengerList().size() == 2) return super.getStandingEyeHeight() + 1f;
        return super.getStandingEyeHeight();
    }

}
