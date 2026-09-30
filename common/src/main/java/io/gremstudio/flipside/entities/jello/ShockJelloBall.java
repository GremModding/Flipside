package io.gremstudio.flipside.entities.jello;

import io.gremstudio.flipside.registry.JellomancyParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ShockJelloBall extends AbstractJelloBall {
    public ShockJelloBall(EntityType<? extends AbstractJelloBall> entityType, Level level) {
        super(entityType, level);
    }

    public ShockJelloBall(LivingEntity livingEntity, Level level, ItemStack itemStack) {
        super(FlipsideEntities.SHOCK_JELLO_BALL, livingEntity, level, itemStack);
    }

    public ShockJelloBall(double x, double y, double z, Level level, ItemStack itemStack) {
        super(FlipsideEntities.SHOCK_JELLO_BALL, x, y, z, level, itemStack);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!level().isClientSide()) {
            int shockedNum = 0;
            for (Entity entity : level().getEntities(this, new AABB(this.getX() - 3, this.getY() - 3, this.getZ() - 3, this.getX() + 3, this.getY() + 3, this.getZ() + 3))) {
                boolean allyCheck = (getOwner() == null || (!getOwner().isAlliedTo(entity))); // Basically true if the entity isn't an ally
                if (entity instanceof LivingEntity && !ownedBy(entity) && allyCheck) {

                    }

                if (entity instanceof LivingEntity livingEntity) {
                    if (entity.getType().is(JellomancyEntityTagRegistry.SHOCK_IMMUNE)) {

                    } else if (allyCheck && level() instanceof ServerLevel serverLevel) {
                        entity.hurtServer(serverLevel, level().damageSources().lightningBolt(), 2 + shockedNum);
                        ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 20, 4, false, false));
                        shockedNum++;
                    }
                }
            }
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 3) {
            Vec3 hitLocale = this.getPosition(0);
            for (Entity entity : level().getEntities(this, new AABB(this.getX() - 3, this.getY() - 3, this.getZ() - 3, this.getX() + 3, this.getY() + 3, this.getZ() + 3))) {
                Vec3 entLocale = entity.getPosition(0);
                boolean allyCheck = (getOwner() == null || (!getOwner().isAlliedTo(entity))); // Basically true if the entity isn't an ally
                if (!allyCheck || entity.getType().is(JellomancyEntityTagRegistry.SHOCK_IMMUNE)) continue;

                int iMax = 32;
                for (int i = 0; i < iMax; i++) {
                    Vec3 particleLocation = Mth.lerp((double) i /iMax, hitLocale, entLocale);
                    level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                        particleLocation.x(),
                        particleLocation.y(),
                        particleLocation.z(),
                        0,
                        0,
                        0);
                }

                hitLocale = entLocale;
            }
        }
    }

    @Override
    public JelloStates getState() {
        return JelloStates.SHOCK;
    }

    @Override
    protected Item getDefaultItem() {
        return FlipsideItems.SHOCK_JELLO;
    }
}
