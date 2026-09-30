package io.gremstudio.flipside.entities.jello;

import io.gremstudio.flipside.registry.FlipsideItems;
import io.gremstudio.flipside.util.AdditionalProjectileUtil;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import static io.gremstudio.flipside.registry.FlipsideEntities.WATER_JELLO_BALL;

public class WaterJelloBall extends AbstractJelloBall{
    public WaterJelloBall(EntityType<? extends AbstractJelloBall> entityType, Level level) {
        super(entityType, level);
    }

    public WaterJelloBall(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(WATER_JELLO_BALL, livingEntity, level, itemStack);
    }

    public WaterJelloBall(double x, double y, double z, Level level, ItemStack itemStack) {
        super(WATER_JELLO_BALL, x, y, z, level, itemStack);
    }

    @Override
    public void tick() {
        super.tick();
        if (isOnFire() && level() instanceof ServerLevel) {
            onBurnUp();
            this.discard();
        }
        if (isInWater()) {
            setDeltaMovement(getDeltaMovement().scale((double) 5/4));
        }
        HitResult aSecondHitResult = AdditionalProjectileUtil.getHitResultOnMoveVectorInclLiquid(this, this::canHitEntity, ClipContext.Block.VISUAL);
        if (aSecondHitResult instanceof BlockHitResult blockHitResult)
            if (level().getBlockState(blockHitResult.getBlockPos()).is(Blocks.WATER) && getDeltaMovement().y < 0) {
                if (!this.getOnPos().equals(blockHitResult.getBlockPos())) {
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.95, -0.95, 0.95));
                    if (this.getDeltaMovement().length() < 0.25) {
                        this.onHit(aSecondHitResult);
                    }
                }
            }
    }

    private void onBurnUp() {
        if (!level().isClientSide()) {
            level().addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    0,
                    0.5,
                    0);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!level().isClientSide()) {
            Entity entity = result.getEntity();
            if (entity.isOnFire()) {
                entity.extinguishFire();
            }
        }
    }

    @Override
    protected ProjectileDeflection hitTargetOrDeflectSelf(HitResult hitResult) {
        if (hitResult instanceof BlockHitResult blockHitResult) {
            if (this.getDeltaMovement().length() < 0.5 || this.isOnFire()) {
                return super.hitTargetOrDeflectSelf(hitResult);
            } else {
                double dimMult = (level().dimensionType().ultraWarm()) ? 0.75 : 1;
                if (blockHitResult.getDirection().getAxis() == Direction.Axis.Y) {
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.8, -0.8, 0.8).scale(dimMult));
                } else this.setDeltaMovement(this.getDeltaMovement().multiply(-0.8, -0.8, -0.8).scale(dimMult));

                return ProjectileDeflection.NONE;
            }
        }
        return super.hitTargetOrDeflectSelf(hitResult);
    }

    @Override
    public JelloStates getState() {
        return JelloStates.WATER;
    }

    @Override
    protected Item getDefaultItem() {
        return FlipsideItems.WATER_JELLO;
    }
}
