package io.siuolplex.flipside;

import io.siuolplex.flipside.registry.FlipsideBlocks;
import io.siuolplex.flipside.registry.FlipsideItems;
import io.siuolplex.untitledlib.util.multiloader.LoaderWrapper;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class Flipside {
    public static final String MOD_ID = "flipside";
    public static Logger LOGGER = LoggerFactory.getLogger("Flipside");

    public static void init(LoaderWrapper wrappedLoader) {
        Consumer<?> consumer = h -> loadRegistries();
    }

    public static void loadRegistries() {
        FlipsideBlocks.init();
        FlipsideItems.init();
    }

    public static ResourceLocation asId(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
