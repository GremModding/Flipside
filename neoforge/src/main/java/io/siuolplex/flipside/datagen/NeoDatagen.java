package io.siuolplex.flipside.datagen;

import io.siuolplex.untitledlib.datagen.DatagenHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class NeoDatagen {
    @SubscribeEvent // on the mod event bus
    public static void onGatherData(GatherDataEvent event) {
        new DatagenHandler(event.getGenerator());
    }
}
