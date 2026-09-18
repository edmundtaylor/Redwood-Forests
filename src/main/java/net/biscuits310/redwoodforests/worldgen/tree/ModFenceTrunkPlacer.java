package net.biscuits310.redwoodforests.worldgen.tree;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.biscuits310.redwoodforests.block.ModBlockStateProperties;
import net.biscuits310.redwoodforests.block.custom.RedwoodOriginBlock;
import net.minecraft.core.BlockPos;
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

public class ModFenceTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<ModFenceTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(i -> trunkPlacerParts(i)
            .and(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("fence_block").forGetter(p -> p.fenceBlock.get()))
            .and(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("origin_block").forGetter(p -> p.originBlock.get()))
            .and(Codec.floatRange(0F, 1F).fieldOf("fenceProportion").forGetter(p -> p.fenceProportion))
            .and(Codec.intRange(0, 3).fieldOf("growth_stage").forGetter(p -> p.growthStage))
            .apply(i, (baseHeight, heightRandA, heightRandB, fenceBlock, originBlock, fenceProportion, growthStage) ->
                    new ModFenceTrunkPlacer(
                            baseHeight,
                            heightRandA,
                            heightRandB,
                            () -> fenceBlock,
                            () -> originBlock,
                            fenceProportion,
                            growthStage)));

    protected final float fenceProportion;
    protected final int growthStage;
    protected final Supplier<Block> fenceBlock;
    protected final Supplier<Block> originBlock;

    public ModFenceTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, Supplier<Block> fenceBlock, Supplier<Block> originBlock, float fenceProportion, int growthStage){
        super(baseHeight, heightRandA, heightRandB);
        this.fenceBlock = fenceBlock;
        this.originBlock = originBlock;
        this.fenceProportion = fenceProportion;
        this.growthStage = growthStage;
    }

    public int getGrowthStage(){
        return this.growthStage;
    }

    @Override
    protected TrunkPlacerType type() {
        return ModTrunkPlacerType.FENCE_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(WorldGenLevel level, BiConsumer<BlockPos, BlockState> trunkSetter, RandomSource random, int treeHeight, BlockPos origin, TreeConfiguration config) {
        placeBelowTrunkBlock(level, trunkSetter, random, origin.below(), config);
        BlockPos.MutableBlockPos trunkPos = new BlockPos.MutableBlockPos();

        BiConsumer<BlockPos, BlockState> fenceSetter =
                (blockPos, state) -> trunkSetter.accept(blockPos, this.fenceBlock.get().defaultBlockState());

        BiConsumer<BlockPos, BlockState> originBlockSetter =
                (blockPos, state) -> trunkSetter.accept(blockPos, this.originBlock.get().defaultBlockState()
                        .setValue(ModBlockStateProperties.GROWTH_STAGE, this.growthStage + 1));

        for (int hh = 0; hh < treeHeight; hh++){
            trunkPos.setWithOffset(origin, 0, hh, 0);
            if (hh == 0){
                this.placeLog(level, originBlockSetter, random, trunkPos, config);
            }
            else if (hh < treeHeight * (1-this.fenceProportion)) {
                this.placeLog(level, trunkSetter, random, trunkPos, config);
            }
            else {
                this.placeLog(level, fenceSetter, random, trunkPos, config);
            }
        }
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(origin.above(treeHeight), 0, false));
    }
}
