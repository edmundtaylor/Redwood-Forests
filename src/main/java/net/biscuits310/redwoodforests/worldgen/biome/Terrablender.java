package net.biscuits310.redwoodforests.worldgen.biome;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.minecraft.resources.Identifier;
import terrablender.api.Regions;

public class Terrablender {
    public static void registerBiomes() {
        Regions.register(new OverworldRegion(Identifier.fromNamespaceAndPath(RedwoodForests.MODID, "overworld"), 4));
    }
}
