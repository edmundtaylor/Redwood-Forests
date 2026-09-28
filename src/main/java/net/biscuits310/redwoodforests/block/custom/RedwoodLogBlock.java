package net.biscuits310.redwoodforests.block.custom;

import net.biscuits310.redwoodforests.block.ModBlockStateProperties;
import net.biscuits310.redwoodforests.block.ModBlocks;
import net.biscuits310.redwoodforests.event.LeafDecayEvent;
import net.biscuits310.redwoodforests.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.function.Supplier;

public class RedwoodLogBlock extends StrippableFlammableRotatedPillarBlock{
    public static final BooleanProperty PREVENTS_NEARBY_LEAF_DECAY = ModBlockStateProperties.PREVENTS_NEARBY_LEAF_DECAY;
    public static final BooleanProperty NATURAL_LOG = ModBlockStateProperties.NATURAL_LOG;

    public RedwoodLogBlock(int flammability, int fireSpreadSpeed, Supplier<Block> strippedBlock, Properties properties) {
        super(flammability, fireSpreadSpeed, strippedBlock, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PREVENTS_NEARBY_LEAF_DECAY, true).setValue(NATURAL_LOG, true) .setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(NATURAL_LOG, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PREVENTS_NEARBY_LEAF_DECAY).add(NATURAL_LOG);
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

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
        if (!state.getValue(NATURAL_LOG) || movedByPiston) {return;}

        Set<BlockPos> checkedBlocks = new HashSet<>();
        updateConnectedLeafDecay(level, pos, checkedBlocks);

        super.affectNeighborsAfterRemoval(state, level, pos, movedByPiston);
    }
}
