package net.biscuits310.redwoodforests.worldgen.tree;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModFenceTrunkRemover extends ModFenceTrunkPlacer{
    public ModFenceTrunkRemover(int baseHeight, int heightRandA, int heightRandB, Supplier<Block> fenceBlock, Supplier<Block> originBlock, float fenceProportion, int growthStage) {
        super(baseHeight, heightRandA, heightRandB, fenceBlock, originBlock, fenceProportion, growthStage);
    }

    @Override
    public @NonNull List<FoliagePlacer.FoliageAttachment> placeTrunk(@NonNull WorldGenLevel level, @NonNull BiConsumer<BlockPos, BlockState> trunkSetter, @NonNull RandomSource random, int treeHeight, BlockPos origin, @NonNull TreeConfiguration config) {
        BlockPos.MutableBlockPos trunkPos = new BlockPos.MutableBlockPos();

        for (int hh = 0; hh < treeHeight; hh++){
            trunkPos.setWithOffset(origin, 0, hh, 0);
            this.placeLog(level, trunkSetter, random, trunkPos, config);
        }
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(origin.above(treeHeight), 0, false));
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
