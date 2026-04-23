package io.siuolplex.untitledlib.datagen.fabric;

import io.siuolplex.untitledlib.datagen.DatagenHandler;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FabricDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        new DatagenHandler(fabricDataGenerator);
    }

}
