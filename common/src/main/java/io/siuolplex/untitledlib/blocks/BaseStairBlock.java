package io.siuolplex.untitledlib.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Most mods have one of these! Why did Mojang make the constructor protected? Beats me! <br>
 * Either way, you can use this instead of the normal stairs.
 */
public class BaseStairBlock extends StairBlock {
    public static final MapCodec<BaseStairBlock> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(propertiesCodec())
                    .apply(inst, BaseStairBlock::new)
    );

    public BaseStairBlock(Properties properties) {
        super(Blocks.STONE.defaultBlockState(), properties);
    }

    @Override
    public @NotNull MapCodec<? extends StairBlock> codec() {
        return CODEC;
    }

    @Override
    public float getExplosionResistance() {
        return this.explosionResistance;
    }
}
