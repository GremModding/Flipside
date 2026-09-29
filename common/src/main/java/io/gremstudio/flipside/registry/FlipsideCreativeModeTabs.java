package io.gremstudio.flipside.registry;

import io.gremstudio.flipside.Flipside;
import io.gremstudio.gremlib.multiloader.item.CreativeTabAPI;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

public class FlipsideCreativeModeTabs {
    public static final ResourceKey<CreativeModeTab> FLIPSIDE_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Flipside.INSTANCE.createId("flipside"));

    // Class is already initialized before tab registration, so have to do it at a separate point.
    public static void registerTabs() {
        CreativeModeTab flipsideTab = CreativeTabAPI.createTab(builder ->
                builder.icon(() -> FlipsideItems.FLIPGRASS.getDefaultInstance())
                        .title(Component.translatableWithFallback("flipside.itemGroup.flipside", "Flipside"))
                        .displayItems(((parameters, output) -> {

                        }))
                        .build()
        );

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FLIPSIDE_KEY, flipsideTab);
    }
}
