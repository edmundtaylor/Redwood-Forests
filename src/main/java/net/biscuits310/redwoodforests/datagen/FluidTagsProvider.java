package net.biscuits310.redwoodforests.datagen;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.fluid.Fluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.FluidTags;

import java.util.concurrent.CompletableFuture;

public class FluidTagsProvider extends net.minecraft.data.tags.FluidTagsProvider {
    public FluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RedwoodForests.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {

        tag(FluidTags.WATER)
                .add(Fluids.HYDROGEL.get())
                .add(Fluids.FLOWING_HYDROGEL.get());
    }
}
