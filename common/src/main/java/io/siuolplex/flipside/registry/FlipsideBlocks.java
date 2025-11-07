package io.siuolplex.flipside.registry;

import io.siuolplex.flipside.Flipside;
import io.siuolplex.untitledlib.registration.DelayedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class FlipsideBlocks {
    private static DelayedRegistry<Block> delayedRegister = new DelayedRegistry<>("flipside", BuiltInRegistries.BLOCK);

    public static Block TEST_BLOCK = register("test_block", Block::new, BlockBehaviour.Properties.of());

    // Properties being separate ironically allows us to have a much more streamlined registration process. Allows us to attach a ResourceKey to it.
    public static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFunc, BlockBehaviour.Properties properties) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Flipside.asId(name));
        Block block = blockFunc.apply(properties.setId(key));
        delayedRegister.addToDelayedRegistry(key, block);
        return block;
    }

    public static void init() {}
}
