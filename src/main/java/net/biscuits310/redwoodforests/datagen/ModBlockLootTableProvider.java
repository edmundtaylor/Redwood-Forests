package net.biscuits310.redwoodforests.datagen;

import net.biscuits310.redwoodforests.block.Blocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

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
        dropSelf(Blocks.REDWOOD_LOG.get());
        dropSelf(Blocks.REDWOOD_PLANKS.get());
        dropSelf(Blocks.STRIPPED_REDWOOD_LOG.get());
        dropSelf(Blocks.REDWOOD_WOOD.get());
        dropSelf(Blocks.STRIPPED_REDWOOD_WOOD.get());
        dropSelf(Blocks.REDWOOD_STAIRS.get());
        // Slabs can block 1 or 2 of themselves based on their blockstate, so the createSlabItemTable loot provider is required
        add(Blocks.REDWOOD_SLAB.get(), this::createSlabItemTable);
        dropSelf(Blocks.REDWOOD_FENCE.get());
        // Leaves do not drop themselves without unique circumstances, but do drop saplings, so require a createLeavesDrop loot provider
        add(Blocks.REDWOOD_LEAVES.get(), createLeavesDrops(Blocks.REDWOOD_LEAVES.get(), Blocks.REDWOOD_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        add(Blocks.DEEP_REDWOOD_LEAVES.get(), createLeavesDrops(Blocks.REDWOOD_LEAVES.get(), Blocks.REDWOOD_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        dropSelf(Blocks.REDWOOD_SAPLING.get());
        // Potted plants drop both the held plant, and the pot, so require the dropPottedContents provider
        dropPottedContents(Blocks.POTTED_REDWOOD_SAPLING.get());
        dropOther(Blocks.REDWOOD_ORIGIN_BLOCK.get(), Blocks.REDWOOD_LOG.get());
    }

    // Applies all the loot tables to the BLOCKS DeferredRegister
    @Override
    protected @NonNull Iterable<Block> getKnownBlocks() {
        return Blocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
