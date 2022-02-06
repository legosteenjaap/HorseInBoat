package me.legosteenjaap.horseinboat.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.mob.MobEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LookAtEntityGoal.class)
public class LookAtEntityGoalMixin {

    @Shadow @Final
    protected MobEntity mob;

    @Shadow @Final @Nullable
    protected Entity target;

    @Inject(method = "canStart", at = @At("RETURN"), cancellable = true)
    public void canStart(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
        //if (this.target != null && this.mob.hasVehicle() && this.mob.getVehicle().equals(this.target.getVehicle())) cir.setReturnValue(false);
    }

    @Inject(method = "shouldContinue", at = @At("RETURN"), cancellable = true)
    public void shouldContinue(CallbackInfoReturnable<Boolean> cir) {
        if (this.mob.hasVehicle() && this.mob.getVehicle().equals(this.target.getVehicle())) cir.setReturnValue(false);
    }

}
