package io.siuolplex.flipside;

import io.siuolplex.untitledlib.registration.DelayedRegistry;
import io.siuolplex.untitledlib.util.neoforge.NeoforgeLoaderWrapper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod("flipside")
public class FlipsideNeoforge {
    static boolean hasInitializedRegistries = false;

    public FlipsideNeoforge(IEventBus eventBus) {
        Flipside.init(new NeoforgeLoaderWrapper());

        eventBus.addListener(FlipsideNeoforge::onRegistration);
    }

    public static void onRegistration(RegisterEvent event) {
        if (!hasInitializedRegistries) {
            Flipside.loadRegistries();
            hasInitializedRegistries = true;
        }
        DelayedRegistry<?> registry = DelayedRegistry.getRegistry("flipside", event.getRegistryKey());
        if (registry != null && registry.keyMatches(event.getRegistryKey())) {
            registry.fireRegistry();
        }
    }
}
