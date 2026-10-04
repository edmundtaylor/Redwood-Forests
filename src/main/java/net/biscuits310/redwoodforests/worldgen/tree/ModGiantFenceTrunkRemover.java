package net.biscuits310.redwoodforests.worldgen.tree;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModGiantFenceTrunkRemover extends ModGiantFenceTrunkPlacer{
    public ModGiantFenceTrunkRemover(int baseHeight, int heightRandA, int heightRandB, Supplier<Block> fenceBlock, Supplier<Block> originBlock, float fenceProportion, float progressChance, int growthStage) {
        super(baseHeight, heightRandA, heightRandB, fenceBlock, originBlock, fenceProportion, progressChance, growthStage);
    }

    @Override
    protected boolean validTreePos(WorldGenLevel level, BlockPos pos) {
        return ModTreeUpgrade.validTrunkUpgradePos(level, pos);
    }

    @Override
    public boolean isFree(WorldGenLevel level, BlockPos pos) {
        return ModTreeUpgrade.validTrunkUpgradeAndPlacementPos(level, pos) || level.isStateAtPosition(pos, state -> state.is(BlockTags.LOGS));
    }
}
