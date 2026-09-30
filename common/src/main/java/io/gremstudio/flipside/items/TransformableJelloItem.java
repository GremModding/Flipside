package io.gremstudio.flipside.items;

import io.gremstudio.flipside.entities.jello.AbstractJelloBall;
import io.gremstudio.flipside.entities.jello.TransformableJelloBall;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;

public class TransformableJelloItem extends AbstractJelloItem {
    public TransformableJelloItem(Projectile.ProjectileFactory<? extends AbstractJelloBall> ballFactory, Properties properties) {
        super(ballFactory, properties);
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        return new TransformableJelloBall(position.x(), position.y(), position.z(), level, itemStack);
    }

    @Override
    public void gremlib$onSelfFrozen(int ticksFrozen) {
        super.gremlib$onSelfFrozen(ticksFrozen);
    }

    @Override
    public void gremlib$onSelfDamage(DamageSource source) {
        super.gremlib$onSelfDamage(source);
    }

    @Override
    public void gremlib$onSelfFluidDipping(FluidState fluidState) {
        super.gremlib$onSelfFluidDipping(fluidState);
    }

    @Override
    public void gremlib$onSelfBurnt(int ticksBurning) {
        super.gremlib$onSelfBurnt(ticksBurning);
    }

    @Override
    public void gremlib$onSelfInExplosion(Entity cause) {
        super.gremlib$onSelfInExplosion(cause);
    }

    @Override
    public void gremlib$onSelfShocked() {
        super.gremlib$onSelfShocked();
    }
}
