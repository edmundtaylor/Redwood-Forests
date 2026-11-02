package net.biscuits310.redwoodforests.worldgen.tree;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFoliagePlacerType {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES =
            DeferredRegister.create(BuiltInRegistries.FOLIAGE_PLACER_TYPE.key(), RedwoodForests.MODID);

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<ModConeFoliagePlacer>> CONE_FOLIAGE_PLACER
            = FOLIAGE_PLACER_TYPES.register("cone_foliage_placer", () -> new FoliagePlacerType<>(ModConeFoliagePlacer.CODEC));
}
