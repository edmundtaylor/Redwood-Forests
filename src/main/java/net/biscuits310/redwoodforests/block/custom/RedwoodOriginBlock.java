package net.biscuits310.redwoodforests.block.custom;

import net.biscuits310.redwoodforests.block.ModBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Supplier;

public class RedwoodOriginBlock extends RedwoodLogBlock{
    public static final IntegerProperty GROWTH_STAGE = ModBlockStateProperties.GROWTH_STAGE;
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    protected final TreeGrower treeGrower;

    public RedwoodOriginBlock(int flammability, int fireSpreadSpeed, Supplier<Block> strippedBlock, TreeGrower treeGrower, Properties properties) {
        super(flammability, fireSpreadSpeed, strippedBlock, properties.randomTicks());
        this.treeGrower = treeGrower;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PREVENTS_NEARBY_LEAF_DECAY, true)
                .setValue(NATURAL_LOG, true)
                .setValue(AXIS, Direction.Axis.Y)
                .setValue(GROWTH_STAGE, 0)
                .setValue(STAGE, 0));
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;
        if (random.nextInt(7) == 0) {
            this.advanceTree(level, pos, state, random);
        }
    }

    protected void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random){
        if (state.getValue(STAGE) == 0){
            level.setBlock(pos, state.cycle(STAGE), 260);
        } else {
            RandomSource growthRandom = RandomSource.create(level.getSeed() ^ pos.asLong());
            this.treeGrower.growTree(level, level.getChunkSource().getGenerator(), pos, state, growthRandom);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGE).add(GROWTH_STAGE);
        super.createBlockStateDefinition(builder);
    }
}
