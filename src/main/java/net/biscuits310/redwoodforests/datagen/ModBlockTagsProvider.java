package net.biscuits310.redwoodforests.datagen;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.block.Blocks;
import net.biscuits310.redwoodforests.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jspecify.annotations.NonNull;

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
    protected void addTags(HolderLookup.@NonNull Provider provider) {

        // Allows goats the drop their horn when running into a block
        tag(BlockTags.SNAPS_GOAT_HORN)
                .add(Blocks.REDWOOD_LOG.get())
                .add(Blocks.REDWOOD_ORIGIN_BLOCK.get());

        // Marks a log as one that can naturally spawn in the world
        tag(BlockTags.OVERWORLD_NATURAL_LOGS)
                .add(Blocks.REDWOOD_LOG.get())
                .add(Blocks.REDWOOD_ORIGIN_BLOCK.get());

        // Marks a log as flammable
        tag(BlockTags.LOGS_THAT_BURN)
                .add(Blocks.REDWOOD_LOG.get())
                .add(Blocks.STRIPPED_REDWOOD_LOG.get())
                .add(Blocks.REDWOOD_WOOD.get())
                .add(Blocks.STRIPPED_REDWOOD_WOOD.get())
                .add(Blocks.REDWOOD_ORIGIN_BLOCK.get());

        // Stops stone from replacing the block when lava touches water
        tag(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE)
                .add(Blocks.REDWOOD_LOG.get())
                .add(Blocks.STRIPPED_REDWOOD_LOG.get())
                .add(Blocks.REDWOOD_WOOD.get())
                .add(Blocks.STRIPPED_REDWOOD_WOOD.get())
                .add(Blocks.REDWOOD_LEAVES.get())
                .add(Blocks.DEEP_REDWOOD_LEAVES.get())
                .add(Blocks.REDWOOD_ORIGIN_BLOCK.get());

        // Allows the tutorial instruction to find a tree to finish
        tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL)
                .add(Blocks.REDWOOD_LOG.get())
                .add(Blocks.REDWOOD_WOOD.get())
                .add(Blocks.REDWOOD_LEAVES.get())
                .add(Blocks.DEEP_REDWOOD_LEAVES.get())
                .add(Blocks.REDWOOD_ORIGIN_BLOCK.get());

        // Allows parrots to spawn on the block
        tag(BlockTags.PARROTS_SPAWNABLE_ON)
                .add(Blocks.REDWOOD_LOG.get())
                .add(Blocks.REDWOOD_WOOD.get())
                .add(Blocks.STRIPPED_REDWOOD_WOOD.get())
                .add(Blocks.REDWOOD_LEAVES.get())
                .add(Blocks.DEEP_REDWOOD_LEAVES.get())
                .add(Blocks.REDWOOD_ORIGIN_BLOCK.get());

        // Marks the block as a log
        tag(BlockTags.LOGS)
                .add(Blocks.REDWOOD_LOG.get())
                .add(Blocks.STRIPPED_REDWOOD_LOG.get())
                .add(Blocks.REDWOOD_WOOD.get())
                .add(Blocks.STRIPPED_REDWOOD_WOOD.get())
                .add(Blocks.REDWOOD_ORIGIN_BLOCK.get());

        // Makes the block mine faster when holding an axe
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(Blocks.REDWOOD_LOG.get())
                .add(Blocks.REDWOOD_PLANKS.get())
                .add(Blocks.STRIPPED_REDWOOD_LOG.get())
                .add(Blocks.REDWOOD_WOOD.get())
                .add(Blocks.STRIPPED_REDWOOD_WOOD.get())
                .add(Blocks.REDWOOD_STAIRS.get())
                .add(Blocks.REDWOOD_SLAB.get())
                .add(Blocks.REDWOOD_FENCE.get())
                .add(Blocks.REDWOOD_ORIGIN_BLOCK.get())
                .add(Blocks.REDWOOD_FENCE_GATE.get())
                .add(Blocks.REDWOOD_DOOR.get())
                .add(Blocks.REDWOOD_TRAPDOOR.get())
                .add(Blocks.REDWOOD_PRESSURE_PLATE.get())
                .add(Blocks.REDWOOD_BUTTON.get());

        // Marks the block as a plank
        tag(BlockTags.PLANKS)
                .add(Blocks.REDWOOD_PLANKS.get());

        // Marks the block as stairs
        tag(BlockTags.STAIRS)
                .add(Blocks.REDWOOD_STAIRS.get());

        // Marks the block as wooden stairs
        tag(BlockTags.WOODEN_STAIRS)
                .add(Blocks.REDWOOD_STAIRS.get());

        // Marks the block as a slab
        tag(BlockTags.SLABS)
                .add(Blocks.REDWOOD_SLAB.get());

        // Marks the block as a wooden slab
        tag(BlockTags.WOODEN_SLABS)
                .add(Blocks.REDWOOD_SLAB.get());

        // Marks the block as a wooden fence
        tag(BlockTags.WOODEN_FENCES)
                .add(Blocks.REDWOOD_FENCE.get());

        // Marks the block as a fence
        tag(BlockTags.FENCES)
                .add(Blocks.REDWOOD_FENCE.get());

        // Makes the block break faster when holding a sword
        tag(BlockTags.SWORD_EFFICIENT)
                .add(Blocks.REDWOOD_LEAVES.get())
                .add(Blocks.DEEP_REDWOOD_LEAVES.get());

        // Allows growing trees to replace the block
        tag(BlockTags.REPLACEABLE_BY_TREES)
                .add(Blocks.REDWOOD_LEAVES.get())
                .add(Blocks.DEEP_REDWOOD_LEAVES.get());

        // Makes the block mine faster when holding a hoe
        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(Blocks.REDWOOD_LEAVES.get())
                .add(Blocks.DEEP_REDWOOD_LEAVES.get());

        // Marks the block as leaves
        tag(BlockTags.LEAVES)
                .add(Blocks.REDWOOD_LEAVES.get())
                .add(Blocks.DEEP_REDWOOD_LEAVES.get());

        // Allows growing mushrooms to replace the block
        tag(BlockTags.REPLACEABLE_BY_MUSHROOMS)
                .add(Blocks.REDWOOD_LEAVES.get());

        tag(BlockTags.FENCE_GATES)
                .add(Blocks.REDWOOD_FENCE_GATE.get());

        tag(BlockTags.MAINTAINS_FARMLAND)
                .add(Blocks.REDWOOD_FENCE_GATE.get());

        tag(BlockTags.UNSTABLE_BOTTOM_CENTER)
                .add(Blocks.REDWOOD_FENCE_GATE.get());

        tag(BlockTags.DOORS)
                .add(Blocks.REDWOOD_DOOR.get());

        tag(BlockTags.MOB_INTERACTABLE_DOORS)
                .add(Blocks.REDWOOD_DOOR.get());

        tag(BlockTags.WOODEN_DOORS)
                .add(Blocks.REDWOOD_DOOR.get());

        tag(BlockTags.TRAPDOORS)
                .add(Blocks.REDWOOD_TRAPDOOR.get());

        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(Blocks.REDWOOD_TRAPDOOR.get());

        tag(BlockTags.PRESSURE_PLATES)
                .add(Blocks.REDWOOD_PRESSURE_PLATE.get());

        tag(BlockTags.WALL_POST_OVERRIDE)
                .add(Blocks.REDWOOD_PRESSURE_PLATE.get());

        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(Blocks.REDWOOD_PRESSURE_PLATE.get());

        tag(BlockTags.BUTTONS)
                .add(Blocks.REDWOOD_BUTTON.get());

        tag(BlockTags.WOODEN_BUTTONS)
                .add(Blocks.REDWOOD_BUTTON.get());

        // Marks the block as a stripped log for other mods
        tag(Tags.Blocks.STRIPPED_LOGS)
                .add(Blocks.STRIPPED_REDWOOD_LOG.get());

        // Marks the block as a stripped wood for other mods
        tag(Tags.Blocks.STRIPPED_WOODS)
                .add(Blocks.STRIPPED_REDWOOD_WOOD.get());

        // Marks the block as a natural log for other mods
        tag(Tags.Blocks.NATURAL_LOGS)
                .add(Blocks.REDWOOD_LOG.get())
                .add(Blocks.REDWOOD_WOOD.get())
                .add(Blocks.REDWOOD_ORIGIN_BLOCK.get());

        // Marks the block as a log that spawns naturally for other mods
        tag(Tags.Blocks.OVERWORLD_NATURAL_LOGS)
                .add(Blocks.REDWOOD_LOG.get())
                .add(Blocks.REDWOOD_ORIGIN_BLOCK.get());

        // Marks the block as a wooden fence for other mods
        tag(Tags.Blocks.FENCES_WOODEN)
                .add(Blocks.REDWOOD_FENCE.get());

        tag(Tags.Blocks.FENCE_GATES)
                .add(Blocks.REDWOOD_FENCE_GATE.get());

        tag(Tags.Blocks.FENCE_GATES_WOODEN)
                .add(Blocks.REDWOOD_FENCE_GATE.get());

        tag(ModTags.Blocks.REDWOOD_GROWERS)
                .add(Blocks.REDWOOD_SAPLING.get())
                .add(Blocks.REDWOOD_1_PLACEMENT_SAPLING.get())
                .add(Blocks.REDWOOD_2_PLACEMENT_SAPLING.get())
                .add(Blocks.REDWOOD_3_PLACEMENT_SAPLING.get())
                .add(Blocks.REDWOOD_ORIGIN_BLOCK.get());

        tag(ModTags.Blocks.REDWOOD_FOLIAGE_REMOVABLE)
                .add(Blocks.REDWOOD_LEAVES.get())
                .add(Blocks.DEEP_REDWOOD_LEAVES.get())
                .add(Blocks.REDWOOD_FENCE.get());

        tag(ModTags.Blocks.REDWOOD_TRUNK_REMOVABLE)
                .add(Blocks.REDWOOD_LOG.get())
                .add(Blocks.REDWOOD_FENCE.get())
                .add(Blocks.REDWOOD_ORIGIN_BLOCK.get());

        tag(ModTags.Blocks.REDWOOD_LOGS)
                .add(Blocks.REDWOOD_LOG.get())
                .add(Blocks.REDWOOD_ORIGIN_BLOCK.get());
    }
}
