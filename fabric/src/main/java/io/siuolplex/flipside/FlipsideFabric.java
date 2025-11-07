package io.siuolplex.flipside;

import io.siuolplex.untitledlib.registration.DelayedRegistry;
import io.siuolplex.untitledlib.util.fabric.FabricLoaderWrapper;
import net.fabricmc.api.ModInitializer;

public class FlipsideFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Flipside.init(new FabricLoaderWrapper());

        handleRegistration();
    }

    private void handleRegistration() {
        Flipside.loadRegistries();
        DelayedRegistry.fireAllRegistriesInMod("flipside");
    }
}
