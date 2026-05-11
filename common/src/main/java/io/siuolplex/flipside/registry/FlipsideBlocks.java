package io.siuolplex.flipside.registry;

import io.siuolplex.flipside.Flipside;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class FlipsideBlocks {
    public static Block FLIPGRASS = register("flipgrass", Block::new, BlockBehaviour.Properties.of());
    public static Block FLIPDIRT = register("flipdirt", Block::new, BlockBehaviour.Properties.of());
    public static Block FLIPSTONE = register("flipstone", Block::new, BlockBehaviour.Properties.of()); // Flipstone, meet the flipstone

    public static Block register(String name, Function<Block.Properties, Block> func, Block.Properties properties) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Flipside.INSTANCE.createId(name));
        Block block = func.apply(properties.setId(key));
        Registry.register(BuiltInRegistries.BLOCK, key, block);
        return block;
    }


    public static void init() {}
}
