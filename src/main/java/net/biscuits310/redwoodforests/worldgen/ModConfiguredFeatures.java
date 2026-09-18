package net.biscuits310.redwoodforests.worldgen;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.block.ModBlocks;
import net.biscuits310.redwoodforests.worldgen.tree.ModConeFoliagePlacer;
import net.biscuits310.redwoodforests.worldgen.tree.ModFenceTrunkPlacer;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;

// Creates configured features
// Configured features are arrangements of blocks
public class ModConfiguredFeatures {

    // Register the redwood key
    public static final ResourceKey<ConfiguredFeature<?, ?>> REDWOOD_KEY = registerKey("redwood_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> REDWOOD_1_KEY = registerKey("redwood_1_key");

    // Actions to be executed
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context){
        // Create the redwood tree configured feature
        // Use the TreeConfigurationBuilder to create a tree
        register(context, REDWOOD_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                // The block that acts as the log in the tree
                BlockStateProvider.simple(ModBlocks.REDWOOD_LOG.get()),
                // Use GiantTrunkPlacer to make a 2x2 base
                // Use a height of 32 blocks, and use a randomness of 4 and 3
                new ModFenceTrunkPlacer( 7, 1, 2, ModBlocks.REDWOOD_FENCE, ModBlocks.REDWOOD_ORIGIN_BLOCK, 0.4F, 0),
                // The block that acts as the leaves in the tree
                BlockStateProvider.simple(ModBlocks.REDWOOD_LEAVES.get()),
                // Use MegaPineFoliagePlacer to create a cone leaf shape
                // Use the default value for radius, no offset, and have a height of 3-7
                new ModConeFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(7), ModBlocks.DEEP_REDWOOD_LEAVES, ModBlocks.REDWOOD_FENCE),
                // Used to change thickness at specific points
                new TwoLayersFeatureSize(1, 1 , 2)).build());

        register(context, REDWOOD_1_KEY, ModFeatures.MOD_TREE_UPGRADE.get(), new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.REDWOOD_LOG.get()),
                new ModFenceTrunkPlacer(13, 1, 2, ModBlocks.REDWOOD_FENCE, ModBlocks.REDWOOD_ORIGIN_BLOCK, 0.25F, 1),
                BlockStateProvider.simple(ModBlocks.REDWOOD_LEAVES.get()),
                new ModConeFoliagePlacer(ConstantInt.of(3), ConstantInt.of(1), ConstantInt.of(12), ModBlocks.DEEP_REDWOOD_LEAVES, ModBlocks.REDWOOD_FENCE),
                new TwoLayersFeatureSize(1, 1, 2)).build());
    }

    // Register a configured feature key
    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(RedwoodForests.MODID, name));
    }

    // Used to add features to a key
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration){
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
