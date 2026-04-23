package io.siuolplex.flipside.registry;

import io.siuolplex.flipside.Flipside;
import io.siuolplex.untitledlib.blocks.BaseDoorBlock;
import io.siuolplex.untitledlib.blocks.BaseSaplingBlock;
import io.siuolplex.untitledlib.blocks.BaseStairBlock;
import io.siuolplex.untitledlib.registration.DelayedRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;

public class FlipsideBlocks {
    private static DelayedRegistry<Block> delayedRegister = new DelayedRegistry<>("flipside", BuiltInRegistries.BLOCK);

    public static Block TEST_BLOCK = register("test_block", Block::new, BlockBehaviour.Properties.of());

    public static Block FLIPSIDE_PORTAL = register("flipside_portal", Block::new, BlockBehaviour.Properties.of());


    public static Block AVALONIA_LOG = register("avalonia_log", RotatedPillarBlock::new, logProperties(MapColor.COLOR_LIGHT_GREEN, MapColor.TERRACOTTA_PURPLE, SoundType.WOOD));
    public static Block AVALONIA_WOOD = register("avalonia_wood", RotatedPillarBlock::new, addWoodProperties(create().mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static Block STRIPPED_AVALONIA_LOG = register("stripped_avalonia_log", RotatedPillarBlock::new, addWoodProperties(create().mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static Block STRIPPED_AVALONIA_WOOD = register("stripped_avalonia_wood", RotatedPillarBlock::new, addWoodProperties(create().mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static Block AVALONIA_LEAVES = register("avalonia_leaves", prop -> new TintedParticleLeavesBlock(0.01F, prop), leavesProperties(SoundType.GRASS));
    public static Block AVALONIA_SAPLING = register(
            "avalonia_sapling",
            prop -> new BaseSaplingBlock(TreeGrower.SPRUCE, prop),
            create()
                    .mapColor(MapColor.COLOR_LIGHT_GREEN)
                    .noCollision()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)
    );

    public static Block AVALONIA_PLANKS = register("avalonia_planks", Block::new, create().mapColor(MapColor.TERRACOTTA_PURPLE));
    public static Block AVALONIA_PLANK_SLAB = register("avalonia_plank_slab", SlabBlock::new, create().mapColor(MapColor.TERRACOTTA_PURPLE));
    public static Block AVALONIA_PLANK_STAIRS = register("avalonia_plank_stairs", BaseStairBlock::new, create().mapColor(MapColor.TERRACOTTA_PURPLE));
    public static Block AVALONIA_PLANK_FENCE = register("avalonia_plank_fence", FenceBlock::new, create().mapColor(MapColor.TERRACOTTA_PURPLE));
    public static Block AVALONIA_PLANK_FENCE_GATE = register("avalonia_plank_fence_gate", prop -> new FenceGateBlock(WoodType.SPRUCE, prop), create().mapColor(MapColor.TERRACOTTA_PURPLE));
    public static Block AVALONIA_PLANK_DOOR = register("avalonia_plank_door", prop -> new BaseDoorBlock(BlockSetType.SPRUCE, prop), create().mapColor(MapColor.TERRACOTTA_PURPLE));
    public static Block AVALONIA_PLANK_TRAPDOOR = register("avalonia_plank_trapdoor", prop -> new TrapDoorBlock(BlockSetType.SPRUCE, prop), create().mapColor(MapColor.TERRACOTTA_PURPLE));


    public static Block AVALONIA_PLANK_SHELF = register(
            "avalonia_plank_shelf",
            ShelfBlock::new,
            create().mapColor(MapColor.TERRACOTTA_PURPLE).instrument(NoteBlockInstrument.BASS).sound(SoundType.SHELF).ignitedByLava().strength(2.0F, 3.0F)
    );


    // Properties being separate ironically allows us to have a much more streamlined registration process. Allows us to attach a ResourceKey to it.
    public static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFunc, BlockBehaviour.Properties properties) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Flipside.asId(name));
        Block block = blockFunc.apply(properties.setId(key));
        delayedRegister.addToDelayedRegistry(key, block);
        return block;
    }

    private static BlockBehaviour.Properties logProperties(MapColor sideColor, MapColor topColor, SoundType sound) {
        return create()
                .mapColor(p_152624_ -> p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? sideColor : topColor)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(sound)
                .ignitedByLava();
    }

    private static BlockBehaviour.Properties leavesProperties(SoundType sound) {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .strength(0.2F)
                .randomTicks()
                .sound(sound)
                .noOcclusion()
                .isValidSpawn((state, level, pos, entityType) -> entityType == EntityType.OCELOT || entityType == EntityType.PARROT)
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
                .isRedstoneConductor((state, level, pos) -> false);
    }


    private static BlockBehaviour.Properties addWoodProperties(BlockBehaviour.Properties properties) {
        return properties.instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava();
    }

    private static BlockBehaviour.Properties create() {
        return BlockBehaviour.Properties.of();
    }

    public static void init() {}
}
