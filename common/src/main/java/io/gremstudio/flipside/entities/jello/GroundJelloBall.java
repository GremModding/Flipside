package io.gremstudio.flipside.entities.jello;

import io.gremstudio.flipside.registry.FlipsideEntities;
import io.gremstudio.flipside.registry.FlipsideItems;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class GroundJelloBall extends AbstractJelloBall {
    public GroundJelloBall(EntityType<? extends AbstractJelloBall> entityType, Level level) {
        super(entityType, level);
    }

    public GroundJelloBall(LivingEntity livingEntity, Level level, ItemStack itemStack) {
        super(FlipsideEntities.GROUND_JELLO_BALL, livingEntity, level, itemStack);
    }

    public GroundJelloBall(double x, double y, double z, Level level, ItemStack itemStack) {
        super(FlipsideEntities.GROUND_JELLO_BALL, x, y, z, level, itemStack);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!level().isClientSide()) {
            double y = this.getY();
            if (result instanceof EntityHitResult entityHitResult) {
                if (!level().getBlockState(entityHitResult.getEntity().blockPosition().below()).canBeReplaced()) {
                    y = entityHitResult.getEntity().getY();
                } else return;
            }
            Direction direction = (result instanceof BlockHitResult blockHitResult) ? blockHitResult.getDirection() : Direction.UP;
            /*GroundSpike groundSpike = new GroundSpike(
                    level(),
                    this.getX(),
                    y,
                    this.getZ(),
                    6,
                    (LivingEntity) getOwner(),
                    Blocks.POINTED_DRIPSTONE
                            .defaultBlockState()
                            .setValue(
                                    BlockStateProperties.DRIPSTONE_THICKNESS,
                                    DripstoneThickness.FRUSTUM)
                            .setValue(
                                    BlockStateProperties.VERTICAL_DIRECTION,
                                    Direction.UP),
            Blocks.POINTED_DRIPSTONE
                    .defaultBlockState()
                    .setValue(
                            BlockStateProperties.DRIPSTONE_THICKNESS,
                            DripstoneThickness.TIP)
                    .setValue(
                            BlockStateProperties.VERTICAL_DIRECTION,
                            Direction.UP),
                    direction
            );
            level().addFreshEntity(groundSpike);*/
        }
    }

    @Override
    public JelloStates getState() {
        return JelloStates.EARTH;
    }

    @Override
    protected Item getDefaultItem() {
        return FlipsideItems.GROUND_JELLO;
    }
}
