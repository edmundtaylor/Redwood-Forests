package net.biscuits310.redwoodforests.event;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.block.ModBlocks;
import net.biscuits310.redwoodforests.block.custom.RedwoodFenceBlock;
import net.biscuits310.redwoodforests.block.custom.RedwoodLeavesBlock;
import net.biscuits310.redwoodforests.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.HashSet;
import java.util.Set;

import static net.biscuits310.redwoodforests.block.custom.RedwoodLogBlock.*;

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

    private static void updateConnectedLeafDecay(ServerLevel level, BlockPos rootPos, Set<BlockPos> checkedBlocks){
        BlockPos.MutableBlockPos neighbourPos = new BlockPos.MutableBlockPos();
        for (Direction direction : Direction.values()){
            neighbourPos.setWithOffset(rootPos, direction);
            BlockState neighbourState = level.getBlockState(neighbourPos);

            if (!checkedBlocks.contains(neighbourPos) && (neighbourState.is(ModTags.Blocks.REWDWOOD_LOGS))) {
                checkedBlocks.add(neighbourPos);
                if (neighbourState.getValue(NATURAL_LOG) && neighbourState.getValue(PREVENTS_NEARBY_LEAF_DECAY)){
                    level.setBlock(neighbourPos, neighbourState.setValue(PREVENTS_NEARBY_LEAF_DECAY, false), 3);
                    LeafDecayEvent.tickDiagonalRedwoodLeavesAndFences(level, neighbourPos);
                    updateConnectedLeafDecay(level, neighbourPos, checkedBlocks);
                }
            }
        }
    }

    public static void destructUpdate(BlockState state, ServerLevel level, BlockPos pos){
        if (!state.getValue(NATURAL_LOG)) {return;}

        Set<BlockPos> checkedBlocks = new HashSet<>();
        updateConnectedLeafDecay(level, pos, checkedBlocks);
    }

    @SubscribeEvent
    public static void onBreak(BlockEvent.BreakEvent event){
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        BlockState state = event.getState();
        if (!(state.is(BlockTags.PREVENTS_NEARBY_LEAF_DECAY))) return;
        if (state.is(ModTags.Blocks.REWDWOOD_LOGS))
            destructUpdate(state, level, event.getPos());

        tickDiagonalRedwoodLeavesAndFences(level, event.getPos());
    }

    @SubscribeEvent
    public static void onNeighbourUpdates(BlockEvent.NeighborNotifyEvent event){
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        if (!((event.getState().is(BlockTags.LEAVES)) || (event.getState().is(ModBlocks.REDWOOD_FENCE)) || (event.getState().is(ModTags.Blocks.REWDWOOD_LOGS)))) return;

        tickDiagonalRedwoodLeavesAndFences(level, event.getPos());
    }
}
