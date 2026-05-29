package me.legosteenjaap.horseinboat.mixin;

import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;

// HorseInventoryScreen rendering was refactored in 26.1 (renderBg replaced by extractBackground/extractRenderState).
// The cosmetic position adjustment for the horse model in the inventory screen is disabled until
// the new rendering API is understood.
@Mixin(HorseInventoryScreen.class)
public class HorseInventoryScreenMixin {
}
