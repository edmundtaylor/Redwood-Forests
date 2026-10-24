package net.biscuits310.redwoodforests;

import net.biscuits310.redwoodforests.datagen.*;
import net.biscuits310.redwoodforests.worldgen.ModConfiguredFeatures;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;

// Uses an event bus to create a generator
@EventBusSubscriber(modid = RedwoodForests.MODID)
public class RedwoodForestsDataGen
{
    // Creates the generator and adds providers
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event)
    {
        // Creates the generator
        DataGenerator generator = event.getGenerator();
        // Creates the pack output of the generator
        PackOutput packOutput = generator.getPackOutput();
        // Gets the lookup provider of the event
        var lookupProvider = event.getLookupProvider();

        // Adds all the providers fot the different classes
        generator.addProvider(true, new ModModelProvider(packOutput));
        generator.addProvider(true, new ModBlockTagsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));
        generator.addProvider(true, new ModRecipeProvider.Runner(packOutput,lookupProvider));
        generator.addProvider(true, new ModItemTagsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModDataPackProvider(packOutput, lookupProvider));
        generator.addProvider(true, new FluidTagsProvider(packOutput, lookupProvider));
    }
}
