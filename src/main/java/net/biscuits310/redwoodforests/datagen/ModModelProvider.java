package net.biscuits310.redwoodforests.datagen;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.block.ModBlocks;
import net.biscuits310.redwoodforests.datagen.custom.ModBlockModelGenerators;
import net.biscuits310.redwoodforests.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

// Applies models to items and blocks
// Inherits the ModelProvider class
public class ModModelProvider extends ModelProvider
{
    // Constructs everything required for the inherited class
    public ModModelProvider(PackOutput output)
    {
        super(output, RedwoodForests.MODID);
    }

    // Overrides the registerModels method to apply models to blocks and items
    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels)
    {
        // Creates a ModBlockModelGenerator to allow custom block models
        ModBlockModelGenerators modBlockModels = new ModBlockModelGenerators(
                // Passes in what is passed into blockModels
                blockModels.blockStateOutput,
                blockModels.itemModelOutput,
                blockModels.modelOutput
        );

        // Create a flat item for items without a corresponding block
        itemModels.generateFlatItem(ModItems.REDWOOD_BARK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHARRED_REDWOOD_BARK.get(), ModelTemplates.FLAT_ITEM);

        // Create a variated log block using the custom modBlockModels function
        // 3 different side textures, so 3 is passed in as numSides
        modBlockModels.createHorizontalVariatedLogBlock(ModBlocks.REDWOOD_LOG.get(), 3);
        modBlockModels.createHorizontalVariatedLogBlock(ModBlocks.REDWOOD_ORIGIN_BLOCK.get(), 3);
        modBlockModels.createVariatedWoodBlock(ModBlocks.REDWOOD_WOOD.get(), ModBlocks.REDWOOD_LOG.get(), 3);

        // Create a non-variated log block using blockModels methods
        blockModels.woodProvider(ModBlocks.STRIPPED_REDWOOD_LOG.get()).logWithHorizontal(ModBlocks.STRIPPED_REDWOOD_LOG.get()).wood(ModBlocks.STRIPPED_REDWOOD_WOOD.get());

        // Create all the models that use the redwood planks texture
        blockModels.family(ModBlocks.REDWOOD_PLANKS.get())
                .stairs(ModBlocks.REDWOOD_STAIRS.get())
                .slab(ModBlocks.REDWOOD_SLAB.get())
                .fence(ModBlocks.REDWOOD_FENCE.get());

        // Create standard cubes for blocks using the blockModels methods
        blockModels.createTrivialCube(ModBlocks.REDWOOD_LEAVES.get());
        blockModels.createTrivialCube(ModBlocks.DEEP_REDWOOD_LEAVES.get());
        blockModels.createPlantWithDefaultItem(ModBlocks.REDWOOD_SAPLING.get(), ModBlocks.POTTED_REDWOOD_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);

    }
}
