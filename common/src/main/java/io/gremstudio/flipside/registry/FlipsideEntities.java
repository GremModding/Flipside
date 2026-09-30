package io.gremstudio.flipside.registry;

import io.gremstudio.flipside.Flipside;
import io.gremstudio.flipside.entities.jello.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class FlipsideEntities {
    public static ResourceKey<EntityType<?>> TRANSFORMABLE_JELLO_BALL_KEY = ResourceKey.create(Registries.ENTITY_TYPE, Flipside.INSTANCE.createId("transformable_jello_ball"));
    public static ResourceKey<EntityType<?>> FIRE_JELLO_BALL_KEY = ResourceKey.create(Registries.ENTITY_TYPE, Flipside.INSTANCE.createId("fire_jello_ball"));
    public static ResourceKey<EntityType<?>> WIND_JELLO_BALL_KEY = ResourceKey.create(Registries.ENTITY_TYPE, Flipside.INSTANCE.createId("wind_jello_ball"));
    public static ResourceKey<EntityType<?>> WATER_JELLO_BALL_KEY = ResourceKey.create(Registries.ENTITY_TYPE, Flipside.INSTANCE.createId("water_jello_ball"));
    public static ResourceKey<EntityType<?>> SHOCK_JELLO_BALL_KEY = ResourceKey.create(Registries.ENTITY_TYPE, Flipside.INSTANCE.createId("shock_jello_ball"));
    public static ResourceKey<EntityType<?>> ICE_JELLO_BALL_KEY = ResourceKey.create(Registries.ENTITY_TYPE, Flipside.INSTANCE.createId("ice_jello_ball"));
    public static ResourceKey<EntityType<?>> GROUND_JELLO_BALL_KEY = ResourceKey.create(Registries.ENTITY_TYPE, Flipside.INSTANCE.createId("ground_jello_ball"));
    public static ResourceKey<EntityType<?>> SCULK_JELLO_BALL_KEY = ResourceKey.create(Registries.ENTITY_TYPE, Flipside.INSTANCE.createId("sculk_jello_ball"));

    public static EntityType<TransformableJelloBall> TRANSFORMABLE_JELLO_BALL = register(TRANSFORMABLE_JELLO_BALL_KEY, EntityType.Builder.<TransformableJelloBall>of(TransformableJelloBall::new, MobCategory.MISC)
            .noLootTable()
            .sized(0.5f, 0.5f)
            .clientTrackingRange(4)
            .updateInterval(10));
    public static EntityType<FireJelloBall> FIRE_JELLO_BALL = register(FIRE_JELLO_BALL_KEY, EntityType.Builder.<FireJelloBall>of(FireJelloBall::new, MobCategory.MISC)
            .noLootTable()
            .sized(0.5f, 0.5f)
            .clientTrackingRange(4)
            .updateInterval(10)
            .fireImmune());
    public static EntityType<WindJelloBall> WIND_JELLO_BALL = register(WIND_JELLO_BALL_KEY, EntityType.Builder.<WindJelloBall>of(WindJelloBall::new, MobCategory.MISC)
            .noLootTable()
            .sized(0.5f, 0.5f)
            .clientTrackingRange(4)
            .updateInterval(10));
    public static EntityType<WaterJelloBall> WATER_JELLO_BALL = register(WATER_JELLO_BALL_KEY, EntityType.Builder.<WaterJelloBall>of(WaterJelloBall::new, MobCategory.MISC)
            .noLootTable()
            .sized(0.5f, 0.5f)
            .clientTrackingRange(4)
            .updateInterval(10));
    public static EntityType<ShockJelloBall> SHOCK_JELLO_BALL = register(SHOCK_JELLO_BALL_KEY, EntityType.Builder.<ShockJelloBall>of(ShockJelloBall::new, MobCategory.MISC)
            .noLootTable()
            .sized(0.5f, 0.5f)
            .clientTrackingRange(4)
            .updateInterval(10));
    public static EntityType<IceJelloBall> ICE_JELLO_BALL = register(ICE_JELLO_BALL_KEY, EntityType.Builder.<IceJelloBall>of(IceJelloBall::new, MobCategory.MISC)
            .noLootTable()
            .sized(0.5f, 0.5f)
            .clientTrackingRange(4)
            .updateInterval(10));
    public static EntityType<GroundJelloBall> GROUND_JELLO_BALL = register(GROUND_JELLO_BALL_KEY, EntityType.Builder.<GroundJelloBall>of(GroundJelloBall::new, MobCategory.MISC)
            .noLootTable()
            .sized(0.5f, 0.5f)
            .clientTrackingRange(4)
            .updateInterval(10));
    public static EntityType<SculkJelloBall> SCULK_JELLO_BALL = register(SCULK_JELLO_BALL_KEY, EntityType.Builder.<SculkJelloBall>of(SculkJelloBall::new, MobCategory.MISC)
            .noLootTable()
            .sized(0.5f, 0.5f)
            .clientTrackingRange(4)
            .updateInterval(10));

    private static <T extends Entity> EntityType<T> register(ResourceKey<EntityType<?>> key, EntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void init() {}
}
