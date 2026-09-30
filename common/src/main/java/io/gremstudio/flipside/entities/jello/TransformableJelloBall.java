package io.gremstudio.flipside.entities.jello;

import io.gremstudio.flipside.registry.FlipsideEntities;
import io.gremstudio.flipside.registry.FlipsideItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
}
