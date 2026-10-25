package net.biscuits310.redwoodforests.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;

import java.util.function.Supplier;

// Creates a custom flammable, strippable, rotatable pillar block blueprint for DeferredBlocks
public class StrippableFlammableRotatedPillarBlock extends FlammableRotatedPillarBlock {
    // Initialises a Block supplier called strippedBlock
    private final Supplier<Block> strippedBlock;

    // Constructs everything required by the inherited class
    // Assigns the given strippedBlock parameter to the strippedBlock supplier
    public StrippableFlammableRotatedPillarBlock(int flammability, int fireSpreadSpeed, Supplier<Block> strippedBlock, Properties properties) {
        super(flammability, fireSpreadSpeed, properties);
        this.strippedBlock = strippedBlock;
    }

    // Returns the strippedBlock
    public Block getStrippedBlock() {
        return strippedBlock.get();
    }

    // Replaces the log block with strippedBlock when right-clicked with an axe item
    @Override
    protected @NonNull InteractionResult useItemOn(ItemStack itemStack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {
        // If the currently held item has the axe item tag
        if (itemStack.is(ItemTags.AXES)){
            // Store the old unstripped block's state (its axis)
            BlockState oldState = level.getBlockState(pos);
            // Store the new state as StrippedBlock with the old axis
            BlockState newState = getStrippedBlock()
                            .defaultBlockState()
                                    .setValue(RotatedPillarBlock.AXIS, oldState.getValue(RotatedPillarBlock.AXIS));

            // Play the axe stripping sound
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1f, 1f);
            // Only execute on the server
            if(!level.isClientSide()){
                // Set the block as the stored stripped block with the correct axis
                level.setBlock(pos, newState, 3);
            }
            // Return a success, which lets the click animation play
            return InteractionResult.SUCCESS;
        }
        // Return a pass, which stops any animations from playing
        return InteractionResult.PASS;
    }
}
