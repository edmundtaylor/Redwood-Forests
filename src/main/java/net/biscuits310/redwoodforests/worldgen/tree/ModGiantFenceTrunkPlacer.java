package net.biscuits310.redwoodforests.worldgen.tree;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.biscuits310.redwoodforests.block.ModBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModGiantFenceTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<ModGiantFenceTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(i -> trunkPlacerParts(i)
            .and(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("fence_block").forGetter(p -> p.fenceBlock.get()))
            .and(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("origin_block").forGetter(p -> p.originBlock.get()))
            .and(Codec.floatRange(0F, 1F).fieldOf("fenceProportion").forGetter(p -> p.fenceProportion))
            .and(Codec.floatRange(0F, 1F).fieldOf("progressChance").forGetter(p -> p.progressChance))
            .and(Codec.intRange(0, 3).fieldOf("growth_stage").forGetter(p -> p.growthStage))
            .apply(i, (baseHeight, heightRandA, heightRandB, fenceBlock, originBlock, fenceProportion, progressChance, growthStage) ->
                    new ModGiantFenceTrunkPlacer(
                            baseHeight,
                            heightRandA,
                            heightRandB,
                            () -> fenceBlock,
                            () -> originBlock,
                            fenceProportion,
                            progressChance,
                            growthStage)));

    protected final float fenceProportion;
    protected final float progressChance;
    protected final int growthStage;
    protected final Supplier<Block> fenceBlock;
    protected final Supplier<Block> originBlock;

    protected final Vec3i[] treeVectors = {new Vec3i(0, 0, 0), new Vec3i(0, 0, 1), new Vec3i(1, 0, 0), new Vec3i(1, 0, 1),
    new Vec3i(0, 0, -1), new Vec3i(1, 0, -1), new Vec3i(-1, 0, 0), new Vec3i(2, 0, 0), new Vec3i(-1, 0, 1), new Vec3i(2, 0, 1), new Vec3i(0, 0, 2), new Vec3i(1, 0, 2)};

    public ModGiantFenceTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, Supplier<Block> fenceBlock, Supplier<Block> originBlock, float fenceProportion, float progressChance, int growthStage) {
        super(baseHeight, heightRandA, heightRandB);
        this.fenceBlock = fenceBlock;
        this.originBlock = originBlock;
        this.fenceProportion = fenceProportion;
        this.progressChance = progressChance;
        this.growthStage = growthStage;
    }

    public int getGrowthStage() {return this.growthStage;}

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerType.GIANT_FENCE_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(WorldGenLevel level, BiConsumer<BlockPos, BlockState> trunkSetter, RandomSource random, int treeHeight, BlockPos origin, TreeConfiguration config) {
        BlockPos below = origin.below();
        placeBelowTrunkBlock(level, trunkSetter, random, below, config);
        placeBelowTrunkBlock(level, trunkSetter, random, below.east(), config);
        placeBelowTrunkBlock(level, trunkSetter, random, below.south(), config);
        placeBelowTrunkBlock(level, trunkSetter, random, below.south().east(), config);
        BlockPos.MutableBlockPos trunkPos = new BlockPos.MutableBlockPos();

        BiConsumer<BlockPos, BlockState> fenceSetter =
                (blockPos, state) -> trunkSetter.accept(blockPos, this.fenceBlock.get().defaultBlockState());

        BiConsumer<BlockPos, BlockState> originBlockSetter =
                (blockPos, state) -> trunkSetter.accept(blockPos, this.originBlock.get().defaultBlockState()
                        .trySetValue(ModBlockStateProperties.GROWTH_STAGE, this.growthStage + 1)
                        .trySetValue(ModBlockStateProperties.TREE_HEIGHT, treeHeight));

        int[] progressState = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int fencePos = -1;

        for (int hh = 0; hh < treeHeight; hh++){
            if (hh == 0)
                this.giantTrunkPlacer(level, trunkSetter, originBlockSetter, fenceSetter, random, trunkPos, config, origin, hh, 0F, true, progressState);
            else if (hh < treeHeight * (1-this.fenceProportion))
                this.giantTrunkPlacer(level, trunkSetter, originBlockSetter, fenceSetter, random, trunkPos, config, origin, hh, 0F, false, progressState);
            else if (hh < treeHeight)
                this.giantTrunkPlacer(level, trunkSetter, originBlockSetter, fenceSetter, random, trunkPos, config, origin, hh, this.progressChance, false, progressState);
        }
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(origin.above(treeHeight), 0, true));
    }

    private void giantTrunkPlacer(
            WorldGenLevel level,
            BiConsumer<BlockPos, BlockState> trunkSetter,
            BiConsumer<BlockPos, BlockState> originBlockSetter,
            BiConsumer<BlockPos, BlockState> fenceSetter,
            RandomSource random,
            BlockPos.MutableBlockPos trunkPos,
            TreeConfiguration config,
            BlockPos treePos,
            int y,
            float progressChance,
            boolean originLevel,
            int[] progressState
    ){
        for (int i = 0; i < this.treeVectors.length; i++){
            trunkPos.setWithOffset(treePos, this.treeVectors[i].offset(0, y, 0));
            if (originLevel){
                if (i == 0 && random.nextFloat() < 0.75){
                    this.placeLogIfFree(level, originBlockSetter, random, trunkPos, config);
                    continue;
                }
                this.placeLogIfFree(level, trunkSetter, random, trunkPos, config);
                continue;
            }
            if (i > 3){
                if (progressState[i] != 1){
                    if (random.nextFloat() < 0.5){
                        progressState[i]++;
                        continue;
                    }
                    this.placeLogIfFree(level, trunkSetter, random, trunkPos, config);
                }
                continue;
            }
            if (progressState[i] == 2)
                continue;
            if (progressState[i] == 1){
                this.placeLogIfFree(level, fenceSetter, random, trunkPos, config );
            }
            if (random.nextFloat() < progressChance) {
                progressState[i]++;
                if (progressState[i] == 2)
                    continue;
                this.placeLogIfFree(level, fenceSetter, random, trunkPos, config);
            }
            this.placeLogIfFree(level, trunkSetter, random, trunkPos, config);
        }
    }

    @Override
    protected void placeLogIfFree(WorldGenLevel level, BiConsumer<BlockPos, BlockState> trunkSetter, RandomSource random, BlockPos.MutableBlockPos pos, TreeConfiguration config) {
        if (this.isFree(level, pos)) {
            this.placeLog(level, trunkSetter, random, pos, config);
            level.updateNeighborsAt(pos, level.getBlockState(pos).getBlock());
        }
    }
}
