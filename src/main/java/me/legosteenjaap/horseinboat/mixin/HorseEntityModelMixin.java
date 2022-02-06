package me.legosteenjaap.horseinboat.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.MathConstants;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseEntityModel.class)
public abstract class HorseEntityModelMixin <T extends HorseBaseEntity> extends AnimalModel<T> {

    float legHindRollRotBoat = MathConstants.PI * -0.7f;
    float legHindYawRotBoat = MathConstants.PI * 0.1f;
    float legHindYBoat = 17f;
    float legHindZBoat = -1f;

    float legFrontRollRotBoat = MathConstants.PI * -0.25f;
    float legFrontYawRotBoat = MathConstants.PI * 0f;
    float legFrontYBoat = -2f;
    float legFrontZBoat = -1.75f;

    float bodyRotBoat = MathConstants.PI * -0.5f;
    float bodyYBoat = 13f;
    float bodyZBoat = -1f;

    float headYBoat = -4f;
    float headZBoat = 0.5f;
    float headPitchRotBoat = MathConstants.PI * 0.25f;
    float headYawRotBoat = MathConstants.PI * 0f;

    @Shadow @Final
    private ModelPart rightFrontLeg;
    @Shadow @Final
    private ModelPart leftFrontLeg;
    @Shadow @Final
    private ModelPart rightHindLeg;
    @Shadow @Final
    private ModelPart leftHindLeg;
    @Shadow @Final
    protected ModelPart body;
    @Shadow @Final
    protected ModelPart head;
    @Shadow @Final
    private ModelPart tail;

    @Inject(method = "setAngles(Lnet/minecraft/entity/passive/HorseBaseEntity;FFFFF)V", at = @At("RETURN"))
    public void setAngles(T horseBaseEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        BoatEntity boat = null;
        if (!horseBaseEntity.isBaby() && horseBaseEntity.hasVehicle() && horseBaseEntity.getVehicle() instanceof BoatEntity) boat = (BoatEntity)horseBaseEntity.getVehicle();
        if (boat != null && boat.getPassengerList().size() == 2) {
            this.body.pivotY =  bodyYBoat;
        }
    }

    @Inject(method = "animateModel(Lnet/minecraft/entity/passive/HorseBaseEntity;FFF)V", at = @At("RETURN"))
    public void animateModel(T horseBaseEntity, float f, float g, float h, CallbackInfo ci) {
        BoatEntity boat = null;
        if (!horseBaseEntity.isBaby() && horseBaseEntity.hasVehicle() && horseBaseEntity.getVehicle() instanceof BoatEntity) boat = (BoatEntity)horseBaseEntity.getVehicle();
        if (!horseBaseEntity.isBaby() && boat != null && boat.getPassengerList().size() == 2) {
            //HEAD
            this.head.pivotY = headYBoat;
            this.head.pivotZ = headZBoat;
            //this.head.pitch = headPitchRotBoat;

            //BODY
            this.body.pivotY = bodyYBoat;
            this.body.pivotZ = bodyZBoat;
            this.body.pitch = bodyRotBoat;

            //HIND LEG
            this.leftHindLeg.pitch = legHindRollRotBoat;
            this.rightHindLeg.pitch = legHindRollRotBoat;
            this.leftHindLeg.yaw = -legHindYawRotBoat;
            this.rightHindLeg.yaw = legHindYawRotBoat;
            leftHindLeg.pivotY = legHindYBoat;
            rightHindLeg.pivotY = legHindYBoat;
            leftHindLeg.pivotZ = legHindZBoat;
            rightHindLeg.pivotZ = legHindZBoat;

            //FRONT LEG
            this.leftFrontLeg.pitch = legFrontRollRotBoat;
            this.rightFrontLeg.pitch = legFrontRollRotBoat;
            this.leftFrontLeg.yaw = -legFrontYawRotBoat;
            this.rightFrontLeg.yaw = legFrontYawRotBoat;
            leftFrontLeg.pivotY = legFrontYBoat;
            rightFrontLeg.pivotY = legFrontYBoat;
            leftFrontLeg.pivotZ = legFrontZBoat;
            rightFrontLeg.pivotZ = legFrontZBoat;

            //TAIL
            this.tail.visible = false;
        } else {
            //HEAD
            this.head.pivotY = 4.0f;
            this.head.pivotZ = -12.0f;

            //BODY
            this.body.pivotY =0.0f;
            this.body.pivotZ = 5.0f;

            //HIND LEG
            this.leftHindLeg.yaw = 0;
            this.rightHindLeg.yaw = 0;
            leftHindLeg.pivotY = 14.0f;
            rightHindLeg.pivotY = 14.0f;
            leftHindLeg.pivotZ = 7.0f;
            rightHindLeg.pivotZ = 7.0f;

            //FRONT LEG
            this.leftFrontLeg.yaw = 0;
            this.rightFrontLeg.yaw = 0;
            leftFrontLeg.pivotY = 14.0f;
            rightFrontLeg.pivotY = 14.0f;
            leftFrontLeg.pivotZ = -10.0f;
            rightFrontLeg.pivotZ = -10.0f;

            //TAIL
            this.tail.visible = true;
        }
    }

}
