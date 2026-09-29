package io.gremstudio.flipside.registry;

import io.gremstudio.flipside.Flipside;
import io.gremstudio.gremlib.multiloader.item.CreativeTabAPI;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;
import java.util.function.Function;

public class FlipsideItems {
    public static Item FLIPGRASS = register("flipgrass", prop -> new BlockItem(FlipsideBlocks.FLIPGRASS, prop), new Item.Properties(), basicPostRegister());
    public static Item FLIPDIRT = register("flipdirt", prop -> new BlockItem(FlipsideBlocks.FLIPDIRT, prop), new Item.Properties(), basicPostRegister());
    public static Item FLIPSTONE = register("flipstone", prop -> new BlockItem(FlipsideBlocks.FLIPSTONE , prop), new Item.Properties(), basicPostRegister());

    public static Item TEST_ITEM = register("test_item", Item::new, new Item.Properties(), basicPostRegister());

    public static Item register(String name, Function<Item.Properties, Item> func, Item.Properties properties, Consumer<Item> postRegister) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Flipside.INSTANCE.createId(name));
        Item item = func.apply(properties.setId(key));
        Registry.register(BuiltInRegistries.ITEM, key, item);
        return item;
    }

    public static Consumer<Item> basicPostRegister() {
        return (item) -> CreativeTabAPI.insertStart(FlipsideCreativeModeTabs.FLIPSIDE_KEY, item::getDefaultInstance);
    }

    public static void init() {}
}
