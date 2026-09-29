package io.gremstudio.flipside;

import io.gremstudio.gremlib.fabric.initializers.GremModInitializer;
import net.fabricmc.api.ModInitializer;

public class FlipsideFabric implements GremModInitializer {
    Flipside flipside;

    @Override
    public void onGremModInitalization() {
        flipside = new Flipside();
    }
}
