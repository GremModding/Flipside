package io.siuolplex.flipside;

import io.siuolplex.flipside.registry.FlipsideBlocks;
import io.siuolplex.flipside.registry.FlipsideEntities;
import io.siuolplex.flipside.registry.FlipsideItems;
import io.siuolplex.gremlib.mod.GremMod;
import io.siuolplex.gremlib.mod.HasRegistration;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Flipside extends GremMod implements HasRegistration {
    private final Logger LOGGER = LoggerFactory.getLogger("Flipside");
    public static Flipside INSTANCE = null;
    Map<ResourceKey<?>, Consumer<Registry<?>>> registryMap = new HashMap<>();

    public Flipside() {
        if (INSTANCE != null) {
            throw new GremMod.GremModReinitError("Can't run a GremMod twice over!");
        }

        INSTANCE = this;
    }

    @Override
    public Map<ResourceKey<?>, Consumer<Registry<?>>> getOrMapRegistries() {
        registryMap.put(Registries.BLOCK, _ -> FlipsideBlocks.init());
        registryMap.put(Registries.ITEM, _ -> FlipsideItems.init());
        registryMap.put(Registries.ENTITY_TYPE, _ -> FlipsideEntities.init());

        return registryMap;
    }

    public void fireRegistry(Registry<?> registry) {
        if (registryMap.get(registry.key()) != null) registryMap.get(registry.key()).accept(registry);
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
