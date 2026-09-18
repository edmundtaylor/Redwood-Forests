package net.biscuits310.redwoodforests.tags;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

// Creates custom tags for recipes and other uses within my mod
public class ModTags {
    // A subclass for creating block tags
    public static class Blocks {
        public static final TagKey<Block> REDWOOD_GROWERS = createTag("redwood_growers");
        public static final TagKey<Block> REDWOOD_FOLIAGE_REMOVABLE = createTag("redwood_foliage_removable");
        public static final TagKey<Block> REDWOOD_TRUNK_REMOVABLE = createTag("redwood_trunk_removable");

        // Creates a block tag using the given name and the mod id
        private static TagKey<Block> createTag(String name){
            return BlockTags.create(Identifier.fromNamespaceAndPath(RedwoodForests.MODID, name));
        }
    }

    // A subclass for creating item tags
    public static class Items{
        // Create an item tag called "redwood_logs" for the redwood planks recipe
        public static final TagKey<Item> REDWOOD_LOGS = createTag("redwood_logs");

        // Creates an item tag using the given name and the mod id
        private static TagKey<Item> createTag(String name){
            return ItemTags.create(Identifier.fromNamespaceAndPath(RedwoodForests.MODID, name));
        }
    }
}
