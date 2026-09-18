package net.biscuits310.redwoodforests.worldgen.tree;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.Set;
import java.util.function.Supplier;

public class ModConeFoliageRemover extends ModConeFoliagePlacer {
    public ModConeFoliageRemover(IntProvider radius, IntProvider offset, IntProvider crownHeight, Supplier<Block> deepFoliageBlock, Supplier<Block> fenceBlock) {
        super(radius, offset, crownHeight, deepFoliageBlock, fenceBlock);
    }

    @Override
    protected boolean tryPlaceDeepLeaf(WorldGenLevel level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, BlockPos pos) {
        return this.tryUpgradeLeaf(level, foliageSetter, random, config, pos);
    }

    @Override
    protected boolean tryPlaceFenceBlock(WorldGenLevel level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, BlockPos pos) {
        return this.tryUpgradeLeaf(level, foliageSetter, random, config, pos);
    }

    protected boolean tryUpgradeLeaf(WorldGenLevel level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, BlockPos pos){
        boolean isPersistent = level.isStateAtPosition(pos, state -> state.getValueOrElse(BlockStateProperties.PERSISTENT, false));
        if (!isPersistent && ModTreeUpgrade.validFoliageUpgradePos(level, pos)) {
            BlockState foliageState = Blocks.AIR.defaultBlockState();
            foliageSetter.set(pos, foliageState);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void placeLeavesRow(WorldGenLevel level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, BlockPos origin, int currentRadius, int y, boolean doubleTrunk, Set<BlockPos> leafBlocks) {
        int offset = doubleTrunk ? 1 : 0;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int dx = -currentRadius; dx <= currentRadius + offset; dx++) {
            for (int dz = -currentRadius; dz <= currentRadius + offset; dz++) {
                BlockPos rootPos = origin.offset(dx, 0, dz).atY(y);

                if (!this.shouldSkipLocationSigned(random, dx, y, dz, currentRadius, doubleTrunk))
                {
                    int shouldSkipLocationLight = shouldSkipLocationLight(dx, dz, random, leafBlocks, rootPos, currentRadius);
                    if (shouldSkipLocationLight == 1){
                        leafBlocks.add(rootPos);
                        pos.setWithOffset(origin, dx, y, dz);
                        tryUpgradeLeaf(level, foliageSetter, random, config, pos);
                    }
                    if (shouldSkipLocationLight == 2) {
                        leafBlocks.add(rootPos);
                        pos.setWithOffset(origin, dx, y, dz);
                        tryPlaceDeepLeaf(level, foliageSetter, random, config, pos);
                    }

                    if (shouldSkipLocationLight == 3){
                        leafBlocks.add(rootPos);
                        pos.setWithOffset(origin, dx, y, dz);
                        tryPlaceFenceBlock(level, foliageSetter, random, config, pos);
                    }
                }
            }
        }
    }
}