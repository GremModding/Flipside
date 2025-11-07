package io.siuolplex.untitledlib.registration;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Used to allow us to streamline the registration process in the common code.
 * @param <T> Type to be registered
 */
public class DelayedRegistry<T> {
    private static final Map<Pair<String, ResourceKey<? extends Registry<?>>>, DelayedRegistry<?>> registries = new HashMap<>();
    private final String modid;
    private final Registry<T> registry;
    private final Map<ResourceKey<T>, T> objMapping = new LinkedHashMap<>();

    public DelayedRegistry(String modid, Registry<T> registry) {
        this.modid = modid;
        this.registry = registry;
        registries.put(Pair.of(modid, registry.key()), this);
    }

    public void addToDelayedRegistry(ResourceKey<T> key, T value) {
        objMapping.put(key, value);
    }

    public void addToDelayedRegistry(ResourceLocation id, T value) {
        addToDelayedRegistry(ResourceKey.create(registry.key(), id), value);
    }

    public void addToDelayedRegistry(String name, T value) {
        addToDelayedRegistry(ResourceKey.create(registry.key(), ResourceLocation.fromNamespaceAndPath(modid, name)), value);
    }

    public void fireRegistry() {
        for (Map.Entry<ResourceKey<T>, T> entry : objMapping.entrySet()) {
            Registry.register(registry, entry.getKey(), entry.getValue());
        }
    }

    public boolean keyMatches(ResourceKey<? extends Registry<?>> key) {
        return registry.key().equals(key);
    }

    public ResourceKey<? extends Registry<T>> getKey() {
        return registry.key();
    }

    public String getModid() {
        return modid;
    }

    public static DelayedRegistry<?> getRegistry(String modid, ResourceKey<? extends Registry<?>> key) {
        return getRegistry(Pair.of(modid, key));
    }

    public static DelayedRegistry<?> getRegistry(Pair<String, ResourceKey<? extends Registry<?>>> pair) {
        return registries.get(pair);
    }

    /**
     * Method for when running under a Fabric-based environment. Will fire registration on all of its registries.
     * @param modid The mod id.
     */
    public static void fireAllRegistriesInMod(String modid) {
        for (Map.Entry<Pair<String, ResourceKey<? extends Registry<?>>>, DelayedRegistry<?>> entry : registries.entrySet()) {
            if (entry.getKey().getFirst().equals(modid)) {
                entry.getValue().fireRegistry();
            }
        }
    }
}
