package io.gremstudio.flipside.items;

import io.gremstudio.flipside.entities.jello.AbstractJelloBall;
import io.gremstudio.flipside.entities.jello.TransformableJelloBall;
import io.gremstudio.gremlib.api.world.interaction.context.item.ItemInteractionContext;
import io.gremstudio.gremlib.api.world.interaction.event.item.ItemWorldInteractions;
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

public class TransformableJelloItem extends AbstractJelloItem implements ItemWorldInteractions, ProjectileItem {
    public TransformableJelloItem(Projectile.ProjectileFactory<? extends AbstractJelloBall> ballFactory, Properties properties) {
        super(ballFactory, properties);
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        return new TransformableJelloBall(position.x(), position.y(), position.z(), level, itemStack);
    }

    @Override
    public void gremlib$onFrozen(ItemInteractionContext context, int ticksFrozen) {
    }

    @Override
    public void gremlib$onDamage(ItemInteractionContext context, DamageSource source) {
    }

    @Override
    public void gremlib$onFluidDipping(ItemInteractionContext context, FluidState fluidState) {
    }

    @Override
    public void gremlib$onBurnt(ItemInteractionContext context, int ticksBurning) {
    }

    @Override
    public void gremlib$onInExplosion(ItemInteractionContext context, Entity cause) {
    }

    @Override
    public void gremlib$onShocked(ItemInteractionContext context) {
    }
}
