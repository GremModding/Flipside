package io.siuolplex.flipside.datagen.worldgen.dimension;

import io.siuolplex.flipside.registry.FlipsideDimensions;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.Optional;
import java.util.OptionalLong;

public class FlipsideDimensionTypes extends DimensionTypes {
    public static void bootstrap(BootstrapContext<DimensionType> context) {
        context.register(FlipsideDimensions.FLIPSIDE_DIMTYPE_KEY, new DimensionType(
                OptionalLong.empty(),
                true, // hasSkylight
                true, // hasCeiling
                false, // ultraWarm
                true, // natural
                1.0, // coordinateScale
                false, // bedWorks
                true, // respawnAnchorWorks
                -64, // minY
                384, // height
                384, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, //infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, //effectsLocation
                0.0F, //ambientLight
                Optional.of(192), //cloudHeight
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0) // monsterSettings
        ));
    }
}
