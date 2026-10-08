package net.biscuits310.redwoodforests.creativemodetab;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.block.ModBlocks;
import net.biscuits310.redwoodforests.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

// Creates creative mode tabs and combines them into a DeferredRegister
public class ModCreativeModeTabs
{
    // Create a DeferredRegister with the creative mode tabs register
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RedwoodForests.MODID);

    // Creates a creative mode tab supplier for a redwood forests tab
    public static final Supplier<CreativeModeTab> REDWOOD_FORESTS_TAB = CREATIVE_MODE_TABS.register("redwood_forests_tab",
            // Set the creative mode tab icon as the texture of redwood bark
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.REDWOOD_BARK.get()))
                    // Set the title as Redwood Forests
                    .title(Component.translatable("creativetab.redwoodforests.redwood_forests"))
                    // Create the tab to the right of the redstone blocks tab
                    .withTabsBefore(CreativeModeTabs.REDSTONE_BLOCKS)
                    // Applies all the items and blocks that should be inside the redwood forests tab
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.REDWOOD_BARK);
                        output.accept(ModItems.CHARRED_REDWOOD_BARK);
                        output.accept(ModBlocks.REDWOOD_PLANKS);
                        output.accept(ModBlocks.REDWOOD_LOG);
                        output.accept(ModBlocks.STRIPPED_REDWOOD_LOG);
                        output.accept(ModBlocks.REDWOOD_WOOD);
                        output.accept(ModBlocks.STRIPPED_REDWOOD_WOOD);
                        output.accept(ModBlocks.REDWOOD_STAIRS);
                        output.accept(ModBlocks.REDWOOD_SLAB);
                        output.accept(ModBlocks.REDWOOD_FENCE);
                        output.accept(ModBlocks.REDWOOD_LEAVES);
                        output.accept(ModBlocks.REDWOOD_SAPLING);
                        output.accept(ModBlocks.DEEP_REDWOOD_LEAVES);
                        output.accept(ModItems.BANANA_SLUG_SPAWN_EGG);
                    })
                    .build());

    // Register the CREATIVE_MODE_TABS register using the event bus
    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
