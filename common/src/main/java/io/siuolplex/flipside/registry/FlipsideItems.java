package io.siuolplex.flipside.registry;

import io.siuolplex.flipside.Flipside;
import io.siuolplex.untitledlib.registration.DelayedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class FlipsideItems {
    private static DelayedRegistry<Item> delayedRegister = new DelayedRegistry<>("flipside", BuiltInRegistries.ITEM);

    public static Item TEST_BLOCK = register("test_block", properties -> new BlockItem(FlipsideBlocks.TEST_BLOCK, properties), new Item.Properties());
    public static Item TEST_ITEM = register("test_item", properties -> new Item(properties), new Item.Properties());


    // Properties being separate ironically allows us to have a much more streamlined registration process. Allows us to attach a ResourceKey to it.
    public static Item register(String name, Function<Item.Properties, Item> func, Item.Properties properties) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Flipside.asId(name));
        Item item = func.apply(properties.setId(key));
        delayedRegister.addToDelayedRegistry(key, item);
        return item;
    }

    public static void init() {}
}
