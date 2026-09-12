package net.biscuits310.redwoodforests.datagen;

import net.biscuits310.redwoodforests.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Set;

// Defines what each of the modded blocks drop on their destruction
// Inherits BlockLootSubProvider
public class ModBlockLootTableProvider extends BlockLootSubProvider {
    // Constructs everything required by the inherited class
    public ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    // Overrides the generate method to create loot drops
    @Override
    protected void generate() {
        // dropSelf makes the block drop its corresponding item
        dropSelf(ModBlocks.REDWOOD_LOG.get());
        dropSelf(ModBlocks.REDWOOD_PLANKS.get());
        dropSelf(ModBlocks.STRIPPED_REDWOOD_LOG.get());
        dropSelf(ModBlocks.REDWOOD_WOOD.get());
        dropSelf(ModBlocks.STRIPPED_REDWOOD_WOOD.get());
        dropSelf(ModBlocks.REDWOOD_STAIRS.get());
        // Slabs can block 1 or 2 of themselves based on their blockstate, so the createSlabItemTable loot provider is required
        add(ModBlocks.REDWOOD_SLAB.get(), this::createSlabItemTable);
        dropSelf(ModBlocks.REDWOOD_FENCE.get());
        // Leaves do not drop themselves without unique circumstances, but do drop saplings, so require a createLeavesDrop loot provider
        add(ModBlocks.REDWOOD_LEAVES.get(), createLeavesDrops(ModBlocks.REDWOOD_LEAVES.get(), ModBlocks.REDWOOD_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        add(ModBlocks.DEEP_REDWOOD_LEAVES.get(), createLeavesDrops(ModBlocks.REDWOOD_LEAVES.get(), ModBlocks.REDWOOD_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        dropSelf(ModBlocks.REDWOOD_SAPLING.get());
        // Potted plants drop both the held plant, and the pot, so require the dropPottedContents provider
        dropPottedContents(ModBlocks.POTTED_REDWOOD_SAPLING.get());
        dropOther(ModBlocks.REDWOOD_ORIGIN_BLOCK.get(), ModBlocks.REDWOOD_LOG.get());
    }

    // Applies all of the loot tables to the BLOCKS DeferredRegister
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
