package net.biscuits310.redwoodforests.entity.custom;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.floats.FloatArraySet;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class BananaSlug extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(BananaSlug.class, EntityDataSerializers.BYTE);
    public boolean verticalCollisionAbove;

    public BananaSlug(EntityType<? extends Animal> type, Level level) {
        super(type, level);
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
    protected void registerGoals() {
        goalSelector.addGoal(0, new WaterAvoidingRandomStrollGoal(this, 2d));
        goalSelector.addGoal(1, new PanicGoal(this, 2d));
        goalSelector.addGoal(2, new TemptGoal(this, 1.0, i -> i.is(Tags.Items.MUSHROOMS), false));
        goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 7f));
        goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.tickCount);
        }
        else
            --this.idleAnimationTimeout;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide())
            this.setupAnimationStates();
        else {
            this.setHanging(this.isTouchingCeiling());
            this.setClimbing(this.horizontalCollision);
        }
    }

    private boolean isTouchingCeiling(){
        return !this.level().noCollision(this, this.getBoundingBox().move(0.0, 0.01, 0.0));
    }

    @Override
    public void move(@NonNull MoverType moverType, @NonNull Vec3 delta) {
        if (this.noPhysics)
            this.verticalCollisionAbove = false;
        else {
            Vec3 movement = this.collide(delta);
            if (Math.abs(delta.y) > 0.0 || this.isLocalInstanceAuthoritative()) {
                this.verticalCollision = delta.y != movement.y;
                this.verticalCollisionAbove = this.verticalCollision;
            }
        }
        super.move(moverType, delta);
    }

    private Vec3 collide(Vec3 movement) {
        AABB aabb = this.getBoundingBox();
        List<VoxelShape> entityColliders = this.level().getEntityCollisions(this, aabb.expandTowards(movement));
        Vec3 movementStep = movement.lengthSqr() == 0.0 ? movement : collideBoundingBox(this, movement, aabb, this.level(), entityColliders);
        boolean xCollision = movement.x != movementStep.x;
        boolean yCollision = movement.y != movementStep.y;
        boolean zCollision = movement.z != movementStep.z;
        boolean onGroundAfterCollision = yCollision && movement.y < 0.0;
        if (this.maxUpStep() > 0.0F && (onGroundAfterCollision || this.onGround()) && (xCollision || zCollision)) {
            AABB groundedAABB = onGroundAfterCollision ? aabb.move(0.0, movementStep.y, 0.0) : aabb;
            AABB stepUpAABB = groundedAABB.expandTowards(movement.x, this.maxUpStep(), movement.z);
            if (!onGroundAfterCollision) {
                stepUpAABB = stepUpAABB.expandTowards(0.0, -1.0E-5F, 0.0);
            }

            List<VoxelShape> colliders = collectColliders(this, this.level(), entityColliders, stepUpAABB);
            float stepHeightToSkip = (float)movementStep.y;
            float[] candidateStepUpHeights = collectCandidateStepUpHeights(groundedAABB, colliders, this.maxUpStep(), stepHeightToSkip);

            for (float candidateStepUpHeight : candidateStepUpHeights) {
                Vec3 stepFromGround = collideWithShapes(new Vec3(movement.x, candidateStepUpHeight, movement.z), groundedAABB, colliders);
                if (stepFromGround.horizontalDistanceSqr() > movementStep.horizontalDistanceSqr()) {
                    double distanceToGround = aabb.minY - groundedAABB.minY;
                    return stepFromGround.subtract(0.0, distanceToGround, 0.0);
                }
            }
        }

        return movementStep;
    }

    private static Vec3 collideWithShapes(Vec3 movement, AABB boundingBox, List<VoxelShape> shapes) {
        if (shapes.isEmpty()) {
            return movement;
        } else {
            Vec3 resolvedMovement = Vec3.ZERO;

            for (Direction.Axis axis : Direction.axisStepOrder(movement)) {
                double axisMovement = movement.get(axis);
                if (axisMovement != 0.0) {
                    double collision = Shapes.collide(axis, boundingBox.move(resolvedMovement), shapes, axisMovement);
                    resolvedMovement = resolvedMovement.with(axis, collision);
                }
            }

            return resolvedMovement;
        }
    }

    private static float[] collectCandidateStepUpHeights(AABB boundingBox, List<VoxelShape> colliders, float maxStepHeight, float stepHeightToSkip) {
        FloatSet candidates = new FloatArraySet(4);

        for (VoxelShape collider : colliders) {
            for (double coord : collider.getCoords(Direction.Axis.Y)) {
                float relativeCoord = (float)(coord - boundingBox.minY);
                if (!(relativeCoord < 0.0F) && relativeCoord != stepHeightToSkip) {
                    if (relativeCoord > maxStepHeight) {
                        break;
                    }

                    candidates.add(relativeCoord);
                }
            }
        }

        float[] sortedCandidates = candidates.toFloatArray();
        FloatArrays.unstableSort(sortedCandidates);
        return sortedCandidates;
    }

    private static List<VoxelShape> collectColliders(@Nullable Entity source, Level level, List<VoxelShape> entityColliders, AABB boundingBox) {
        ImmutableList.Builder<VoxelShape> colliders = ImmutableList.builderWithExpectedSize(entityColliders.size() + 1);
        if (!entityColliders.isEmpty()) {
            colliders.addAll(entityColliders);
        }

        WorldBorder worldBorder = level.getWorldBorder();
        boolean isEntityInsideCloseToBorder = source != null && worldBorder.isInsideCloseToBorder(source, boundingBox);
        if (isEntityInsideCloseToBorder) {
            colliders.add(worldBorder.getCollisionShape());
        }

        colliders.addAll(level.getBlockCollisions(source, boundingBox));
        return colliders.build();
    }

    @Override
    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean onHangable() {
        return this.isHanging();
    }

    public boolean isClimbing() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public boolean isHanging () {return (this.entityData.get(DATA_FLAGS_ID) & 2) != 0;}

    public void setClimbing(boolean value){
        byte flags = this.entityData.get(DATA_FLAGS_ID);
        if (value) {
            flags = (byte) (flags | 1);
        } else {
            flags = (byte) (flags & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, flags)   ;
    }

    public void setHanging(boolean value){
        byte flags = this.entityData.get(DATA_FLAGS_ID);
        if (value)
            flags = (byte) (flags | 2);
        else
            flags = (byte) (flags & -4);

        this.entityData.set(DATA_FLAGS_ID, flags);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 5)
                .add(Attributes.MOVEMENT_SPEED, 0.05)
                .add(Attributes.TEMPT_RANGE, 16d)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Tags.Items.MUSHROOMS);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NonNull ServerLevel level, @NonNull AgeableMob partner) {
        return null;
    }

    @Override
    public void travel(@NonNull Vec3 input) {
        if (this.shouldTravelInFluid(this.level().getFluidState(this.blockPosition())))
            this.travelInFluid(input, net.minecraft.world.level.material.Fluids.EMPTY.defaultFluidState());
        else if (this.isFallFlying())
            this.travelFallFlying(input);
        else
            this.travelInAir(input);
    }

    private void travelFallFlying(Vec3 input) {
        if (this.onClimbable()) {
            this.travelInAir(input);
            this.stopFallFlying();
        } else {
            Vec3 lastMovement = this.getDeltaMovement();
            double lastSpeed = lastMovement.horizontalDistance();
            this.setDeltaMovement(this.updateFallFlyingMovement(lastMovement));
            this.move(MoverType.SELF, this.getDeltaMovement());
            if (!this.level().isClientSide()) {
                double newSpeed = this.getDeltaMovement().horizontalDistance();
                this.handleFallFlyingCollisions(lastSpeed, newSpeed);
            }
        }
    }

    private void handleFallFlyingCollisions(double moveHorLength, double newMoveHorLength) {
        if (this.horizontalCollision) {
            double diff = moveHorLength - newMoveHorLength;
            float dmg = (float)(diff * 10.0 - 3.0);
            if (dmg > 0.0F) {
                this.playSound(this.getFallDamageSound((int)dmg), 1.0F, 1.0F);
                this.hurt(this.damageSources().flyIntoWall(), dmg);
            }
        }
    }

    private SoundEvent getFallDamageSound(int dmg) {
        return dmg > 4 ? this.getFallSounds().big() : this.getFallSounds().small();
    }

    private Vec3 updateFallFlyingMovement(Vec3 movement) {
        Vec3 lookAngle = this.getLookAngle();
        float leanAngle = this.getXRot() * (float) (Math.PI / 180.0);
        double lookHorLength = Math.sqrt(lookAngle.x * lookAngle.x + lookAngle.z * lookAngle.z);
        double moveHorLength = movement.horizontalDistance();
        double gravity = this.getEffectiveGravity();
        double liftForce = Mth.square(Math.cos(leanAngle));
        movement = movement.add(0.0, gravity * (-1.0 + liftForce * 0.75), 0.0);
        if (movement.y < 0.0 && lookHorLength > 0.0) {
            double convert = movement.y * -0.1 * liftForce;
            movement = movement.add(lookAngle.x * convert / lookHorLength, convert, lookAngle.z * convert / lookHorLength);
        }

        if (leanAngle < 0.0F && lookHorLength > 0.0) {
            double convert = moveHorLength * -Mth.sin(leanAngle) * 0.04;
            movement = movement.add(-lookAngle.x * convert / lookHorLength, convert * 3.2, -lookAngle.z * convert / lookHorLength);
        }

        if (lookHorLength > 0.0) {
            movement = movement.add(
                    (lookAngle.x / lookHorLength * moveHorLength - movement.x) * 0.1, 0.0, (lookAngle.z / lookHorLength * moveHorLength - movement.z) * 0.1
            );
        }

        return movement.multiply(0.99F, 0.98F, 0.99F);
    }

    private void travelInAir(Vec3 input) {
        BlockPos posBelow = this.getBlockPosBelowThatAffectsMyMovement();
        float blockFriction = this.onGround() ? this.level().getBlockState(posBelow).getFriction(this.level(), posBelow, this) : 1.0F;
        float friction = blockFriction * 0.91F;
        Vec3 movement = this.handleRelativeFrictionAndCalculateMovement(input, blockFriction);
        double movementY = movement.y;
        MobEffectInstance levitationEffect = this.getEffect(MobEffects.LEVITATION);
        if (this.isHanging())
            movementY = 0.01;
        else if (levitationEffect != null) {
            movementY += (0.05 * (levitationEffect.getAmplifier() + 1) - movement.y) * 0.2;
        } else if (!this.level().isClientSide() || this.level().isLoaded(posBelow)) {
            movementY -= this.getEffectiveGravity();
        } else if (this.getY() > this.level().getMinY()) {
            movementY = -0.1;
        } else {
            movementY = 0.0;
        }



        if (this.shouldDiscardFriction()) {
            this.setDeltaMovement(movement.x, movementY, movement.z);
        } else {
            float verticalFriction = this instanceof FlyingAnimal ? friction : 0.98F;
            this.setDeltaMovement(movement.x * friction, movementY * verticalFriction, movement.z * friction);
        }
    }

    private Vec3 handleRelativeFrictionAndCalculateMovement(Vec3 input, float friction) {
        this.moveRelative(this.getFrictionInfluencedSpeed(friction), input);
        this.setDeltaMovement(this.handleOnClimbable(this.getDeltaMovement()));
        this.move(MoverType.SELF, this.getDeltaMovement());
        Vec3 movement = this.getDeltaMovement();
        if ((this.horizontalCollision || this.jumping) && (this.onClimbable() || this.wasInPowderSnow && PowderSnowBlock.canEntityWalkOnPowderSnow(this))) {
            movement = new Vec3(movement.x, 0.2, movement.z);
        }

        return movement;
    }

    private Vec3 handleOnClimbable(Vec3 delta) {
        if (this.onClimbable()) {
            this.resetFallDistance();
            float max = 0.15F;
            double xd = Mth.clamp(delta.x, -0.15F, 0.15F);
            double zd = Mth.clamp(delta.z, -0.15F, 0.15F);
            double yd = Math.max(delta.y, -0.15F);

            delta = new Vec3(xd, yd, zd);
        }

        return delta;
    }

    private float getFrictionInfluencedSpeed(float blockFriction) {
        return this.onGround() ? this.getSpeed() * (0.21600002F / (blockFriction * blockFriction * blockFriction)) : this.getFlyingSpeed();
    }

    private void floatInWaterWhileRidden() {
        boolean canEntityFloatInWater = this.is(EntityTypeTags.CAN_FLOAT_WHILE_RIDDEN);
        if (canEntityFloatInWater && this.isVehicle() && this.getFluidHeight(FluidTags.WATER) > this.getFluidJumpThreshold()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.04F, 0.0));
        }
    }

    private void travelInFluid(Vec3 input, FluidState fluidState){
        boolean isFalling = this.getDeltaMovement().y <= 0.0;
        double oldY = this.getY();
        double baseGravity = this.getEffectiveGravity();
        if (this.isInWater()) {
            this.travelInWater(input, baseGravity, isFalling, oldY);
            this.floatInWaterWhileRidden();
        }
        else
            this.travelInLava(input, baseGravity, isFalling, oldY);
    }

    private void travelInLava(Vec3 input, double baseGravity, boolean isFalling, double oldY){
        this.moveRelative(0.02F, input);
        this.move(MoverType.SELF, this.getDeltaMovement());
        if (this.getFluidHeight(FluidTags.LAVA) <= this.getFluidJumpThreshold()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.5, 0.8F, 0.5));
            Vec3 movement = this.getFluidFallingAdjustedMovement(baseGravity, isFalling, this.getDeltaMovement());
            this.setDeltaMovement(movement);
        } else
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5));

        if (baseGravity != 0.0)
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -baseGravity / 4.0, 0.0));
        this.jumpOutOfFluid(oldY);
    }


    private void jumpOutOfFluid(double oldY) {
        Vec3 movement = this.getDeltaMovement();
        if (this.horizontalCollision && this.isFree(movement.x, movement.y + 0.6F - this.getY() + oldY, movement.z)) {
            this.setDeltaMovement(movement.x, 0.3F, movement.z);
        }
    }
}
