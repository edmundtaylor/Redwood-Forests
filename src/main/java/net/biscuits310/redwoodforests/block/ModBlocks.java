package net.biscuits310.redwoodforests.block;

import net.biscuits310.redwoodforests.RedwoodForests;
import net.biscuits310.redwoodforests.block.custom.*;
import net.biscuits310.redwoodforests.item.ModItems;
import net.biscuits310.redwoodforests.worldgen.tree.ModTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

// Creates DeferredBlocks, and combines them into a Deferredregister
public class ModBlocks
{
    // Combines DeferredBlocks into a DeferredRegister
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(RedwoodForests.MODID);

    // Returns false and allows passing of given parameters
    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

    // Creates the redwood planks block as a DeferredBlock
    public static final DeferredBlock<Block> REDWOOD_PLANKS = registerBlock("redwood_planks",
            // Sets the properties for a plank block, and creates it using the Block class
            properties -> new FlammableBlock(5, 20, properties
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    // Creates the redwood log block as a DeferredBlock
    public static final DeferredBlock<Block> REDWOOD_LOG = registerBlock("redwood_log",
            // Sets the properties for a log block, and creates it using the StrippableFlammableRotatedPillarBlock class
            properties -> new RedwoodLogBlock(5, 5, ModBlocks.STRIPPED_REDWOOD_LOG, properties
                    .mapColor(state -> state.getValue(StrippableFlammableRotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final DeferredBlock<Block> REDWOOD_ORIGIN_BLOCK = registerBlock("redwood_origin_block",
            properties -> new RedwoodOriginBlock(5, 5, ModBlocks.STRIPPED_REDWOOD_LOG, ModTreeGrowers.REDWOOD, properties
                    .mapColor(state -> state.getValue(StrippableFlammableRotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    // Creates the redwood wood block as a DeferredBlock
    public static final DeferredBlock<Block> REDWOOD_WOOD = registerBlock("redwood_wood",
            // Sets the properties for a wood block, and creates it using the StrippableFlammableRotatedPillarBlock class
            properties -> new StrippableFlammableRotatedPillarBlock(5, 5, ModBlocks.STRIPPED_REDWOOD_WOOD, properties
                    .mapColor(MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));
                // Flammability does not need to be overriden

    // Creates a stripped redwood log block as a DeferredBlock
    public static final DeferredBlock<Block> STRIPPED_REDWOOD_LOG = registerBlock("stripped_redwood_log",
            // Sets the properties for a stripped log block, and creates it using the FlammableRotatedPillarBlock class
            // Uses FlammableRotatedPillarBlock instead of StrippableFlammableRotatedPillarBlock as stripping functionality is not required
            properties -> new FlammableRotatedPillarBlock(5, 5, properties
                    .mapColor(MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    // Creats a stripped redwood wood block as a DeferredBlock
    public static final DeferredBlock<Block> STRIPPED_REDWOOD_WOOD = registerBlock("stripped_redwood_wood",
            // Sets the properties of a stripped wood block, and creates it using the FlammableRotatedPillarBlock class
            properties -> new FlammableRotatedPillarBlock(5, 5, properties
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    // Creates a redwood stairs block as a DeferredBlock
    public static final DeferredBlock<Block> REDWOOD_STAIRS = registerBlock("redwood_stairs",
            // Sets the properties of a wooden stair block, and creates it using the StairBlock class
            // Also inputs redwood planks, to define further properties
            properties -> new FlammableStairBlock(ModBlocks.REDWOOD_PLANKS.get().defaultBlockState(), 5, 20, properties
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    // Creates a redwood slab block as a DeferredBlock
    public static final DeferredBlock<Block> REDWOOD_SLAB = registerBlock("redwood_slab",
            // Sets the properties of a wooden slab block, and creates it using the SlabBlock class
            properties -> new FlammableSlabBlock(5, 20, properties
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    // Creates a redwood fence block as a DeferredBlock
    public static final DeferredBlock<Block> REDWOOD_FENCE = registerBlock("redwood_fence",
            // Sets the properties of a wooden fence, and creates it using the FenceBlock class
            properties -> new RedwoodFenceBlock(5, 20, properties
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava())
);

    // Creates a redwood leaves block as a DeferredBlock
    public static final DeferredBlock<Block> REDWOOD_LEAVES = registerBlock("redwood_leaves",
            // Sets the properties of a non-tinted leaves block, and creates it using the UntintedParticleLeavesBlock class
            properties -> new RedwoodLeavesBlock(0.01F, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES,  -9399763), 30, 60, properties
                    .mapColor(MapColor.PLANT)
                    .strength(0.2F)
                    .randomTicks()
                    .sound(SoundType.GRASS)
                    .noOcclusion()
                    .isValidSpawn(Blocks::ocelotOrParrot)
                    .isSuffocating(ModBlocks::never)
                    .isViewBlocking(ModBlocks::never)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
                    .isRedstoneConductor(ModBlocks::never)));

    public static final DeferredBlock<Block> DEEP_REDWOOD_LEAVES = registerBlock("deep_redwood_leaves",
            properties -> new RedwoodLeavesBlock(0.01F, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, -9399763), 30, 60, properties
                    .mapColor(MapColor.PLANT)
                    .strength(0.2F)
                    .randomTicks()
                    .sound(SoundType.GRASS)
                    .noOcclusion()
                    .isValidSpawn(Blocks::ocelotOrParrot)
                    .isSuffocating(ModBlocks::never)
                    .isViewBlocking(ModBlocks::never)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
                    .isRedstoneConductor(ModBlocks::never)));

    // Creates a redwood sapling block as a DeferredBlock
    public static final DeferredBlock<Block> REDWOOD_SAPLING = registerBlock("redwood_sapling",
            // Sets the properties of a sapling block, and creates it using the SaplingBlock class
            // Also inputs the redwood tree grower, which assigns the type of tree to be grown when the sapling grows into a tree
            properties -> new SaplingBlock(ModTreeGrowers.REDWOOD, properties
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));

    // Creates a potted redwood sapling block for use in a flower pot
    public static final DeferredBlock<Block> POTTED_REDWOOD_SAPLING = registerBlock("potted_redwood_sapling",
            // Sets the properties of a potted plant block, and creates it using the FlowerPotBlock class
            properties -> new FlowerPotBlock(ModBlocks.REDWOOD_SAPLING.get(), properties
                    .instabreak()
                    .noOcclusion()
                    .pushReaction(PushReaction.DESTROY)));

    // Creates a DeferredBlock and its corresponding item using the given parameters
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function)
    {
        // Creates the DeferredBlock
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        // Creates the item for the block
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    // Registers an item using the given parameters
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block)
    {
        ModItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    // Registers the BLOCKS DeferredRegister using the event bus
    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}