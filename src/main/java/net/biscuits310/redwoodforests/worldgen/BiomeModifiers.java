package net.biscuits310.redwoodforests.worldgen;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

// UNUSED - Creates BiomeModifiers
// BiomeModifiers edit existing biomes to include custom PlacedFeatures
public class BiomeModifiers {

    // Actions to be executed
    public static void boostrap(BootstrapContext<BiomeModifier> context) {
        // Get the placed features
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        // Get the vanilla biomes
        var biomes = context.lookup(Registries.BIOME);
    }

    // Register a biome modifiers key
    private static ResourceKey<BiomeModifier> registerKey(String name){
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(RedwoodForests.MODID, name));
    }
}
