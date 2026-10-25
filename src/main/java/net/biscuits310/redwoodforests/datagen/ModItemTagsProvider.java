package net.biscuits310.redwoodforests.datagen;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.block.Blocks;
import net.biscuits310.redwoodforests.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

// Adds item tags to all the modded items, including block counterparts
// Inherits from the ItemTagsProvider class
public class ModItemTagsProvider extends ItemTagsProvider {
    // Constructs everything required by the inherited class
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RedwoodForests.MODID);
    }

    // Overrides addTags to add itemTags to mod items
    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        // Marks a block as planks for crafting recipes
        tag(ItemTags.PLANKS)
                .add(Blocks.REDWOOD_PLANKS.asItem());

        // Marks a block as wood that can make wooden tools for crafting recipes
        tag(ItemTags.WOODEN_TOOL_MATERIALS)
                .add(Blocks.REDWOOD_PLANKS.asItem());

        // Allows an item to finish the tutorial by picking it up
        tag(ItemTags.COMPLETES_FIND_TREE_TUTORIAL)
                .add(Blocks.REDWOOD_LOG.asItem())
                .add(Blocks.STRIPPED_REDWOOD_LOG.asItem())
                .add(Blocks.STRIPPED_REDWOOD_WOOD.asItem())
                .add(Blocks.REDWOOD_LEAVES.asItem())
                .add(Blocks.DEEP_REDWOOD_LEAVES.asItem());

        // Marks an item as a log for crafting recipes
        tag(ItemTags.LOGS)
                .add(Blocks.REDWOOD_LOG.asItem())
                .add(Blocks.STRIPPED_REDWOOD_LOG.asItem())
                .add(Blocks.STRIPPED_REDWOOD_WOOD.asItem());

        // Marks an item as a flammable log for smelting recipes
        tag(ItemTags.LOGS_THAT_BURN)
                .add(Blocks.REDWOOD_LOG.asItem())
                .add(Blocks.STRIPPED_REDWOOD_LOG.asItem())
                .add(Blocks.STRIPPED_REDWOOD_WOOD.asItem());

        // Marks an item as a stair for crafting recipes
        tag(ItemTags.STAIRS)
                .add(Blocks.REDWOOD_STAIRS.asItem());

        // Marks an item as wooden stairs for crafting recipes
        tag(ItemTags.WOODEN_STAIRS)
                .add(Blocks.REDWOOD_STAIRS.asItem());

        // Marks an item as a slab for crafting recipes
        tag(ItemTags.SLABS)
                .add(Blocks.REDWOOD_SLAB.asItem());

        // Marks an item as a wooden slab for crafting recipes
        tag(ItemTags.WOODEN_SLABS)
                .add(Blocks.REDWOOD_SLAB.asItem());

        // Marks an item as a fence for crafting recipes
        tag(ItemTags.FENCES)
                .add(Blocks.REDWOOD_FENCE.asItem());

        // Marks an item as a wooden fence for crafting recipes
        tag(ItemTags.WOODEN_FENCES)
                .add(Blocks.REDWOOD_FENCE.asItem());

        // Marks an item as leaves for crafting recipes
        tag(ItemTags.LEAVES)
                .add(Blocks.REDWOOD_LEAVES.asItem())
                .add(Blocks.DEEP_REDWOOD_LEAVES.asItem());

        tag(ItemTags.FENCE_GATES)
                .add(Blocks.REDWOOD_FENCE_GATE.asItem());

        // Marks an item as a stripped log for crafting recipes for other mods
        tag(Tags.Items.STRIPPED_LOGS)
                .add(Blocks.STRIPPED_REDWOOD_LOG.asItem());

        // Marks an item as a stripped wood for crafting recipes for other mods
        tag(Tags.Items.STRIPPED_WOODS)
                .add(Blocks.STRIPPED_REDWOOD_WOOD.asItem());

        // Marks an item as a fence for crafting recipes for other mods
        tag(Tags.Items.FENCES)
                .add(Blocks.REDWOOD_FENCE.asItem());

        // Marks an item as a wooden fence for crafting recipes for other mods
        tag(Tags.Items.FENCES_WOODEN)
                .add(Blocks.REDWOOD_FENCE.asItem());

        tag(Tags.Items.FENCE_GATES)
                .add(Blocks.REDWOOD_FENCE_GATE.asItem());

        tag(Tags.Items.FENCE_GATES_WOODEN)
                .add(Blocks.REDWOOD_FENCE_GATE.asItem());

        // Marks an item as a redwood log for the redwood planks recipe
        tag(ModTags.Items.REDWOOD_LOGS)
                .add(Blocks.STRIPPED_REDWOOD_LOG.asItem())
                .add(Blocks.REDWOOD_LOG.asItem())
                .add(Blocks.STRIPPED_REDWOOD_WOOD.asItem())
                .add(Blocks.REDWOOD_WOOD.asItem());
    }
}
