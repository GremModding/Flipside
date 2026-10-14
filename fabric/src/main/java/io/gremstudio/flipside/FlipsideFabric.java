package io.gremstudio.flipside;

import io.gremstudio.gremlib.fabric.initializers.GremModInitializer;

public class FlipsideFabric implements GremModInitializer {
    Flipside flipside;

    @Override
    public void onGremModInitalization() {
        flipside = new Flipside();
    }
}
