package net.biscuits310.redwoodforests.event;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.entity.ModEntities;
import net.biscuits310.redwoodforests.entity.custom.BananaSlugEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = RedwoodForests.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.BANANA_SLUG.get(), BananaSlugEntity.createAttributes().build());
    }
}
