package io.gremstudio.flipside.entities.jello;

import io.gremstudio.flipside.registry.JellomancyCardinalComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class IceJelloBall extends AbstractJelloBall {
    public IceJelloBall(EntityType<? extends AbstractJelloBall> entityType, Level level) {
        super(entityType, level);
    }

    public IceJelloBall(LivingEntity livingEntity, Level level, ItemStack itemStack) {
        super(FlipsideEntities.ICE_JELLO_BALL, livingEntity, level, itemStack);
    }

    public IceJelloBall(double x, double y, double z, Level level, ItemStack itemStack) {
        super(FlipsideEntities.ICE_JELLO_BALL, x, y, z, level, itemStack);
    }

    @Override
    public JelloStates getState() {
        return JelloStates.ICE;
    }

    @Override
    protected Item getDefaultItem() {
        return FlipsideItems.ICE_JELLO;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        boolean allyCheck = (getOwner() == null || (!getOwner().isAlliedTo(entity))); // Basically true if the entity isn't an ally
        //todo: Temporary effect, change to something better.

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 20, 4, false, false));
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 3) {
            int iMax = 8;
            for (int i = 0; i < iMax; i++) {
                int jMax = 4;
                for (int j = 0; j < jMax; j++) {
                    double xOffset = Math.sin(Math.PI*i/((double) iMax /2) + j*Math.PI/24)*((double) j/10+1);
                    double zOffset = Math.cos(Math.PI*i/((double) iMax /2) + j*Math.PI/24)*((double) j/10+1);
                    level().addParticle(ParticleTypes.SNOWFLAKE,
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            -xOffset*0.2,
                            0,
                            -zOffset*0.2);
                }
            }
        }
    }
}
