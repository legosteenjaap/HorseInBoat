package me.legosteenjaap.horseinboat.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Boat.class)
public class BoatMixin {

	@Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getBbWidth()F", ordinal = 0))
	private float makeExceptionForHorse(Entity entity) {
		if (entity instanceof AbstractHorse) return 0;
		return entity.getBbWidth();
	}

}

