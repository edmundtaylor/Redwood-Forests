package net.biscuits310.redwoodforests.datagen.custom;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

// Creates custom block model generators, inheriting the functions from BlockModelGenerators
public class ModBlockModelGenerators extends BlockModelGenerators {
    // Constructs everything required by the inherited class
    public ModBlockModelGenerators(Consumer<BlockModelDefinitionGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<Identifier, ModelInstance> modelOutput) {
        super(blockStateOutput, itemModelOutput, modelOutput);
    }

    // Creates a multivariant by combining an array of variants
    public static MultiVariant createRandomVariants(Variant... variants){
        return variants(variants);
    }

    // UNUSED - Creates a variated log block model without horizontal variant consideration
    // Takes the block, and the number of variated textures in as parameters
    public void createVariatedLogBLock(Block block, int numSides) {
        // Initialises an array of variants that is the length of the number of variated sides
        Variant[] variants = new Variant[numSides];
        // Executes the number of variated textures inputted
        for (int i = 1; i <= numSides; i++){
            // The ith - 1 variant in the array is equal to a model created with the CUBE_COLUMN template
            variants[i-1] = plainModel(ModelTemplates.CUBE_COLUMN.create(
                    // Create the model JSON file in the RedwoodForests block package, using the name blockname_i
                    Identifier.fromNamespaceAndPath(RedwoodForests.MODID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath() + "_" + i),
                    // Use the custom texture mapping to assign the texture png to the model
                    ModTextureMapping.randomLogColumn(block, i),
                    ModBlockModelGenerators.this.modelOutput));
        }
        // Create the blockstate by combining the variants in the variants array
        this.blockStateOutput.accept(createAxisAlignedPillarBlock(block, createRandomVariants(variants)));
        // Create an item model using the block model
        this.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(RedwoodForests.MODID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath() + "_1"));
    }

    // Creates a variated log block model with horizontal variant consideration
    // Takes the block, and the number of variated texture in as parameters
    public void createHorizontalVariatedLogBlock(Block block, int numSides){
        // Initialises an array of variants that is the length of the number of variated sides
        Variant[] variants = new Variant[numSides];
        // Initialises an array of horizontal variants that is the length of the number of variated sides
        Variant[] horizontalVariants = new Variant[numSides];
        // Executes the number of variated textures inputted
        for (int i = 1; i <= numSides; i++){
            // The ith - 1 variant in the variants array is equal to a model created with the CUBE_COLUMN template
            variants[i-1] = plainModel(ModelTemplates.CUBE_COLUMN.create(
                    // Create the model JSON file in the RedwoodForests block package, using the name blockname_i
                    Identifier.fromNamespaceAndPath(RedwoodForests.MODID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath() + "_" + i),
                    // Use the custom texture mapping to assign the texture png to the model
                    ModTextureMapping.randomLogColumn(block, i),
                    ModBlockModelGenerators.this.modelOutput));
            // The ith - 1 variants in the horizontalVariants array is equal to a model created with the CUBE_COLUMN_HORIZONTAL template
            horizontalVariants[i-1] = plainModel(ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(
                    // Create the model JSON file in the RedwoodForests block package using the name blockname_horizontal_i
                    Identifier.fromNamespaceAndPath(RedwoodForests.MODID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath() + "_horizontal_" + i),
                    // Use the custom texture mapping to assign the texture png to the model
                    ModTextureMapping.randomLogColumn(block, i),
                    ModBlockModelGenerators.this.modelOutput));
        }
        // Creates the blockstate by combining the variants in both of the arrays using createRotatedPillarWithHorizontalVariant
        this.blockStateOutput.accept(createRotatedPillarWithHorizontalVariant(block, createRandomVariants(variants), createRandomVariants(horizontalVariants)));
        // Create an item model using the block model
        this.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(RedwoodForests.MODID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath() + "_1"));
    }

    // Creates a variated wood block model
    public void createVariatedWoodBlock(Block block, Block logBlock, int numSides){
        // Initialises an array of horizontal variants that is the length of the number of variated sides
        Variant[] variants = new Variant[numSides];
        // Executes the number of variated textures inputted
        for (int i = 1; i <= numSides; i++){
            // The ith - 1 variants in the variants array is equal to a model created with the CUBE_COLUMN template
            variants[i-1] = plainModel(ModelTemplates.CUBE_COLUMN.create(
                    // Create the model JSON file in the RedwoodForests block package, using the name blockname_i
                    Identifier.fromNamespaceAndPath(RedwoodForests.MODID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath() + "_" + i),
                    // Uses the custom texture mapping to assign the texture png to the model
                    // Makes the ends of the log use the texture on the side
                    ModTextureMapping.randomLogColumn(logBlock, i).copyAndUpdate(TextureSlot.END, ModTextureMapping.randomLogColumn(logBlock, i).get(TextureSlot.SIDE)),
                    ModBlockModelGenerators.this.modelOutput));
        }
        // Creates the blockstate by combining the variants in the variants array
        this.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock(block, createRandomVariants(variants)));
        // Create an item model using the block model
        this.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(RedwoodForests.MODID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath() + "_1"));
    }
}
