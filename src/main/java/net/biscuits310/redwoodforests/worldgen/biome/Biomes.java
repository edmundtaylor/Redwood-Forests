package net.biscuits310.redwoodforests.worldgen.biome;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.entity.ModEntities;
import net.biscuits310.redwoodforests.worldgen.PlacedFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;


public class Biomes {
    public static final ResourceKey<Biome> REDWOOD_FOREST = ResourceKey.create(Registries.BIOME,
            Identifier.fromNamespaceAndPath(RedwoodForests.MODID, "redwood_forest"));

    public static void bootstrap(BootstrapContext<Biome> context) {
        context.register(REDWOOD_FOREST, redwoodForest(context));
    }

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    private static Biome redwoodForest(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.farmAnimals(spawnBuilder);
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);
        spawnBuilder.addSpawn(MobCategory.AMBIENT, 5, new MobSpawnSettings.SpawnerData(ModEntities.BANANA_SLUG.get(), 1, 2));

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addMossyStoneBlock(biomeBuilder);
        BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
        BiomeDefaultFeatures.addFerns(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addBirchForestFlowers(biomeBuilder);
        BiomeDefaultFeatures.addBushes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addExtraGold(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.REDWOOD_PLACED_KEY);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.REDWOOD_1_PLACED_KEY);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.REDWOOD_2_PLACED_KEY);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.REDWOOD_3_PLACED_KEY);

        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder, true);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(1.2f)
                .temperature(0.93f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(0xff3f76e4)
                        .foliageColorOverride(0xCC80B348)
                        .grassColorOverride(0xCC80B348)
                        .build())
                .build();
    }
}
