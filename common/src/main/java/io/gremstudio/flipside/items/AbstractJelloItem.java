package io.gremstudio.flipside.items;

import io.gremstudio.flipside.entities.jello.AbstractJelloBall;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public abstract class AbstractJelloItem extends Item implements ProjectileItem {
    private Projectile.ProjectileFactory<? extends AbstractJelloBall> factory;

    public AbstractJelloItem(Projectile.ProjectileFactory<? extends AbstractJelloBall> factory, Properties properties) {
        super(properties);
        this.factory = factory;
    }

    public boolean canTransform() {
        return false;
    }

    public Projectile.ProjectileFactory<? extends AbstractJelloBall> ballFactory() {
        return factory;
    };

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.SNOWBALL_THROW,
                SoundSource.NEUTRAL,
                0.5F,
                0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
        );
        if (level instanceof ServerLevel serverLevel) {
            Projectile.spawnProjectileFromRotation(ballFactory(), serverLevel, itemStack, player, 0.0F, 1.5F, 1.0F);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemStack.consume(1, player);
        return InteractionResult.SUCCESS;
    }
}
