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

    protected final Vec3i[] treeVectors = {new Vec3i(0, 0, 0), new Vec3i(0, 0, 1), new Vec3i(1, 0, 0), new Vec3i(1, 0, 1)};

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

        boolean[] fenceState = {false, false, false, false};
        int fencePos = -1;

        for (int hh = 0; hh < treeHeight + 5; hh++){
            if (hh == 0)
                this.giantTrunkPlacer(level, trunkSetter, originBlockSetter, fenceSetter, random, trunkPos, config, origin, hh, 0F, true, fenceState, -2);
            else if (hh < treeHeight * (1-this.fenceProportion))
                this.giantTrunkPlacer(level, trunkSetter, originBlockSetter, fenceSetter, random, trunkPos, config, origin, hh, 0F, false, fenceState, -2);
            else if (hh < treeHeight)
                this.giantTrunkPlacer(level, trunkSetter, originBlockSetter, fenceSetter, random, trunkPos, config, origin, hh, this.progressChance, false, fenceState, -2);
            else
                this.giantTrunkPlacer(level, trunkSetter, originBlockSetter, fenceSetter, random, trunkPos, config, origin, hh, 0F, false, fenceState, fencePos);
        }
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(origin.above(treeHeight), 0, false));
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
            boolean[] fenceState,
            int fencePos
    ){
        for (int i = 0; i < this.treeVectors.length; i++){
            trunkPos.setWithOffset(treePos, this.treeVectors[i].offset(0, y, 0));
            if (fencePos == -1)
                fencePos = random.nextIntBetweenInclusive(0, 3);
            if (i == fencePos){
                this.placeLogIfFree(level, fenceSetter, random, trunkPos, config);
                continue;
            }
            if (fencePos != -2)
                continue;
            if (originLevel && i == 0){
                this.placeLogIfFree(level, originBlockSetter, random, trunkPos, config);
                continue;
            }
            if (fenceState[i]){
                this.placeLogIfFree(level, fenceSetter, random, trunkPos, config );
                continue;
            }
            if (random.nextFloat() < progressChance) {
                fenceState[i] = true;
                this.placeLogIfFree(level, fenceSetter, random, trunkPos, config);
            }
            else {
                this.placeLogIfFree(level, trunkSetter, random, trunkPos, config);
            }
        }
    }
}
