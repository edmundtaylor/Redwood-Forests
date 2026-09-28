package net.biscuits310.redwoodforests.block.custom;

import net.biscuits310.redwoodforests.block.ModBlockStateProperties;
import net.biscuits310.redwoodforests.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.OptionalInt;

public class RedwoodFenceBlock extends FlammableFenceBlock{

    public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE;

    public RedwoodFenceBlock(int flammability, int fireSpreadSpeed, Properties properties) {
        super(flammability, fireSpreadSpeed, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, 7).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlock(pos, updateDistanceWithDiagonal(state, level, pos), 3);
    }

    private static BlockState updateDistanceWithDiagonal(BlockState state, LevelAccessor level, BlockPos pos) {
        // The default distance is 7, the maximum possible distance. Any updated distances will be lower than this.
        int newDistance = 7;
        // Creates a block position.
        BlockPos.MutableBlockPos neighborPos = new BlockPos.MutableBlockPos();

        // These for loops loop through all of the neighbouring blocks, including the diagonal blocks. The difference in x y and z is set as dx dy and dz.
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    // Ignore the update if the block being checked is the block being updated.
                    if (dx == 0 && dy == 0 && dz == 0) {
                        continue;
                    }

                    // Set the block position indicating the block to be checked to its coordinate position using the update block and coordinate offsets.
                    neighborPos.setWithOffset(pos, dx, dy, dz);
                    // The new distance is the smaller of the current distance and the distance stored by the neighbouring block + 1.
                    newDistance = Math.min(newDistance, getBlockStateDistanceAt(level.getBlockState(neighborPos)) + 1);
                    // If the new distance is 1, it cannot be smaller, so therefore the loop can stop. Easy optimisation!
                    if (newDistance == 1) {
                        // Return the state by updating the block's current state, but changing the distance to 1.
                        return state.setValue(DISTANCE, 1);
                    }
                }
            }
        }
        return state.setValue(DISTANCE, newDistance);
    }

    // If the distance found returns as empty, make it 7.
    private static int getBlockStateDistanceAt (BlockState state) {
        return getOptionalDistanceBlockstate(state).orElse(7);
    }

    // This returns an optinalint, which can be empty or an integer. If found empty, the wrapper sets to 7.
    public static OptionalInt getOptionalDistanceBlockstate(BlockState state){
        // If the block being checked is a redwood log
        if (state.is(ModBlocks.REDWOOD_LOG)){
            // if the blockstate the block prevents nearby leaf decay is true (note that this is specifically my custom blockstate which can be disabled)
            if (state.getValue(ModBlockStateProperties.PREVENTS_NEARBY_LEAF_DECAY)){
                // Return the distance of 0, which means that consecutive leaves will have a distance of 1.
                return OptionalInt.of(0);
            }
            // If the log does not have the prevents leaf decay blockstate
            else {
                // Return the distance of the block if it has the distance property. If it does not, return empty.
                return state.hasProperty(DISTANCE) ? OptionalInt.of(state.getValue(DISTANCE)) : OptionalInt.empty();
            }
        }
        // If the block being checked is not a redwood log
        else {
            // If the block being checked has the vanilla leaf decay property (such as a birch log)
            if (state.is(BlockTags.PREVENTS_NEARBY_LEAF_DECAY)){
                // Return 0
                return  OptionalInt.of(0);
            }
            // If the block being checked does not have the leaf decay property
            else {
                // Return the distance of the block if it has the distance property. If it does not, return empty.
                return state.hasProperty(DISTANCE) ? OptionalInt.of(state.getValue(DISTANCE)) : OptionalInt.empty();
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(DISTANCE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState replacedFluidState = context.getLevel().getFluidState(context.getClickedPos());
        BlockPos north = pos.north();
        BlockPos east = pos.east();
        BlockPos south = pos.south();
        BlockPos west = pos.west();
        BlockState northState = level.getBlockState(north);
        BlockState eastState = level.getBlockState(east);
        BlockState southState = level.getBlockState(south);
        BlockState westState = level.getBlockState(west);
        return updateDistanceWithDiagonal(defaultBlockState(), context.getLevel(), pos)
                .setValue(NORTH, this.connectsTo(northState, northState.isFaceSturdy(level, north, Direction.SOUTH), Direction.SOUTH))
                .setValue(EAST, this.connectsTo(eastState, eastState.isFaceSturdy(level, east, Direction.WEST), Direction.WEST))
                .setValue(SOUTH, this.connectsTo(southState, southState.isFaceSturdy(level, south, Direction.NORTH), Direction.NORTH))
                .setValue(WEST, this.connectsTo(westState, westState.isFaceSturdy(level, west, Direction.EAST), Direction.EAST))
                .setValue(WATERLOGGED, replacedFluidState.is(Fluids.WATER));
    }
}
