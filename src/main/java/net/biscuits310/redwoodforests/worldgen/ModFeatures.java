package net.biscuits310.redwoodforests.worldgen;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.worldgen.tree.ModTreeUpgrade;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, RedwoodForests.MODID);

    public static final DeferredHolder<Feature<?>, ModTreeUpgrade> MOD_TREE_UPGRADE
            = FEATURES.register("mod_tree_upgrade", () -> new ModTreeUpgrade(TreeConfiguration.CODEC));

}
