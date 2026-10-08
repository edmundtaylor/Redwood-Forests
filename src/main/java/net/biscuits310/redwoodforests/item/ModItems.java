package net.biscuits310.redwoodforests.item;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.entity.ModEntities;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// Creates DeferredItems and combines them into a DeferredRegister
public class  ModItems
{
    // Combined DeferredItems into a DeferredRegister
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RedwoodForests.MODID);

    public static final DeferredItem<Item> BANANA_SLUG_SPAWN_EGG = ITEMS.registerItem("banana_slug_spawn_egg",
            properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.BANANA_SLUG.get())));

    // Creates redwood bark as a simple item, defining the id
    public static final DeferredItem<Item> REDWOOD_BARK = ITEMS.registerSimpleItem("redwood_bark");
    // Creates charred redwood bark as a simple item, defining the id
    public static final DeferredItem<Item> CHARRED_REDWOOD_BARK = ITEMS.registerSimpleItem("charred_redwood_bark");

    // Registers the ITEMS DeferredRegister using the event bus
    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
