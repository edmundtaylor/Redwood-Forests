package net.biscuits310.redwoodforests.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.UntintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

public class FlammableUntintedParticleLeavesBlock extends UntintedParticleLeavesBlock {
    int flammability;
    int fireSpreadSpeed;
    public FlammableUntintedParticleLeavesBlock(float leafParticleChance, ParticleOptions leafParticle, int flammability, int fireSpreadSpeed, Properties properties) {
        super(leafParticleChance, leafParticle, properties);
        this.flammability = flammability;
        this.fireSpreadSpeed = fireSpreadSpeed;
    }

    // Set flammability to true
    @Override
    public boolean isFlammable(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull Direction direction) {
        return true;
    }

    // Set the flammability to the given flammability
    @Override
    public int getFlammability(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull Direction direction) {
        return this.flammability;
    }

    // Set the speed fire spreads to the given fireSpreadSpeed
    @Override
    public int getFireSpreadSpeed(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull Direction direction) {
        return this.fireSpreadSpeed;
    }
}
