package net.biscuits310.redwoodforests.worldgen.tree;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

import java.util.function.Supplier;

public class ModGiantFenceTrunkRemover extends ModGiantFenceTrunkPlacer{
    public ModGiantFenceTrunkRemover(int baseHeight, int heightRandA, int heightRandB, Supplier<Block> fenceBlock, Supplier<Block> originBlock, float fenceProportion, float progressChance, int growthStage) {
        super(baseHeight, heightRandA, heightRandB, fenceBlock, originBlock, fenceProportion, progressChance, growthStage);
    }

    @Override
    protected boolean validTreePos(@NonNull WorldGenLevel level, @NonNull BlockPos pos) {
        return ModTreeUpgrade.validTrunkUpgradePos(level, pos);
    }

    @Override
    public boolean isFree(@NonNull WorldGenLevel level, @NonNull BlockPos pos) {
        return ModTreeUpgrade.validTrunkUpgradeAndPlacementPos(level, pos) || level.isStateAtPosition(pos, state -> state.is(BlockTags.LOGS));
    }
}
