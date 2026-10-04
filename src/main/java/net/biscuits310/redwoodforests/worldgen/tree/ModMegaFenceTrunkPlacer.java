package net.biscuits310.redwoodforests.worldgen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.function.Supplier;

public class ModMegaFenceTrunkPlacer extends ModGiantFenceTrunkPlacer{
    public static final MapCodec<ModMegaFenceTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(i -> trunkPlacerParts(i)
            .and(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("fence_block").forGetter(p -> p.fenceBlock.get()))
            .and(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("origin_block").forGetter(p -> p.originBlock.get()))
            .and(Codec.floatRange(0F, 1F).fieldOf("fenceProportion").forGetter(p -> p.fenceProportion))
            .and(Codec.floatRange(0F, 1F).fieldOf("progressChance").forGetter(p -> p.progressChance))
            .and(Codec.intRange(0, 3).fieldOf("growth_stage").forGetter(p -> p.growthStage))
            .apply(i, (baseHeight, heightRandA, heightRandB, fenceBlock, originBlock, fenceProportion, progressChance, growthStage) ->
                    new ModMegaFenceTrunkPlacer(
                            baseHeight,
                            heightRandA,
                            heightRandB,
                            () -> fenceBlock,
                            () -> originBlock,
                            fenceProportion,
                            progressChance,
                            growthStage)));

    protected final Vec3i[] megaTreeVectors = {
            new Vec3i(0, 0, 0), new Vec3i(0, 0, 1), new Vec3i(1, 0, 0), new Vec3i(1, 0, 1), new Vec3i(-1, 0, 1), new Vec3i(-1, 0, 0), new Vec3i(0, 0, -1), new Vec3i(1, 0, -1), new Vec3i(-1, 0, -1),
            new Vec3i(-1, 0, 2), new Vec3i(0, 0, 2), new Vec3i(1, 0, 2), new Vec3i(-2, 0, 1), new Vec3i(2, 0, 1), new Vec3i(-2, 0, 0), new Vec3i(2, 0, 0), new Vec3i(-2, 0, -1), new Vec3i(2, 0, -1), new Vec3i(-1, 0, -2), new Vec3i(0, 0, -2), new Vec3i(1, 0, -2)};

    public ModMegaFenceTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, Supplier<Block> fenceBlock, Supplier<Block> originBlock, float fenceProportion, float progressChance, int growthStage) {
        super(baseHeight, heightRandA, heightRandB, fenceBlock, originBlock, fenceProportion, progressChance, growthStage);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerType.MEGA_FENCE_TRUNK_PLACER.get();
    }

    @Override
    protected Vec3i[] getTreeVectors() {
        return this.megaTreeVectors;
    }

    @Override
    public int getNumBaseTrunkBlocks() {
        return 9;
    }
}
