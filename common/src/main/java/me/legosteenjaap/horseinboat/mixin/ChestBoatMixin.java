package me.legosteenjaap.horseinboat.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBoat.class)
public class ChestBoatMixin extends Boat {

    public ChestBoatMixin(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "getSinglePassengerXOffset", at = @At("HEAD"), cancellable = true)
    protected void getSinglePassengerXOffset(CallbackInfoReturnable<Float> cir) {
        if (this.getPassengers().size() == 1 && this.getPassengers().get(0) instanceof AbstractHorse) cir.setReturnValue(0.4f);
    }


}
