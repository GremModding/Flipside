package io.gremstudio.flipside.entities.jello;

import io.gremstudio.flipside.Flipside;
import io.gremstudio.flipside.registry.FlipsideEntities;
import io.gremstudio.flipside.registry.FlipsideItems;
import io.gremstudio.gremlib.api.util.VectorUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.function.BiConsumer;

public class SculkJelloBall extends AbstractJelloBall implements VibrationSystem {
    private final DynamicGameEventListener<Listener> dynamicGameEventListener;
    private Data vibrationData;
    private User vibrationUser;
    int heardVibration = 21;
    int spawningTimer = 20;
    private Vector3fc targetPosition = this.position().toVector3f();
    protected static final EntityDataAccessor<Integer> DATA_VIB = SynchedEntityData.defineId(SculkJelloBall.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Vector3fc> DATA_TARGET_POS = SynchedEntityData.defineId(SculkJelloBall.class, EntityDataSerializers.VECTOR3);


    public SculkJelloBall(EntityType<? extends AbstractJelloBall> entityType, Level level) {
        super(entityType, level);
        this.dynamicGameEventListener = new DynamicGameEventListener<>(new Listener(this));
        this.vibrationUser = new VibrationUser();
        this.vibrationData = new Data();
        setDataHeardVibration(21);
        setDataTargetPos(this.position().toVector3f());
    }

    public SculkJelloBall(LivingEntity livingEntity, Level level, ItemStack itemStack) {
        super(FlipsideEntities.SCULK_JELLO_BALL, livingEntity, level, itemStack);
        this.dynamicGameEventListener = new DynamicGameEventListener<>(new Listener(this));
        this.vibrationUser = new VibrationUser();
        this.vibrationData = new Data();
        setDataHeardVibration(21);
        setDataTargetPos(this.position().toVector3f());
    }

    public SculkJelloBall(double x, double y, double z, Level level, ItemStack itemStack) {
        super(FlipsideEntities.SCULK_JELLO_BALL, x, y, z, level, itemStack);
        this.dynamicGameEventListener = new DynamicGameEventListener<>(new Listener(this));
        this.vibrationUser = new VibrationUser();
        this.vibrationData = new Data();
        setDataHeardVibration(21);
        setDataTargetPos(this.position().toVector3f());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VIB, 0);
        builder.define(DATA_TARGET_POS, this.position().toVector3f());
    }

    @Override
    protected double getDefaultGravity() {
        return 0;
    }

    @Override
    public void tick() {
        if (level() instanceof ServerLevel serverLevel) {
            Ticker.tick(serverLevel, this.vibrationData, this.vibrationUser);
        }

        if (spawningTimer > 0) {
            double yMove = -Math.cos(2*Math.PI*((double)spawningTimer/20))+1;
            setDeltaMovement(0, yMove*0.1, 0);
            spawningTimer--;
        } else if (getDataHeardVibration() > 20) {
            this.setDeltaMovement(0, 0, 0);
        } else {
            heardVibration--;
            targetPosition = getDataTargetPos();
            Vec3 movement = VectorUtil.distanceBetween(position(), VectorUtil.fromVec3ftoVec3(targetPosition));
            setDeltaMovement(VectorUtil.scaleVec3To(movement, 0.05));
            if (heardVibration <= 0 || VectorUtil.fromVec3ftoBlockpos(targetPosition).equals(VectorUtil.fromVec3ftoBlockpos(position().toVector3f()))) {
                heardVibration = 21;
                this.setDeltaMovement(0, 0, 0);
            }
        }

        super.tick();
    }



    private int getDataHeardVibration() {
        return this.entityData.get(DATA_VIB);
    }

    public void setDataHeardVibration(int heardVibration) {
        this.entityData.set(DATA_VIB, heardVibration);
    }

    private Vector3fc getDataTargetPos() {
        return this.entityData.get(DATA_TARGET_POS);
    }

    public void setDataTargetPos(Vector3f pos) {
        this.entityData.set(DATA_TARGET_POS, pos);
    }

    @Override
    public JelloStates getState() {
        return JelloStates.SCULK;
    }

    @Override
    protected Item getDefaultItem() {
        return FlipsideItems.JELLO;
    }



    @Override
    public Data getVibrationData() {
        return this.vibrationData;
    }

    @Override
    public User getVibrationUser() {
        return this.vibrationUser;
    }

    @Override
    public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerLevel> listenerConsumer) {
        if (this.level() instanceof ServerLevel serverLevel) {
            listenerConsumer.accept(this.dynamicGameEventListener, serverLevel);
        }
    }

    class VibrationUser implements User {
        private static final int GAME_EVENT_LISTENER_RANGE = 16;
        private final PositionSource positionSource = new EntityPositionSource(SculkJelloBall.this, SculkJelloBall.this.getEyeHeight());

        @Override
        public int getListenerRadius() {
            return GAME_EVENT_LISTENER_RANGE;
        }

        @Override
        public PositionSource getPositionSource() {
            return this.positionSource;
        }

        @Override
        public TagKey<GameEvent> getListenableEvents() {
            return GameEventTags.WARDEN_CAN_LISTEN;
        }

        @Override
        public boolean canTriggerAvoidVibration() {
            return true;
        }

        @Override
        public boolean canReceiveVibration(ServerLevel level, BlockPos pos, Holder<GameEvent> gameEvent, GameEvent.Context context) {
            return spawningTimer <= 0 && (!SculkJelloBall.this.ownedBy(context.sourceEntity()));
        }

        @Override
        public void onReceiveVibration(
                ServerLevel level, BlockPos pos, Holder<GameEvent> gameEvent, @Nullable Entity entity, @Nullable Entity playerEntity, float distance
        ) {
            SculkJelloBall.this.heardVibration = 20;
            setDataHeardVibration(20);
            Vector3f targetPos = pos.getCenter().toVector3f();
            if (entity != null) {
                targetPos = new Vector3f((float) entity.getX(), (float) entity.getEyeY(), (float) entity.getZ());
            }

            SculkJelloBall.this.targetPosition = targetPos;
            setDataTargetPos(targetPos);

            Flipside.INSTANCE.getLogger().info("Vibration received");
        }
    }
}
