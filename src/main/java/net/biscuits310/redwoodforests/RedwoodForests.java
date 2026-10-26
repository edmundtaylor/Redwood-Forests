package net.biscuits310.redwoodforests;

import net.biscuits310.redwoodforests.block.Blocks;
import net.biscuits310.redwoodforests.creativemodetab.ModCreativeModeTabs;
import net.biscuits310.redwoodforests.entity.ModEntities;
import net.biscuits310.redwoodforests.fluid.Fluids;
import net.biscuits310.redwoodforests.item.Items;
import net.biscuits310.redwoodforests.worldgen.ModFeatures;
import net.biscuits310.redwoodforests.worldgen.biome.Terrablender;
import net.biscuits310.redwoodforests.worldgen.biome.surface.CustomSurfaceRules;
import net.biscuits310.redwoodforests.worldgen.tree.ModFoliagePlacerType;
import net.biscuits310.redwoodforests.worldgen.tree.ModTrunkPlacerType;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.neoforge.transfer.fluid.DispenseFluidContainer;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import terrablender.api.SurfaceRuleManager;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(RedwoodForests.MODID)
public class RedwoodForests {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "redwoodforests";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public RedwoodForests(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for mod loading
        modEventBus.addListener(this::commonSetup);
        // Register creative mode tabs
        ModCreativeModeTabs.register(modEventBus);
        // Register items
        Items.register(modEventBus);
        // Register blocks
        Blocks.register(modEventBus);

        Fluids.FLUIDS.register(modEventBus);
        Fluids.FLUID_TYPES.register(modEventBus);

        ModEntities.register(modEventBus);

        ModTrunkPlacerType.TRUNK_PLACER_TYPES.register(modEventBus);
        ModFoliagePlacerType.FOLIAGE_PLACER_TYPES.register(modEventBus);

        ModFeatures.FEATURES.register(modEventBus);

        Terrablender.registerBiomes();
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (RedwoodForests) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);


    }

    // common setup
    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> DispenserBlock.registerBehavior(Items.HYDROGEL_BUCKET, DispenseFluidContainer.getInstance()));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MODID, CustomSurfaceRules.makeRules());
    }

    // UNUSED for adding items to vanilla creative mode tabs
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
