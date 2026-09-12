package net.biscuits310.redwoodforests.worldgen.tree;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

// Creates tree growers, which define the chances of different types of ConfiguredFeatures spawning
public class ModTreeGrowers {
    // Creates the redwood TreeGrower
    public static final TreeGrower REDWOOD = new TreeGrower(
            // Defines the name
            RedwoodForests.MODID + "redwood",
            // Defines the chance of the secondary ConfiguredFeature from spawning
            // This float is 0 as I do not currently have a secondary ConfiguredFeature
            0.0F,
            // The ConfiguredFeature for a mega tree
            // A mega tree is a larger variant of a tree spawned using 4 saplings arranged in a square
            Optional.empty(),
            // The ConfiguredFeature for a secondary mega tree
            Optional.empty(),
            // The ConfiguredFeature for the main tree
            // Using the REDWOOD_KEY as that is the ConfiguredFeature for my tree
            Optional.of(ModConfiguredFeatures.REDWOOD_KEY),
            // The ConfiguredFeature for the secondary tree
            Optional.empty(),
            // Defines the flowers required to spawn a bee nest on the tree
            Optional.empty(),
            Optional.empty());


}
