package io.gremstudio.flipside.entities.jello;

import io.gremstudio.flipside.registry.FlipsideEntities;
import io.gremstudio.flipside.registry.FlipsideItems;
import io.gremstudio.gremlib.util.AdditionalProjectileUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class FireJelloBall extends AbstractJelloBall {
    public FireJelloBall(EntityType<? extends AbstractJelloBall> entityType, Level level) {
        super(entityType, level);
    }

    public FireJelloBall(double x, double y, double z, Level level, ItemStack itemStack) {
        super(FlipsideEntities.FIRE_JELLO_BALL, x, y, z, level, itemStack);
    }

    public FireJelloBall(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(FlipsideEntities.FIRE_JELLO_BALL, livingEntity, level, itemStack);
    }



    @Override
    protected Item getDefaultItem() {
        return FlipsideItems.FIRE_JELLO;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!level().isClientSide()) {
            boolean fullAllyCheck = true; // Will be true if all entities are not allies
            for (Entity entity : level().getEntities(this, new AABB(this.getX() - 3, this.getY() - 3.0, this.getZ() - 3, this.getX() + 3, this.getY() + 3, this.getZ() + 3))) {
                boolean allyCheck = (getOwner() == null || (!getOwner().isAlliedTo(entity))); // Basically true if the entity isn't an ally
                if (entity instanceof LivingEntity && !ownedBy(entity) && allyCheck) {
                    entity.setRemainingFireTicks(40);
                    entity.setSharedFlagOnFire(true);
                }

                if (entity instanceof LivingEntity livingEntity) {
                    if (entity.is(JellomancyEntityTagRegistry.HEALED_BY_FIRE_JELLO)) {
                        livingEntity.heal(3);
                    } else if (allyCheck && level() instanceof ServerLevel serverLevel) {
                        entity.hurtServer(serverLevel, level().damageSources().inFire(), 3);
                    }
                }

                if (entity instanceof Creeper creepyCreeper && allyCheck) {
                    creepyCreeper.ignite();
                }

                fullAllyCheck = allyCheck && fullAllyCheck;
            }
            if (fullAllyCheck) {
                if (BaseFireBlock.canBePlacedAt(level(), this.getBlockPosBelowThatAffectsMyMovement().above(), this.getDirection().getOpposite())) {
                    BlockState blockState2 = BaseFireBlock.getState(level(), this.getBlockPosBelowThatAffectsMyMovement().above());
                    level().setBlock(this.getBlockPosBelowThatAffectsMyMovement().above(), blockState2, 11);
                    level().gameEvent(this, GameEvent.BLOCK_PLACE, this.getBlockPosBelowThatAffectsMyMovement().above());
                }
            }
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
                    level().addParticle(ParticleTypes.FLAME,
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

    public boolean canDiscardOnHit() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        HitResult aSecondHitResult = AdditionalProjectileUtil.getHitResultOnMoveVectorInclLiquid(this, this::canHitEntity, ClipContext.Block.VISUAL);
        if (aSecondHitResult instanceof BlockHitResult blockHitResult && level().getBlockState(blockHitResult.getBlockPos()).is(Blocks.LAVA)) {
            if (!this.getOnPos().equals(blockHitResult.getBlockPos())){
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.9, -0.75, 0.9));
                if (this.getDeltaMovement().length() < 0.25) {
                    this.onHit(aSecondHitResult);
                }
            } else {
                this.onHit(aSecondHitResult);
            }
        }
    }

    @Override
    protected ProjectileDeflection hitTargetOrDeflectSelf(HitResult hitResult) {
        return super.hitTargetOrDeflectSelf(hitResult);
    }

    @Override
    public JelloStates getState() {
        return JelloStates.FIRE;
    }
}
