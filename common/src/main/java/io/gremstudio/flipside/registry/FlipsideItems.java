package io.gremstudio.flipside.registry;

import io.gremstudio.flipside.Flipside;
import io.gremstudio.flipside.entities.jello.TransformableJelloBall;
import io.gremstudio.flipside.items.TransformableJelloItem;
import io.gremstudio.gremlib.multiloader.item.CreativeTabAPI;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DamageResistant;

import java.util.function.Consumer;
import java.util.function.Function;

public class FlipsideItems {
    public static Item FLIPGRASS = register("flipgrass", prop -> new BlockItem(FlipsideBlocks.FLIPGRASS, prop), new Item.Properties(), basicPostRegister());
    public static Item FLIPDIRT = register("flipdirt", prop -> new BlockItem(FlipsideBlocks.FLIPDIRT, prop), new Item.Properties(), basicPostRegister());
    public static Item FLIPSTONE = register("flipstone", prop -> new BlockItem(FlipsideBlocks.FLIPSTONE , prop), new Item.Properties(), basicPostRegister());

    public static Item TEST_ITEM = register("test_item", Item::new, new Item.Properties(), basicPostRegister());

    public static Item JELLO = register("jello", prop -> new TransformableJelloItem(TransformableJelloBall::new, prop),
            new Item.Properties().delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.get(DamageTypeTags.IS_LIGHTNING).get())),
            basicPostRegister());

    public static Item register(String name, Function<Item.Properties, Item> func, Item.Properties properties, Consumer<Item> postRegister) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Flipside.INSTANCE.createId(name));
        Item item = Registry.register(BuiltInRegistries.ITEM, key, func.apply(properties.setId(key)));
        postRegister.accept(item);
        return item;
    }

    public static Consumer<Item> basicPostRegister() {
        return (item) -> CreativeTabAPI.insertEnd(FlipsideCreativeModeTabs.FLIPSIDE_KEY, item::getDefaultInstance);
    }

    public static void init() {}
}
