package net.biscuits310.redwoodforests.entity.client;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class BananaSlugModel extends EntityModel<BananaSlugRenderState> {
    private final ModelPart controller;
    private final ModelPart base;
    private final ModelPart head;
    private final ModelPart leftUpperAppendage;
    private final ModelPart leftLowerAppendage;
    private final ModelPart rightLowerAppendage;
    private final ModelPart rightUpperAppendage;

    private final KeyframeAnimation walkingAnimation;
    private final KeyframeAnimation idlingAnimation;

    public BananaSlugModel(ModelPart root) {
        super(root);
        this.controller = root.getChild("controller");
        this.base = this.controller.getChild("base");
        this.head = this.controller.getChild("head");
        this.leftUpperAppendage = this.head.getChild("leftUpperAppendage");
        this.leftLowerAppendage = this.head.getChild("leftLowerAppendage");
        this.rightLowerAppendage = this.head.getChild("rightLowerAppendage");
        this.rightUpperAppendage = this.head.getChild("rightUpperAppendage");

        this.walkingAnimation = BananaSlugAnimations.Crawl.bake(root);
        this.idlingAnimation = BananaSlugAnimations.Idle.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition controller = partdefinition.addOrReplaceChild("controller", CubeListBuilder.create(), PartPose.offset(0.0F, 24.1305F, -4.0086F));

        PartDefinition base = controller.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = controller.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 21).addBox(-3.0F, -6.0F, -6.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition leftUpperAppendage = head.addOrReplaceChild("leftUpperAppendage", CubeListBuilder.create(), PartPose.offset(1.0F, -6.7318F, -4.1655F));

        PartDefinition leftUpperAppendage_r1 = leftUpperAppendage.addOrReplaceChild("leftUpperAppendage_r1", CubeListBuilder.create().texOffs(24, 21).addBox(0.0F, -5.0F, 0.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.211F, 0.056F, 0.2559F));

        PartDefinition leftLowerAppendage = head.addOrReplaceChild("leftLowerAppendage", CubeListBuilder.create(), PartPose.offset(1.0F, -3.7832F, -4.9487F));

        PartDefinition leftLowerAppendage_r1 = leftLowerAppendage.addOrReplaceChild("leftLowerAppendage_r1", CubeListBuilder.create().texOffs(28, 21).addBox(0.0F, 0.0F, 0.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.9384F, -0.4626F, -0.066F));

        PartDefinition rightLowerAppendage = head.addOrReplaceChild("rightLowerAppendage", CubeListBuilder.create(), PartPose.offset(-1.0F, -3.7832F, -4.9487F));

        PartDefinition rightLowerAppendage_r1 = rightLowerAppendage.addOrReplaceChild("rightLowerAppendage_r1", CubeListBuilder.create().texOffs(28, 27).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.9384F, 0.4626F, 0.066F));

        PartDefinition rightUpperAppendage = head.addOrReplaceChild("rightUpperAppendage", CubeListBuilder.create(), PartPose.offset(-1.0F, -6.7318F, -4.1655F));

        PartDefinition rightUpperAppendage_r1 = rightUpperAppendage.addOrReplaceChild("rightUpperAppendage_r1", CubeListBuilder.create().texOffs(24, 27).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.211F, -0.056F, -0.2559F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(BananaSlugRenderState state) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(state.yRot, state.xRot);

        this.idlingAnimation.apply(state.idleAnimationState, state.ageInTicks, 1f);
        this.walkingAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 10f, 100f);
    }

    private void applyHeadRotation(float headYaw, float headPitch){
        headYaw = Mth.clamp(headYaw, -20f, 20f);
        headPitch = Mth.clamp(headPitch, -20f, 20f);

        this.head.yRot = headYaw * ((float)Mth.PI / 180f);
        this.head.xRot = headPitch * ((float)Mth.PI / 180f);
    }
}
