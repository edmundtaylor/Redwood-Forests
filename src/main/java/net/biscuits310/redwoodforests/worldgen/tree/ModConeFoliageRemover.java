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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ModConeFoliageRemover extends ModConeFoliagePlacer {
    public ModConeFoliageRemover(IntProvider radius, IntProvider offset, IntProvider crownHeight, Supplier<Block> deepFoliageBlock, Supplier<Block> fenceBlock, IntProvider tipHeight) {
        super(radius, offset, crownHeight, deepFoliageBlock, fenceBlock, tipHeight);
    }

    private final Set<BlockPos> trunkMap = new HashSet<>();

    @Override
    protected void createFoliage(WorldGenLevel level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, int treeHeight, FoliageAttachment foliageAttachment, int foliageHeight, int leafRadius, int offset) {
        trunkMap.clear();
        BlockPos foliagePos = foliageAttachment.pos();
        BlockPos.MutableBlockPos tempTrunkPos = new BlockPos.MutableBlockPos();
        for (int h = 0; h < treeHeight; h++){
            tempTrunkPos.setWithOffset(foliagePos, 0, -h, 0);
            trunkMap.add(tempTrunkPos.immutable());
        }
        int currentRadius;
        Set<BlockPos> leafBlocks = new HashSet<>();

        for (int depth = 1; depth <= foliageHeight; depth++){
            currentRadius = Math.round(depth * leafRadius / (float)foliageHeight);
            if (depth == foliageHeight) {currentRadius /= 2;}
            this.placeLeavesRow(level, foliageSetter, random, config, foliagePos, currentRadius, -depth+offset, foliageAttachment.doubleTrunk(), leafBlocks);
        }


        for (int height = 0; height <= tipHeight(random); height++){
            BlockPos currentPos = foliageAttachment.pos().offset(0, height, 0);
            if (random.nextFloat() < 0.25){
                tryPlaceFenceBlock(level, foliageSetter, random, config, currentPos);
                continue;
            }
            tryPlaceLeaf(level, foliageSetter, random, config, currentPos);
        }
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
        if (!isPersistent && ModTreeUpgrade.validFoliageUpgradePos(level, pos) && !trunkMap.contains(pos)) {
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