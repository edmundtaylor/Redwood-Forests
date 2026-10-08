package net.biscuits310.redwoodforests.entity;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.entity.custom.BananaSlugEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.createEntities(RedwoodForests.MODID);

    public static final ResourceKey<EntityType<?>> BANANA_SLUG_KEY =
            ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(RedwoodForests.MODID, "banana_slug"));

    public static final Supplier<EntityType<BananaSlugEntity>> BANANA_SLUG = ENTITY_TYPES.register("banana_slug",
            () -> EntityType.Builder.of(BananaSlugEntity::new, MobCategory.CREATURE).sized(0.5f, 0.5f).build(BANANA_SLUG_KEY));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
