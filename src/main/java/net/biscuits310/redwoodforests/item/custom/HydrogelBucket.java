package net.biscuits310.redwoodforests.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class HydrogelBucket extends BucketItem {
    public HydrogelBucket(Fluid content, Properties properties) {
        super(content, properties);
    }

    @Override
    public boolean emptyContents(@Nullable LivingEntity user, @NonNull Level level, @NonNull BlockPos pos, @Nullable BlockHitResult hitResult, @Nullable ItemStack containerItem) {
        if (!(this.content instanceof FlowingFluid flowingFluid)) {
            return false;
        } else {
            BlockState blockState = level.getBlockState(pos);
            Block block = blockState.getBlock();
            boolean mayReplace = blockState.canBeReplaced(this.content);
            boolean shiftKeyDown = user != null && user.isShiftKeyDown();
            boolean placeLiquid = mayReplace
                    || block instanceof LiquidBlockContainer container && container.canPlaceLiquid(user, level, pos, blockState, this.content);
            var containedFluidStack = containerItem != null ? net.neoforged.neoforge.transfer.fluid.FluidUtil.getFirstStackContained(containerItem) : net.neoforged.neoforge.fluids.FluidStack.EMPTY;
            boolean canPlaceFluidInsideBlock = blockState.isAir() || placeLiquid && (!shiftKeyDown || hitResult == null);
            if (!canPlaceFluidInsideBlock) {
                return hitResult != null && this.emptyContents(user, level, hitResult.getBlockPos().relative(hitResult.getDirection()), null, containerItem);
            } else if (!containedFluidStack.isEmpty() && this.content.getFluidType().isVaporizedOnPlacement(level, pos, containedFluidStack)) {
                this.content.getFluidType().onVaporize(user, level, pos, containedFluidStack);
                return true;
            } else if (block instanceof LiquidBlockContainer container && container.canPlaceLiquid(user, level, pos, blockState, content)) {
                container.placeLiquid(level, pos, blockState, flowingFluid.getSource(false));
                this.playEmptySound(user, level, pos);
                return true;
            } else {
                if (!level.isClientSide() && mayReplace && !blockState.getFluidState().isEmpty()) {
                    level.destroyBlock(pos, true);
                }

                if (!level.setBlock(pos, this.content.defaultFluidState().createLegacyBlock(), 11) && !blockState.getFluidState().isSource()) {
                    return false;
                } else {
                    this.playEmptySound(user, level, pos);
                    return true;
                }
            }
        }
    }
}
