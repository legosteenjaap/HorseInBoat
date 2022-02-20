package me.legosteenjaap.horseinboat.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
    import org.spongepowered.asm.mixin.injection.At;
    import org.spongepowered.asm.mixin.injection.Inject;
    import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {Horse.class, ZombieHorse.class, SkeletonHorse.class, AbstractChestedHorse.class})
public class HorseMixin extends AbstractHorse {

    protected HorseMixin(EntityType<? extends AbstractHorse> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    public void mobInteract(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (!(player.isSecondaryUseActive() && this.isTamed()) && this.isPassenger() && this.getVehicle() instanceof Boat && this.getVehicle().getPassengers().size() == 2) cir.setReturnValue(InteractionResult.FAIL);
    }

}
