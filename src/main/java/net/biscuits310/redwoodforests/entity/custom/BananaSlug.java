package net.biscuits310.redwoodforests.entity.custom;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.floats.FloatArraySet;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatSet;
import net.biscuits310.redwoodforests.entity.ModEntities;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class BananaSlug extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(BananaSlug.class, EntityDataSerializers.BYTE);
    public boolean verticalCollisionAbove;
    private float climbingSpeed;

    public BananaSlug(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2, i -> i.is(Tags.Items.MUSHROOMS), false));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 5.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    protected @NonNull PathNavigation createNavigation(@NonNull Level level) {
        return new WallClimberNavigation(this, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(DATA_FLAGS_ID, (byte) 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide())
            this.setupAnimationStates();
        else {
            this.setClimbing(this.horizontalCollision);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 5)
                .add(Attributes.MOVEMENT_SPEED, 0.1)
                .add(Attributes.TEMPT_RANGE, 16d)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NonNull DamageSource source) {
        return SoundEvents.COD_FLOP;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.BABY_NAUTILUS_DEATH;
    }

    @Override
    protected void playStepSound(@NonNull BlockPos pos, @NonNull BlockState blockState) {
        this.playSound(SoundEvents.FROG_STEP, 0.05F, 1.0F);
    }

    @Override
    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setClimbing(boolean value) {
        byte flags = this.entityData.get(DATA_FLAGS_ID);
        if (value) {
            flags = (byte) (flags | 1);
        } else {
            flags = (byte) (flags & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, flags);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NonNull ServerLevel level, @NonNull AgeableMob partner) {
        return ModEntities.BANANA_SLUG.get().create(level, EntitySpawnReason.BREEDING);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Tags.Items.MUSHROOMS);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.tickCount);
        }
        else
            --this.idleAnimationTimeout;
    }
}
