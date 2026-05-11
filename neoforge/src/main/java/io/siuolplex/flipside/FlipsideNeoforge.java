package io.siuolplex.flipside;

import io.siuolplex.gremlib.neoforge.initializers.GremModInitalizationEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mod("flipside")
public class FlipsideNeoforge {
    static boolean hasInitializedRegistries = false;
    public static Flipside flipside;
    public final IEventBus modBus;

    public FlipsideNeoforge(IEventBus modBus) {
        this.modBus = modBus;
        modBus.register(this);
    }

    @SubscribeEvent
    public void onGremModInitalization(GremModInitalizationEvent event) {
        flipside = new Flipside();
    }
}
