package net.biscuits310.redwoodforests.worldgen.biome.surface;

import net.biscuits310.redwoodforests.worldgen.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class CustomSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource MOSS = makeStateRule(Blocks.MOSS_BLOCK);
    private static final SurfaceRules.RuleSource PODZOL = makeStateRule(Blocks.PODZOL);

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);

        return SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.REDWOOD_FOREST),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)));
    }


    private static net.minecraft.world.level.levelgen.SurfaceRules.RuleSource makeStateRule(Block block) {
        return net.minecraft.world.level.levelgen.SurfaceRules.state(block.defaultBlockState());
    }
}
