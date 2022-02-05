package me.legosteenjaap.horseinboat.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;


@Mixin(HorseBaseEntity.class)
public abstract class HorseBaseEntityMixin extends AnimalEntity {

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
                return changedBox;
            }
        }
        return super.getBoundingBox();
    }

}
