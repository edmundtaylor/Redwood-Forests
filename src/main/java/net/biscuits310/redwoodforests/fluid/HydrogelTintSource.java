package net.biscuits310.redwoodforests.fluid;

import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.fluid.FluidTintSource;
import org.jspecify.annotations.NonNull;

public class HydrogelTintSource implements FluidTintSource {
    public static final HydrogelTintSource INSTANCE = new HydrogelTintSource();

    private HydrogelTintSource() {}

    @Override
    public int color(@NonNull FluidState state) {
        return 0xfff6dc47;
    }
}
