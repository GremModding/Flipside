package io.siuolplex.flipside.blocks;

import io.siuolplex.flipside.registry.FlipsideDimensions;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FlipsidePortalBlock extends Block implements Portal {
    public FlipsidePortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable TeleportTransition getPortalDestination(ServerLevel currentLevel, @NotNull Entity travelingEntity, @NotNull BlockPos portalPos) {
        ResourceKey<Level> destLevelKey = currentLevel.dimension() == FlipsideDimensions.FLIPSIDE_LEVEL_KEY ? Level.OVERWORLD : FlipsideDimensions.FLIPSIDE_LEVEL_KEY;
        ServerLevel destLevel = currentLevel.getServer().getLevel(destLevelKey);

        if (destLevel == null) {
            return null;
        } else {
            boolean isDestFlipside = destLevelKey == FlipsideDimensions.FLIPSIDE_LEVEL_KEY;

            WorldBorder worldBorder = destLevel.getWorldBorder();

            double teleportationScale = DimensionType.getTeleportationScale(currentLevel.dimensionType(), destLevel.dimensionType());
            BlockState entranceState = currentLevel.getBlockState(portalPos);
            BlockPos exitPos = worldBorder.clampToBounds(portalPos.getX() * teleportationScale, portalPos.getY(), portalPos.getZ() * teleportationScale);

            BlockUtil.FoundRectangle threeDots;
            TeleportTransition.PostTeleportTransition postTransition;

            Optional<BlockPos> optional = destLevel.getPortalForcer().findClosestPortalPosition(exitPos, isDestFlipside, worldBorder);
            if (optional.isPresent()) {
                BlockPos blockPos = optional.get();
                BlockState blockstate = destLevel.getBlockState(blockPos);
                threeDots = BlockUtil.getLargestRectangleAround(blockPos, blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, (p_351970_) -> destLevel.getBlockState(p_351970_) == blockstate);
                postTransition = TeleportTransition.PLAY_PORTAL_SOUND.then((p_351967_) -> p_351967_.placePortalTicket(blockPos));
            } else {
                Direction.Axis entranceAxis = entranceState.getOptionalValue(BlockStateProperties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
                Optional<BlockUtil.FoundRectangle> optional1 = destLevel.getPortalForcer().createPortal(exitPos, entranceAxis);
                if (optional1.isEmpty()) {
                    //LOGGER.error("Unable to create a portal, likely target out of worldborder");
                    return null;
                }

                threeDots = optional1.get();
                postTransition = TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET);
            }

            Direction.Axis horizontalAxis = Direction.Axis.X;
            Vec3 offset = new Vec3(0.5F, 0.0F, 0.0F);
            if (entranceState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
                horizontalAxis = entranceState.getValue(BlockStateProperties.HORIZONTAL_AXIS);
                BlockUtil.FoundRectangle foundRectangleNotClickbait = BlockUtil.getLargestRectangleAround(portalPos, horizontalAxis, 21, Direction.Axis.Y, 21, (predicate) -> currentLevel.getBlockState(predicate) == entranceState);
                offset = travelingEntity.getRelativePortalPosition(horizontalAxis, foundRectangleNotClickbait);
            }

            BlockPos minCornerPos = threeDots.minCorner;
            BlockState destBlockstate = destLevel.getBlockState(minCornerPos);
            Direction.Axis destAxis = destBlockstate.getOptionalValue(BlockStateProperties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
            double rectangleXSize = threeDots.axis1Size;
            double rectangleYSize = threeDots.axis2Size;
            EntityDimensions entitySize = travelingEntity.getDimensions(travelingEntity.getPose());
            int yRot = horizontalAxis == destAxis ? 0 : 90;

            double xOffset = entitySize.width() / 2.0d + (rectangleXSize - entitySize.width()) * offset.x();
            double yOffset = (rectangleYSize - entitySize.height()) * offset.y();
            double zOffset = 0.5d + offset.z();

            boolean shouldRotate = destAxis == Direction.Axis.X;

            Vec3 goalPos = new Vec3((double)minCornerPos.getX() + (shouldRotate ? xOffset : zOffset), (double)minCornerPos.getY() + yOffset, (double)minCornerPos.getZ() + (shouldRotate ? zOffset : xOffset));
            Vec3 destVec = PortalShape.findCollisionFreePosition(goalPos, destLevel, travelingEntity, entitySize);

            return new TeleportTransition(destLevel, destVec, Vec3.ZERO, yRot, 0.0F, Relative.union(Relative.DELTA, Relative.ROTATION), postTransition);
        }
    }
}
