package net.biscuits310.redwoodforests.datagen.custom;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.world.level.block.Block;

// Creates custom texture mappings, inheriting from TextureMapping
public class ModTextureMapping extends TextureMapping {
    // Creates a texture mapping for a column with random sides
    public static TextureMapping randomLogColumn(Block block, int i){
        return new TextureMapping()
                // Makes the side texture take the texture called blockname_i where i is the current variant
                .put(TextureSlot.SIDE, getBlockTexture(block, "_" + i))
                // Makes the top texture take the texture called blockname_top
                // This is because the top texture is not randomised
                .put(TextureSlot.END, getBlockTexture(block, "_top"))
                // Makes the particle take the log texture
                .put(TextureSlot.PARTICLE, getBlockTexture(block));
    }
}
