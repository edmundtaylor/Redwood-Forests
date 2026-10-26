package net.biscuits310.redwoodforests.event;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.entity.ModEntities;
import net.biscuits310.redwoodforests.entity.custom.BananaSlug;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = RedwoodForests.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.BANANA_SLUG.get(), BananaSlug.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
                ModEntities.BANANA_SLUG.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, level, spawnType, pos, random) -> {
                    boolean validSurface = level.getBlockState(pos.below()).is(BlockTags.VALID_SPAWN);
                    boolean brightEnough = level.getRawBrightness(pos, 0) >= 4;
                    return validSurface && brightEnough;
                },
                RegisterSpawnPlacementsEvent.Operation.OR);
    }
}
