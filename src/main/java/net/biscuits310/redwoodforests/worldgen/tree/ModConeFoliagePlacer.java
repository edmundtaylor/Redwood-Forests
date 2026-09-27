package net.biscuits310.redwoodforests.worldgen.tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.biscuits310.redwoodforests.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.material.Fluids;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ModConeFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<ModConeFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
            i -> foliagePlacerParts(i)
                    .and(IntProviders.codec(0, 24).fieldOf("crown_height").forGetter(p -> p.crownHeight))
                    .and(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("deep_foliage_block").forGetter(p -> p.deepFoliageBlock.get()))
                    .and(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("fence_block").forGetter(p -> p.fenceBlock.get()))
                    .apply(i, (radius, offset, crownHeight, deepFoliageBlock, fenceBlock) ->
                            new ModConeFoliagePlacer(
                                    radius,
                                    offset,
                                    crownHeight,
                                    () -> deepFoliageBlock,
                                    () -> fenceBlock
                            )));

    private final IntProvider crownHeight;
    private final Supplier<net.minecraft.world.level.block.Block> deepFoliageBlock;
    private final Supplier<net.minecraft.world.level.block.Block> fenceBlock;

    public ModConeFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider crownHeight, Supplier<Block> deepFoliageBlock, Supplier<Block> fenceBlock){
        super(radius, offset);
        this.crownHeight = crownHeight;
        this.deepFoliageBlock = deepFoliageBlock;
        this.fenceBlock = fenceBlock;
    }

    @Override
    protected FoliagePlacerType type(){
        return ModFoliagePlacerType.CONE_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(
            WorldGenLevel level,
            FoliageSetter foliageSetter,
            RandomSource random,
            TreeConfiguration config,
            int treeHeight,
            FoliageAttachment foliageAttachment,
            int foliageHeight,
            int leafRadius,
            int offset
    ) {
        BlockPos foliagePos = foliageAttachment.pos();
        int currentRadius;
        Set<BlockPos> leafBlocks = new HashSet<>();

        for (int depth = 1; depth <= foliageHeight; depth++){
            currentRadius = Math.round(depth * leafRadius / (float)foliageHeight);
            if (depth == foliageHeight) {currentRadius /= 2;}
            this.placeLeavesRow(level, foliageSetter, random, config, foliagePos, currentRadius, -depth+offset, foliageAttachment.doubleTrunk(), leafBlocks);
        }

        for (int height = 0; height <=2; height++){
            tryPlaceLeaf(level, foliageSetter, random, config, foliageAttachment.pos().offset(0, height, 0));
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int treeHeight, TreeConfiguration config) {
        return this.crownHeight.sample(random);
    }

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
                        tryPlaceLeaf(level, foliageSetter, random, config, pos);
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

    protected int shouldSkipLocationLight(int dx, int dz, RandomSource random, Set<BlockPos> leafBlocks, BlockPos rootPos, int currentRadius) {
        float skipChance = -0.2f;
        float branchChance = 0.2f;

        float distance = Mth.sqrt(dx*dx + dz*dz);
        skipChance += distance / currentRadius * 0.4;
        branchChance -= distance/currentRadius * 0.2;


        if (leafBlocks.contains(rootPos.west())) {skipChance+=0.25;}
        if (leafBlocks.contains(rootPos.above())) {skipChance+=0.4;}
        if (leafBlocks.contains(rootPos.north())) {skipChance+=0.25;}

        if (skipChance > 1){skipChance = 1;}
        if (skipChance < 0){skipChance = 0;}
        if (branchChance > 1){branchChance = 1;}
        if (branchChance < 0){branchChance = 0;}


        if (random.nextFloat() < skipChance){
            return 0;
        }
        if (random.nextFloat() < branchChance){
            return 3;
        }

        if (skipChance < 0.2) {return 2;}
        return 1;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int dx, int y, int dz, int currentRadius, boolean doubleTrunk) {
        return dx*dx + dz*dz > currentRadius*currentRadius;
    }

    protected boolean tryPlaceDeepLeaf(
            WorldGenLevel level, FoliagePlacer.FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, BlockPos pos
    ) {
        boolean isPersistent = level.isStateAtPosition(pos, state -> state.getValueOrElse(BlockStateProperties.PERSISTENT, false));
        if (!isPersistent && TreeFeature.validTreePos(level, pos)) {
            BlockState foliageState = this.deepFoliageBlock.get().defaultBlockState();
            if (foliageState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                foliageState = foliageState.setValue(
                        BlockStateProperties.WATERLOGGED, level.isFluidAtPosition(pos, fluidState -> fluidState.isSourceOfType(Fluids.WATER))
                );
            }

            foliageSetter.set(pos, foliageState);
            return true;
        } else {
            return false;
        }
    }

    protected boolean tryPlaceFenceBlock(
            WorldGenLevel level, FoliagePlacer.FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, BlockPos pos
    ) {
        boolean isPersistent = level.isStateAtPosition(pos, state -> state.getValueOrElse(BlockStateProperties.PERSISTENT, false));
        if (!isPersistent && TreeFeature.validTreePos(level, pos)) {
            BlockState foliageState = this.fenceBlock.get().defaultBlockState();
            if (foliageState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                foliageState = foliageState.setValue(
                        BlockStateProperties.WATERLOGGED, level.isFluidAtPosition(pos, fluidState -> fluidState.isSourceOfType(Fluids.WATER))
                );
            }

            foliageSetter.set(pos, foliageState);
            return true;
        } else {
            return false;
        }
    }
}
