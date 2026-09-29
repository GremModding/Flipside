package io.gremstudio.flipside;

import io.gremstudio.flipside.registry.FlipsideBlocks;
import io.gremstudio.flipside.registry.FlipsideCreativeModeTabs;
import io.gremstudio.flipside.registry.FlipsideEntities;
import io.gremstudio.flipside.registry.FlipsideItems;
import io.gremstudio.gremlib.mod.GremMod;
import io.gremstudio.gremlib.mod.HasRegistration;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Flipside extends GremMod implements HasRegistration {
    private final Logger LOGGER = LoggerFactory.getLogger("Flipside");
    public static Flipside INSTANCE = null;

    public Flipside() {
        if (INSTANCE != null) {
            throw new GremMod.GremModReinitError("Can't run a GremMod twice over!");
        }
        super();
        INSTANCE = this;
    }

    public void fireRegistry(Registry<?> registry) {
        if (registry.key().equals(Registries.BLOCK)) {
            FlipsideBlocks.init();
        } else if (registry.key().equals(Registries.ITEM)) {
            FlipsideItems.init();
        } else if (registry.key().equals(Registries.ENTITY_TYPE)) {
            FlipsideEntities.init();
        } else if (registry.key().equals(Registries.CREATIVE_MODE_TAB)) {
            FlipsideCreativeModeTabs.registerTabs();
        }
    }

    @Override
    public String getModID() {
        return "flipside";
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}
