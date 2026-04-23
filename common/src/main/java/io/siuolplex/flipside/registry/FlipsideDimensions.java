package io.siuolplex.flipside.registry;

import io.siuolplex.flipside.Flipside;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class FlipsideDimensions {
    public static ResourceKey<Level> FLIPSIDE_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION, Flipside.asId("flipside"));
    public static ResourceKey<DimensionType> FLIPSIDE_DIMTYPE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE, Flipside.asId("flipside"));
}
