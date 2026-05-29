package me.legosteenjaap.horseinboat.mixin;

import me.legosteenjaap.horseinboat.rendering.IBoatRenderState;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.state.EquineRenderState;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.AbstractChestBoat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorseRenderer.class)
public class AbstractHorseRendererMixin {

    @Inject(
        method = "extractRenderState(Lnet/minecraft/world/entity/animal/equine/AbstractHorse;Lnet/minecraft/client/renderer/entity/state/EquineRenderState;F)V",
        at = @At("RETURN")
    )
    private <T extends AbstractHorse, S extends EquineRenderState> void injectExtractRenderState(
            T horse, S state, float partialTick, CallbackInfo ci) {
        boolean inBoat = horse.isPassenger() && horse.getVehicle() instanceof AbstractBoat;
        boolean has2Passengers = inBoat && horse.getVehicle().getPassengers().size() == 2;
        boolean isChestBoat = inBoat && horse.getVehicle() instanceof AbstractChestBoat
                && ((AbstractBoat) horse.getVehicle()).getMaxPassengers() == 1;
        ((IBoatRenderState) state).horseinboat$setBoatInfo(inBoat, has2Passengers, isChestBoat);
    }
}
