package net.biscuits310.redwoodforests.fluid;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.block.Blocks;
import net.biscuits310.redwoodforests.item.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jspecify.annotations.NonNull;

public class Fluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, RedwoodForests.MODID);

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, RedwoodForests.MODID);

    public static final DeferredHolder<FluidType, FluidType> HYDROGEL_TYPE = FLUID_TYPES.register(
            "hydrogel_type",
            id -> new FluidType(FluidType.Properties.create()
                    .descriptionId(Util.makeDescriptionId("fluid", id))
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.SLIME_BLOCK_PLACE)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.SLIME_SQUISH)
                    .canDrown(false)
                    .canSwim(true)
            ){
                @Override
                public boolean supportsBoating(@NonNull AbstractBoat boat) {
                    return true;
                }

                @Override
                public boolean canHydrate(@NonNull Entity entity) {
                    return true;
                }

                @Override
                public boolean canConvertToSource(@NonNull FluidStack stack) {
                    return super.canConvertToSource(stack);
                }

                @Override
                public boolean isVaporizedOnPlacement(@NonNull Level level, @NonNull BlockPos pos, @NonNull FluidStack stack) {
                    return false;
                }
            });

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> HYDROGEL = FLUIDS.register(
            "hydrogel",
            () -> new BaseFlowingFluid.Source(Fluids.HYDROGEL_PROPERTIES));

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> FLOWING_HYDROGEL = FLUIDS.register(
            "flowing_hydrogel",
            () -> new BaseFlowingFluid.Flowing(Fluids.HYDROGEL_PROPERTIES));

    public static final BaseFlowingFluid.Properties HYDROGEL_PROPERTIES =
            new BaseFlowingFluid.Properties(HYDROGEL_TYPE, HYDROGEL, FLOWING_HYDROGEL)
                    .block(Blocks.HYDROGEL)
                    .bucket(Items.HYDROGEL_BUCKET);
}
