package me.legosteenjaap.horseinboat.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.AbstractChestBoat;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractChestBoat.class)
public abstract class ChestBoatMixin extends AbstractBoat {

    protected ChestBoatMixin(EntityType<? extends AbstractBoat> entityType, Level level) {
        super(entityType, level, null);
    }

    @Inject(method = "getSinglePassengerXOffset", at = @At("HEAD"), cancellable = true)
    protected void getSinglePassengerXOffset(CallbackInfoReturnable<Float> cir) {
        if (this.getPassengers().size() == 1 && this.getPassengers().get(0) instanceof AbstractHorse) cir.setReturnValue(0.4f);
    }

}
