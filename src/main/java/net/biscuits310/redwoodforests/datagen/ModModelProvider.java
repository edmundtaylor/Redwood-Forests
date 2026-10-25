package net.biscuits310.redwoodforests.datagen;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.block.Blocks;
import net.biscuits310.redwoodforests.datagen.custom.ModBlockModelGenerators;
import net.biscuits310.redwoodforests.item.Items;
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

        blockModels.createNonTemplateModelBlock(Blocks.HYDROGEL.get());

        // Create a flat item for items without a corresponding block
        itemModels.generateFlatItem(Items.REDWOOD_BARK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(Items.CHARRED_REDWOOD_BARK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(Items.BANANA_SLUG_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(Items.HYDROGEL_BUCKET.get(), ModelTemplates.FLAT_ITEM);

        // Create a variated log block using the custom modBlockModels function
        // 3 different side textures, so 3 is passed in as numSides
        modBlockModels.createHorizontalVariatedLogBlock(Blocks.REDWOOD_LOG.get(), 3);
        modBlockModels.createHorizontalVariatedLogBlock(Blocks.REDWOOD_ORIGIN_BLOCK.get(), 3);
        modBlockModels.createVariatedWoodBlock(Blocks.REDWOOD_WOOD.get(), Blocks.REDWOOD_LOG.get(), 3);

        // Create a non-variated log block using blockModels methods
        blockModels.woodProvider(Blocks.STRIPPED_REDWOOD_LOG.get()).logWithHorizontal(Blocks.STRIPPED_REDWOOD_LOG.get()).wood(Blocks.STRIPPED_REDWOOD_WOOD.get());

        // Create all the models that use the redwood planks texture
        blockModels.family(Blocks.REDWOOD_PLANKS.get())
                .stairs(Blocks.REDWOOD_STAIRS.get())
                .slab(Blocks.REDWOOD_SLAB.get())
                .fence(Blocks.REDWOOD_FENCE.get())
                .fenceGate(Blocks.REDWOOD_FENCE_GATE.get())
                .door(Blocks.REDWOOD_DOOR.get())
                .pressurePlate(Blocks.REDWOOD_PRESSURE_PLATE.get())
                .button(Blocks.REDWOOD_BUTTON.get())
                .trapdoor(Blocks.REDWOOD_TRAPDOOR.get());

        // Create standard cubes for blocks using the blockModels methods
        blockModels.createTrivialCube(Blocks.REDWOOD_LEAVES.get());
        blockModels.createTrivialCube(Blocks.DEEP_REDWOOD_LEAVES.get());
        blockModels.createPlantWithDefaultItem(Blocks.REDWOOD_SAPLING.get(), Blocks.POTTED_REDWOOD_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
    }
}
