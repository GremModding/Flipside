package io.gremstudio.flipside.registry.tags;

import io.gremstudio.flipside.Flipside;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class FlipsideEntityTags {
    public static TagKey<EntityType<?>> HEALED_BY_FIRE_JELLO = register("healed_by_fire_jello");

    public static TagKey<EntityType<?>> register(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, Flipside.INSTANCE.createId(name));
    }
}
