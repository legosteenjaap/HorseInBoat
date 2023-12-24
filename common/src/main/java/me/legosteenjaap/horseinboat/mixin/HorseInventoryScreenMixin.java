package me.legosteenjaap.horseinboat.mixin;

import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(HorseInventoryScreen.class)
public class HorseInventoryScreenMixin {

    @Shadow @Final
    private AbstractHorse horse;

    @ModifyConstant(method = "renderBg", constant = @Constant(intValue = 70, ordinal = 0))
    private int changeModelPos(int value) {
        if (!(horse instanceof Llama) && horse.isPassenger() && horse.getVehicle() instanceof Boat && horse.getVehicle().getPassengers().size() == 2) return 79;
        return value;
    }

}
