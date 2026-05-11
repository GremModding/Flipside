package io.siuolplex.flipside;

import net.fabricmc.api.ModInitializer;

public class FlipsideFabric implements ModInitializer {
    Flipside flipside;

    @Override
    public void onInitialize() {
        flipside = new Flipside();
        handleRegistration();
    }

    private void handleRegistration() {
    }
}
