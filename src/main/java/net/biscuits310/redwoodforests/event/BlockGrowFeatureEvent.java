package net.biscuits310.redwoodforests.event;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.block.custom.RedwoodOriginBlock;
import net.biscuits310.redwoodforests.worldgen.tree.ModTreeGrowthData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = RedwoodForests.MODID)
public class BlockGrowFeatureEvent {

    @SubscribeEvent
    public static void blockGrowFeatureEvent(net.neoforged.neoforge.event.level.BlockGrowFeatureEvent event){
        BlockPos currentPos = event.getPos();
        BlockState state = event.getLevel().getBlockState(currentPos);
        if (!(state.getBlock() instanceof RedwoodOriginBlock)) return;
        int treeHeight = state.getValue(RedwoodOriginBlock.TREE_HEIGHT);

        ModTreeGrowthData.TEMPORARY_TREE_HEIGHTS.put(currentPos, treeHeight);
    }
}
