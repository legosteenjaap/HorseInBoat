package me.legosteenjaap.horseinboat.mixin;

import com.mojang.math.Constants;
import me.legosteenjaap.horseinboat.rendering.IBoatRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.animal.equine.AbstractEquineModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.EquineRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractEquineModel.class)
public abstract class HorseModelMixin<T extends EquineRenderState> extends EntityModel<T> {

    @Unique float legHindRollRotBoat = Constants.PI * -0.7f;
    @Unique float legHindyRotRotBoat = Constants.PI * 0.1f;
    @Unique float legHindYBoat = 19.25f;
    @Unique float legHindZBoat = -1f;

    @Unique float legFrontRollRotBoat = Constants.PI * -0.25f;
    @Unique float legFrontyRotRotBoat = Constants.PI * 0f;
    @Unique float legFrontYBoat = 0.25f;
    @Unique float legFrontZBoat = -1.75f;

    @Unique float bodyRotBoat = Constants.PI * -0.5f;
    @Unique float bodyYBoat = 15.25f;
    @Unique float bodyZBoat = -1f;

    @Unique float headYBoat = -1.75f;
    @Unique float headZBoat = 0.5f;
    @Unique float headYRotBoat = Constants.PI * 0f;
    @Unique float headXRotBoat = 0.5235988f;

    @Unique boolean updatedToNormalModel = true;

    @Shadow @Final protected ModelPart rightFrontLeg;
    @Shadow @Final protected ModelPart leftFrontLeg;
    @Shadow @Final protected ModelPart rightHindLeg;
    @Shadow @Final protected ModelPart leftHindLeg;
    @Shadow @Final protected ModelPart body;
    @Shadow @Final protected ModelPart headParts;
    @Shadow @Final private ModelPart tail;

    protected HorseModelMixin(net.minecraft.client.model.geom.ModelPart root) {
        super(root);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/EquineRenderState;)V", at = @At("RETURN"))
    public void setupAnim(T state, CallbackInfo ci) {
        if (state.isBaby) return;

        IBoatRenderState boatState = (IBoatRenderState) state;
        boolean inBoat = boatState.horseinboat$isInBoat();
        boolean has2Passengers = boatState.horseinboat$boatHas2Passengers();
        boolean isChestBoat = boatState.horseinboat$boatIsChestBoat();

        if (inBoat && (isChestBoat || has2Passengers)) {
            // HEAD
            this.headParts.y = headYBoat;
            this.headParts.z = headZBoat;

            // BODY
            this.body.y = bodyYBoat;
            this.body.z = bodyZBoat;
            this.body.xRot = bodyRotBoat;

            // HIND LEG
            this.leftHindLeg.xRot = legHindRollRotBoat;
            this.rightHindLeg.xRot = legHindRollRotBoat;
            this.leftHindLeg.yRot = -legHindyRotRotBoat;
            this.rightHindLeg.yRot = legHindyRotRotBoat;
            leftHindLeg.y = legHindYBoat;
            rightHindLeg.y = legHindYBoat;
            leftHindLeg.z = legHindZBoat;
            rightHindLeg.z = legHindZBoat;

            // FRONT LEG
            this.leftFrontLeg.xRot = legFrontRollRotBoat;
            this.rightFrontLeg.xRot = legFrontRollRotBoat;
            this.leftFrontLeg.yRot = -legFrontyRotRotBoat;
            this.rightFrontLeg.yRot = legFrontyRotRotBoat;
            leftFrontLeg.y = legFrontYBoat;
            rightFrontLeg.y = legFrontYBoat;
            leftFrontLeg.z = legFrontZBoat;
            rightFrontLeg.z = legFrontZBoat;

            // TAIL
            this.tail.visible = false;

            // Extra body.y adjustment when a player is also in the boat
            if (has2Passengers) {
                this.body.y = bodyYBoat;
            }

            // Disable head animations while in a boat
            this.headParts.xRot = headXRotBoat;
            this.headParts.yRot = headYRotBoat;

            updatedToNormalModel = false;
        } else if (!updatedToNormalModel) {
            // Restore defaults
            this.headParts.y = 4.0f;
            this.headParts.z = -12.0f;

            this.body.y = 0.0f;
            this.body.z = 5.0f;
            this.body.xRot = 0.0f;

            this.leftHindLeg.yRot = 0;
            this.rightHindLeg.yRot = 0;
            leftHindLeg.y = rightHindLeg.y = 14.0f;
            leftHindLeg.z = rightHindLeg.z = 7.0f;

            this.leftFrontLeg.yRot = 0;
            this.rightFrontLeg.yRot = 0;
            leftFrontLeg.y = rightFrontLeg.y = 14.0f;
            leftFrontLeg.z = rightFrontLeg.z = -10.0f;

            this.tail.visible = true;

            updatedToNormalModel = true;
        } else if (inBoat) {
            // In boat but not chest boat and not 2 passengers — disable head animations
            this.headParts.xRot = headXRotBoat;
            this.headParts.yRot = headYRotBoat;
        }
    }
}
