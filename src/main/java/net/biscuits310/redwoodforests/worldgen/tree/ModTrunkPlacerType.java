package net.biscuits310.redwoodforests.worldgen.tree;

import com.mojang.serialization.MapCodec;
import net.biscuits310.redwoodforests.RedwoodForests;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTrunkPlacerType{
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES =
            DeferredRegister.create(BuiltInRegistries.TRUNK_PLACER_TYPE.key(), RedwoodForests.MODID);

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<ModFenceTrunkPlacer>> FENCE_TRUNK_PLACER
            = TRUNK_PLACER_TYPES.register("fence_trunk_placer", () -> new TrunkPlacerType<>(ModFenceTrunkPlacer.CODEC));

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<ModGiantFenceTrunkPlacer>> GIANT_FENCE_TRUNK_PLACER
            = TRUNK_PLACER_TYPES.register("giant_fence_trunk_placer", () -> new TrunkPlacerType<>(ModGiantFenceTrunkPlacer.CODEC));
}
