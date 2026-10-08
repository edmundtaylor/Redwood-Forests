package net.biscuits310.redwoodforests.entity.client;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.entity.custom.BananaSlugEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class BananaSlugRenderer extends MobRenderer<BananaSlugEntity, BananaSlugRenderState, BananaSlugModel> {
    public BananaSlugRenderer(EntityRendererProvider.Context context) {
        super(context, new BananaSlugModel(context.bakeLayer(ModModelLayerLocations.BANANA_SLUG)), 0.25f);
    }

    @Override
    public Identifier getTextureLocation(BananaSlugRenderState state) {
        return Identifier.fromNamespaceAndPath(RedwoodForests.MODID, "textures/entity/banana_slug/banana_slug.png");
    }

    @Override
    public BananaSlugRenderState createRenderState() {
        return new BananaSlugRenderState();
    }

    @Override
    public void extractRenderState(BananaSlugEntity entity, BananaSlugRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.idleAnimationState.copyFrom(entity.idleAnimationState);
    }
}
