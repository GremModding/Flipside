package io.gremstudio.flipside.entities.jello;

import io.gremstudio.flipside.registry.FlipsideEntities;
import io.gremstudio.flipside.registry.FlipsideItems;
import io.gremstudio.gremlib.api.util.AdditionalProjectileUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class TransformableJelloBall extends AbstractJelloBall {
    public TransformableJelloBall(double x, double y, double z, Level level, ItemStack itemStack) {
        super(FlipsideEntities.TRANSFORMABLE_JELLO_BALL, x, y, z, level, itemStack);
    }

    public TransformableJelloBall(EntityType<? extends AbstractJelloBall> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public JelloStates getState() {
        return JelloStates.BASE;
    }

    public TransformableJelloBall(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(FlipsideEntities.TRANSFORMABLE_JELLO_BALL, livingEntity, level, itemStack);
    }

    @Override
    protected Item getDefaultItem() {
        return FlipsideItems.JELLO;
    }

    @Override
    public void tick() {
        super.tick();
        if (isInWater()) {
            setDeltaMovement(getDeltaMovement().scale((double) 5/4));
        }
        HitResult aSecondHitResult = AdditionalProjectileUtil.getHitResultOnMoveVectorInclLiquid(this, this::canHitEntity, ClipContext.Block.VISUAL);
        if (aSecondHitResult instanceof BlockHitResult blockHitResult) {
            if (level().getBlockState(blockHitResult.getBlockPos()).is(Blocks.WATER) && getDeltaMovement().y < 0) {
                if (!this.getOnPos().equals(blockHitResult.getBlockPos())) {
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.95, -0.95, 0.95));
                    if (this.getDeltaMovement().length() < 0.25) {
                        this.onHit(aSecondHitResult);
                    }
                }
            } else if (!level().getBlockState(blockHitResult.getBlockPos()).getCollisionShape(level(), blockHitResult.getBlockPos()).isEmpty() && getDeltaMovement().y < 0) {
                if (!this.getOnPos().equals(blockHitResult.getBlockPos())) {
                    // Could add a boolean that checks for if the previous tick had a bounce, and if so it will ignore the previous.
                    Vec3 test1 = blockHitResult.getDirection().getUnitVec3().multiply(blockHitResult.getDirection().getUnitVec3()).scale(-1);
                    Vec3 reflectVec = new Vec3(Math.min(1, 2*(test1.x()+0.5)), Math.min(1, 2*(test1.y()+0.5)), Math.min(1, 2*(test1.z()+0.5)));
                    this.setDeltaMovement(this.getDeltaMovement().multiply(1, 1, 1).multiply(reflectVec));
                    if (this.getDeltaMovement().length() < 0.05) {
                        this.onHit(aSecondHitResult);
                    }
                }
            }
        }
    }
}
