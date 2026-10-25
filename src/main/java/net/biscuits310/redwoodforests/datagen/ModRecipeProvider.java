package net.biscuits310.redwoodforests.datagen;

import net.biscuits310.redwoodforests.block.Blocks;
import net.biscuits310.redwoodforests.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.Ingredient;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

// Creates recipes for blocks and items
// Inherits from the RecipeProvider class
public class ModRecipeProvider extends RecipeProvider {
    // Constructs everything required for the inherited class
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    // A subclass of ModRecipeProvider which creates a provider for all the modded recipes
    public static class Runner extends RecipeProvider.Runner{
        // Constructs everything required for the inherited class
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        // Overrides createRecipeProvider to create a RecipeProvider with the ModRecipeProvider class
        // As opposed to the RecipeProvider class
        @Override
        protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registries, @NonNull RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        // Changes the name of the provider
        @Override
        public @NonNull String getName() {
            return "RedwoodForests Recipes";
        }
    }

    // Overrides buildRecipes to create all the modded recipes
    @Override
    protected void buildRecipes() {
        // Creates a recipe for redwood planks
        // Keep in the recipe book category building blocks
        // Give 4 redwood planks
        // The recipe can be in any shape
        shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.REDWOOD_PLANKS.get(),4)
                // Requires any item with the redwood logs tag (redwood log, redwood wood, stripped, redwood log etc)
                .requires(ModTags.Items.REDWOOD_LOGS)
                // Unlocked in the recipe book like this
                .unlockedBy(getHasName(Blocks.REDWOOD_LOG.get()), has(ModTags.Items.REDWOOD_LOGS))
                // Keep in the redwood planks group
                .group("redwood_planks")
                .save(output);

        // Creates a recipe for redwood wood
        // Keep in the recipe book category building blocks
        // Give 3 redwood wood
        // The recipe must be shaped
        shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.REDWOOD_WOOD.get(), 3)
                // The character 'A' represents the redwood log item
                .define('A', Blocks.REDWOOD_LOG.asItem())
                // The crafting table pattern required to craft the redwood log
                .pattern("AA")
                .pattern("AA")
                // Unlocked in the recipe book like this
                .unlockedBy(getHasName(Blocks.REDWOOD_LOG.get()), has(Blocks.REDWOOD_LOG))
                // Keep in the redwood wood group
                .group("redwood_wood")
                .save(output);

        // Creates a recipe for stripped redwood wood
        // Keep in the recipe book category building blocks
        // Give 3 stripped redwood wood
        // The recipe must be shaped
        shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.STRIPPED_REDWOOD_WOOD.get(), 3)
                // The character 'A' represents the stripped redwood log item
                .define('A', Blocks.STRIPPED_REDWOOD_LOG.asItem())
                // The crafting table pattern required to craft the redwood log
                .pattern("AA")
                .pattern("AA")
                // Unlocked in the recipe book like this
                .unlockedBy(getHasName(Blocks.STRIPPED_REDWOOD_LOG.get()), has(Blocks.STRIPPED_REDWOOD_LOG))
                // Keep in the stripped redwood wood group
                .group("stripped_redwood_wood")
                .save(output);

        // Create a recipe for redwood stairs
        // stairBuilder automatically gives the shape and mirrored variants for stairs
        // Give redwood stairs
        // Use the redwood planks item as the ingredient
        stairBuilder(Blocks.REDWOOD_STAIRS.get(), Ingredient.of(Blocks.REDWOOD_PLANKS))
                // Unlock in the recipe book like this
                .unlockedBy(getHasName(Blocks.REDWOOD_PLANKS.get()), has(Blocks.REDWOOD_PLANKS))
                .save(output);

        // Create a recipe for a redwood slab
        // slabBuilder automatically gives the shape for a slab
        // Give redwood slabs
        // Use the redwood planks item as the ingredient
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, Blocks.REDWOOD_SLAB.get(), Ingredient.of(Blocks.REDWOOD_PLANKS))
                // Unlock in the recipe book like this
                .unlockedBy(getHasName(Blocks.REDWOOD_PLANKS.get()), has(Blocks.REDWOOD_PLANKS))
                .save(output);

        // Create a recipe for a redwood fence
        // fenceBuilder automatically gives the shape for a fence
        // Give redwood fence
        // Use the redwood planks ingredient as the ingredient
        fenceBuilder(Blocks.REDWOOD_FENCE.get(), Ingredient.of(Blocks.REDWOOD_PLANKS))
                // Unlock in the recipe book like this
                .unlockedBy(getHasName(Blocks.REDWOOD_PLANKS.get()), has(Blocks.REDWOOD_PLANKS))
                .save(output);

        fenceGateBuilder(Blocks.REDWOOD_FENCE_GATE.get(), Ingredient.of(Blocks.REDWOOD_PLANKS))
                .unlockedBy(getHasName(Blocks.REDWOOD_PLANKS.get()), has(Blocks.REDWOOD_PLANKS))
                .save(output);

        doorBuilder(Blocks.REDWOOD_DOOR.get(), Ingredient.of(Blocks.REDWOOD_PLANKS))
                .unlockedBy(getHasName(Blocks.REDWOOD_PLANKS.get()), has(Blocks.REDWOOD_PLANKS))
                .save(output);

        trapdoorBuilder(Blocks.REDWOOD_TRAPDOOR.get(), Ingredient.of(Blocks.REDWOOD_PLANKS))
                .unlockedBy(getHasName(Blocks.REDWOOD_PLANKS.get()), has(Blocks.REDWOOD_PLANKS))
                .save(output);

        pressurePlateBuilder(RecipeCategory.REDSTONE, Blocks.REDWOOD_PRESSURE_PLATE.get(), Ingredient.of(Blocks.REDWOOD_PLANKS))
                .unlockedBy(getHasName(Blocks.REDWOOD_PLANKS.get()), has(Blocks.REDWOOD_PLANKS))
                .save(output);

        buttonBuilder(Blocks.REDWOOD_BUTTON.get(), Ingredient.of(Blocks.REDWOOD_PLANKS))
                .unlockedBy(getHasName(Blocks.REDWOOD_PLANKS.get()), has(Blocks.REDWOOD_PLANKS))
                .save(output);
    }
}
