package net.biscuits310.redwoodforests.datagen;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.block.ModBlocks;
import net.biscuits310.redwoodforests.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

// Adds block tags to all the modded blocks
// Inherits from BlockTagsProvider
public class ModBlockTagsProvider extends BlockTagsProvider {
    // Constructs everything required by the inherited class
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RedwoodForests.MODID);
    }

    // Overrides addTags to add blocktags to mod blocks
    @Override
    protected void addTags(HolderLookup.Provider provider) {

        // Allows goats the drop their horn when running into a block
        tag(BlockTags.SNAPS_GOAT_HORN)
                .add(ModBlocks.REDWOOD_LOG.get())
                .add(ModBlocks.REDWOOD_ORIGIN_BLOCK.get());

        // Marks a log as one that can naturally spawn in the world
        tag(BlockTags.OVERWORLD_NATURAL_LOGS)
                .add(ModBlocks.REDWOOD_LOG.get())
                .add(ModBlocks.REDWOOD_ORIGIN_BLOCK.get());

        // Marks a log as flammable
        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.REDWOOD_LOG.get())
                .add(ModBlocks.STRIPPED_REDWOOD_LOG.get())
                .add(ModBlocks.REDWOOD_WOOD.get())
                .add(ModBlocks.STRIPPED_REDWOOD_WOOD.get())
                .add(ModBlocks.REDWOOD_ORIGIN_BLOCK.get());

        // Stops stone from replacing the block when lava touches water
        tag(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE)
                .add(ModBlocks.REDWOOD_LOG.get())
                .add(ModBlocks.STRIPPED_REDWOOD_LOG.get())
                .add(ModBlocks.REDWOOD_WOOD.get())
                .add(ModBlocks.STRIPPED_REDWOOD_WOOD.get())
                .add(ModBlocks.REDWOOD_LEAVES.get())
                .add(ModBlocks.DEEP_REDWOOD_LEAVES.get())
                .add(ModBlocks.REDWOOD_ORIGIN_BLOCK.get());

        // Allows the tutorial instruction to find a tree to finish
        tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL)
                .add(ModBlocks.REDWOOD_LOG.get())
                .add(ModBlocks.REDWOOD_WOOD.get())
                .add(ModBlocks.REDWOOD_LEAVES.get())
                .add(ModBlocks.DEEP_REDWOOD_LEAVES.get())
                .add(ModBlocks.REDWOOD_ORIGIN_BLOCK.get());

        // Allows parrots to spawn on the block
        tag(BlockTags.PARROTS_SPAWNABLE_ON)
                .add(ModBlocks.REDWOOD_LOG.get())
                .add(ModBlocks.REDWOOD_WOOD.get())
                .add(ModBlocks.STRIPPED_REDWOOD_WOOD.get())
                .add(ModBlocks.REDWOOD_LEAVES.get())
                .add(ModBlocks.DEEP_REDWOOD_LEAVES.get())
                .add(ModBlocks.REDWOOD_ORIGIN_BLOCK.get());

        // Marks the block as a log
        tag(BlockTags.LOGS)
                .add(ModBlocks.REDWOOD_LOG.get())
                .add(ModBlocks.STRIPPED_REDWOOD_LOG.get())
                .add(ModBlocks.REDWOOD_WOOD.get())
                .add(ModBlocks.STRIPPED_REDWOOD_WOOD.get())
                .add(ModBlocks.REDWOOD_ORIGIN_BLOCK.get());

        // Makes the block mine faster when holding an axe
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.REDWOOD_LOG.get())
                .add(ModBlocks.REDWOOD_PLANKS.get())
                .add(ModBlocks.STRIPPED_REDWOOD_LOG.get())
                .add(ModBlocks.REDWOOD_WOOD.get())
                .add(ModBlocks.STRIPPED_REDWOOD_WOOD.get())
                .add(ModBlocks.REDWOOD_STAIRS.get())
                .add(ModBlocks.REDWOOD_SLAB.get())
                .add(ModBlocks.REDWOOD_FENCE.get())
                .add(ModBlocks.REDWOOD_ORIGIN_BLOCK.get());

        // Marks the block as a plank
        tag(BlockTags.PLANKS)
                .add(ModBlocks.REDWOOD_PLANKS.get());

        // Marks the block as stairs
        tag(BlockTags.STAIRS)
                .add(ModBlocks.REDWOOD_STAIRS.get());

        // Marks the block as wooden stairs
        tag(BlockTags.WOODEN_STAIRS)
                .add(ModBlocks.REDWOOD_STAIRS.get());

        // Marks the block as a slab
        tag(BlockTags.SLABS)
                .add(ModBlocks.REDWOOD_SLAB.get());

        // Marks the block as a wooden slab
        tag(BlockTags.WOODEN_SLABS)
                .add(ModBlocks.REDWOOD_SLAB.get());

        // Marks the block as a wooden fence
        tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.REDWOOD_FENCE.get());

        // Marks the block as a fence
        tag(BlockTags.FENCES)
                .add(ModBlocks.REDWOOD_FENCE.get());

        // Makes the block break faster when holding a sword
        tag(BlockTags.SWORD_EFFICIENT)
                .add(ModBlocks.REDWOOD_LEAVES.get())
                .add(ModBlocks.DEEP_REDWOOD_LEAVES.get());

        // Allows growing trees to replace the block
        tag(BlockTags.REPLACEABLE_BY_TREES)
                .add(ModBlocks.REDWOOD_LEAVES.get())
                .add(ModBlocks.DEEP_REDWOOD_LEAVES.get());

        // Makes the block mine faster when holding a hoe
        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.REDWOOD_LEAVES.get())
                .add(ModBlocks.DEEP_REDWOOD_LEAVES.get());

        // Marks the block as leaves
        tag(BlockTags.LEAVES)
                .add(ModBlocks.REDWOOD_LEAVES.get())
                .add(ModBlocks.DEEP_REDWOOD_LEAVES.get());

        // Allows growing mushrooms to replace the block
        tag(BlockTags.REPLACEABLE_BY_MUSHROOMS)
                .add(ModBlocks.REDWOOD_LEAVES.get());

        // Marks the block as a stripped log for other mods
        tag(Tags.Blocks.STRIPPED_LOGS)
                .add(ModBlocks.STRIPPED_REDWOOD_LOG.get());

        // Marks the block as a stripped wood for other mods
        tag(Tags.Blocks.STRIPPED_WOODS)
                .add(ModBlocks.STRIPPED_REDWOOD_WOOD.get());

        // Marks the block as a natural log for other mods
        tag(Tags.Blocks.NATURAL_LOGS)
                .add(ModBlocks.REDWOOD_LOG.get())
                .add(ModBlocks.REDWOOD_WOOD.get())
                .add(ModBlocks.REDWOOD_ORIGIN_BLOCK.get());

        // Marks the block as a log that spawns naturally for other mods
        tag(Tags.Blocks.OVERWORLD_NATURAL_LOGS)
                .add(ModBlocks.REDWOOD_LOG.get())
                .add(ModBlocks.REDWOOD_ORIGIN_BLOCK.get());

        // Marks the block as a wooden fence for other mods
        tag(Tags.Blocks.FENCES_WOODEN)
                .add(ModBlocks.REDWOOD_FENCE.get());

        tag(ModTags.Blocks.REDWOOD_GROWERS)
                .add(ModBlocks.REDWOOD_SAPLING.get())
                .add(ModBlocks.REDWOOD_ORIGIN_BLOCK.get());

        tag(ModTags.Blocks.REDWOOD_FOLIAGE_REMOVABLE)
                .add(ModBlocks.REDWOOD_LEAVES.get())
                .add(ModBlocks.DEEP_REDWOOD_LEAVES.get())
                .add(ModBlocks.REDWOOD_FENCE.get());

        tag(ModTags.Blocks.REDWOOD_TRUNK_REMOVABLE)
                .add(ModBlocks.REDWOOD_LOG.get())
                .add(ModBlocks.REDWOOD_FENCE.get());
    }
}
