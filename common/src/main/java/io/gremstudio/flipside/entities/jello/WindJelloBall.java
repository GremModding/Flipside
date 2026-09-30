package io.gremstudio.flipside.entities.jello;

import io.gremstudio.flipside.util.AdditionalProjectileUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Optional;
import java.util.function.Function;

public class WindJelloBall extends AbstractJelloBall {
    int tickTimerParticle = 0;
    public WindJelloBall(EntityType<? extends AbstractJelloBall> entityType, Level level) {
        super(entityType, level);
    }

    public WindJelloBall(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(FlipsideEntities.WIND_JELLO_BALL, livingEntity, level, itemStack);
    }

    public WindJelloBall(double x, double y, double z, Level level, ItemStack itemStack) {
        super(FlipsideEntities.WIND_JELLO_BALL, x, y, z, level, itemStack);
    }

    @Override
    protected Item getDefaultItem() {
        return FlipsideItems.WIND_JELLO;
    }

    @Override
    protected double getDefaultGravity() {
        return super.getDefaultGravity() / 0.5;
    }

    @Override
    public void tick() {
        super.tick();
        if (tickTimerParticle >= 1) {
            level().addParticle(ParticleTypes.SMALL_GUST,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    -this.getDeltaMovement().x(),
                    0,
                    -this.getDeltaMovement().z());
            tickTimerParticle = 0;
        } else {
            tickTimerParticle++;
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        this.level()
                .explode(
                        this,
                        null,
                        new SimpleExplosionDamageCalculator(
                                true, false, Optional.of(1.05f), BuiltInRegistries.BLOCK.get(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())),
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        1.2F,
                        false,
                        Level.ExplosionInteraction.TRIGGER,
                        ParticleTypes.GUST_EMITTER_SMALL,
                        ParticleTypes.GUST_EMITTER_LARGE,
                        SoundEvents.WIND_CHARGE_BURST
                );
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!level().isClientSide()) {
            Entity entity = result.getEntity();
            entity.addDeltaMovement(
                    this.getDeltaMovement().multiply(0.5, 0.5, 0.5).add(
                            0,
                            (Math.abs(this.getDeltaMovement().x())+Math.abs(this.getDeltaMovement().z()))*0.2,
                            0
                    )
            );
        }

    }


    @Override
    public JelloStates getState() {
        return JelloStates.WIND;
    }

}
