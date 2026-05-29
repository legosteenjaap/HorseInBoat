package me.legosteenjaap.horseinboat.mixin;

import me.legosteenjaap.horseinboat.rendering.IBoatRenderState;
import net.minecraft.client.renderer.entity.state.EquineRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EquineRenderState.class)
public class EquineRenderStateMixin implements IBoatRenderState {

    @Unique private boolean horseinboat$inBoat = false;
    @Unique private boolean horseinboat$has2Passengers = false;
    @Unique private boolean horseinboat$isChestBoat = false;

    @Override
    public boolean horseinboat$isInBoat() { return horseinboat$inBoat; }

    @Override
    public boolean horseinboat$boatHas2Passengers() { return horseinboat$has2Passengers; }

    @Override
    public boolean horseinboat$boatIsChestBoat() { return horseinboat$isChestBoat; }

    @Override
    public void horseinboat$setBoatInfo(boolean inBoat, boolean has2Passengers, boolean isChestBoat) {
        this.horseinboat$inBoat = inBoat;
        this.horseinboat$has2Passengers = has2Passengers;
        this.horseinboat$isChestBoat = isChestBoat;
    }
}
