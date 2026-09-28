package net.biscuits310.redwoodforests.event;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.block.ModBlocks;
import net.biscuits310.redwoodforests.block.custom.RedwoodFenceBlock;
import net.biscuits310.redwoodforests.block.custom.RedwoodLeavesBlock;
import net.biscuits310.redwoodforests.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = RedwoodForests.MODID)
public class LeafDecayEvent {

    public static void tickDiagonalRedwoodLeavesAndFences(ServerLevel level, BlockPos pos){
        for (int dx = -1; dx <= 1; dx++){
            for (int dy = -1; dy <= 1; dy++){
                for (int dz = -1; dz <= 1; dz++){
                    int adjacency = 0;
                    if (dx == 0) {adjacency++;}
                    if (dy == 0) {adjacency++;}
                    if (dz == 0) {adjacency++;}
                    if (adjacency >= 2) {continue;}

                    BlockPos blockUpdatePos = pos.offset(dx, dy, dz);
                    BlockState blockUpdateState = level.getBlockState(blockUpdatePos);
                    if (blockUpdateState.getBlock() instanceof RedwoodLeavesBlock || blockUpdateState.getBlock() instanceof RedwoodFenceBlock){
                        level.scheduleTick(blockUpdatePos, blockUpdateState.getBlock(), 1);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBreak(BlockEvent.BreakEvent event){
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        if (!(event.getState().is(BlockTags.PREVENTS_NEARBY_LEAF_DECAY))) return;

        tickDiagonalRedwoodLeavesAndFences(level, event.getPos());
    }

    @SubscribeEvent
    public static void onNeighbourUpdates(BlockEvent.NeighborNotifyEvent event){
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        if (!((event.getState().is(BlockTags.LEAVES)) || (event.getState().is(ModBlocks.REDWOOD_FENCE)) || (event.getState().is(ModTags.Blocks.REWDWOOD_LOGS)))) return;

        tickDiagonalRedwoodLeavesAndFences(level, event.getPos());
    }
}
