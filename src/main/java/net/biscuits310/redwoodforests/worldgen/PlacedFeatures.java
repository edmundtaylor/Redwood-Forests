package net.biscuits310.redwoodforests.worldgen;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.block.BlockStateProperties;
import net.biscuits310.redwoodforests.block.Blocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

// Creates placed features
// Placed features are configured features given positions
public class PlacedFeatures {

    public static final ResourceKey<PlacedFeature> REDWOOD_PLACED_KEY = registerKey("redwood_placed");
    public static final ResourceKey<PlacedFeature> REDWOOD_1_PLACED_KEY = registerKey("redwood_1_placed");
    public static final ResourceKey<PlacedFeature> REDWOOD_2_PLACED_KEY = registerKey("redwood_2_placed");
    public static final ResourceKey<PlacedFeature> REDWOOD_3_PLACED_KEY = registerKey("redwood_3_placed");

    // Actions to be executed
    public static void bootstrap(BootstrapContext<PlacedFeature> context){
        // Get the configured features
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, REDWOOD_PLACED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatures.REDWOOD_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.2f, 3),
                        Blocks.REDWOOD_SAPLING.get()));
        register(context, REDWOOD_1_PLACED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatures.REDWOOD_1_PLACEMENT_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.2f, 3),
                        Blocks.REDWOOD_1_PLACEMENT_SAPLING.get()));
        register(context, REDWOOD_2_PLACED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatures.REDWOOD_2_PLACEMENT_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.2f, 3),
                        Blocks.REDWOOD_2_PLACEMENT_SAPLING.get()));
        register(context, REDWOOD_3_PLACED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatures.REDWOOD_3_PLACEMENT_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.2f, 3),
                        Blocks.REDWOOD_3_PLACEMENT_SAPLING.get()));
    }

    // Register a placed feature key
    private static ResourceKey<PlacedFeature> registerKey(String name){
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(RedwoodForests.MODID, name));
    }

    // Used to add features to a key
    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers){
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
