package net.biscuits310.redwoodforests.block;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ModBlockStateProperties {
    public static final BooleanProperty PREVENTS_NEARBY_LEAF_DECAY = BooleanProperty.create("prevents_nearby_leaf_decay");
    public static final BooleanProperty NATURAL_LOG = BooleanProperty.create("natural_log");
    public static final IntegerProperty GROWTH_STAGE = IntegerProperty.create("growth_stage", 0, 3);
}
