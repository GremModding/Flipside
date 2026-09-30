package io.gremstudio.flipside.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.gremstudio.flipside.client.render.model.JelloBallModel;
import io.gremstudio.flipside.client.render.state.JelloBallRenderState;
import io.gremstudio.flipside.entities.jello.AbstractJelloBall;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class JelloBallEntityRenderer extends EntityRenderer<AbstractJelloBall, JelloBallRenderState> {
    private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath("flipside", "textures/entity/jello_ball.png");
    private final JelloBallModel model;

    public JelloBallEntityRenderer(EntityRendererProvider.Context context, AbstractJelloBall.JelloStates jelloState) {
        super(context);
        this.model = new JelloBallModel(context.bakeLayer(JelloBallModel.getLayerLocation(jelloState)));
    }

    public void render(JelloBallRenderState renderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot+90));
        poseStack.mulPose(Axis.ZN.rotationDegrees(renderState.xRot+90));
        poseStack.scale(1.0f-(float)Math.abs(renderState.deltMove.y*0.25),  0.5f + (float)Math.abs(renderState.deltMove.y*1f), 1.0f-(float)Math.abs(renderState.deltMove.y*0.25));
        //poseStack.scale((renderState.xRot/180)+1, (renderState.yRot/180)+1, (renderState.xRot/180)+1);
        VertexConsumer vertexConsumer = ItemFeatureRenderer.getFoilBuffer(multiBufferSource, this.model.renderType(getLocation(renderState.state)), false, false);
        this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(renderState, poseStack, multiBufferSource, i);
    }

    public JelloBallRenderState createRenderState() {
        return new JelloBallRenderState();
    }

    public void extractRenderState(AbstractJelloBall jelloBall, JelloBallRenderState renderState, float f) {
        super.extractRenderState(jelloBall, renderState, f);
        renderState.deltMove = jelloBall.getDeltaMovement();
        renderState.yRot = jelloBall.getYRot(f);
        renderState.xRot = jelloBall.getXRot(f);
        renderState.state = jelloBall.getState();
    }

    public Identifier getLocation(AbstractJelloBall.JelloStates state) {
        return switch (state) {
            case BASE -> Identifier.fromNamespaceAndPath("flipside", "textures/entity/jello_ball.png");
            case FIRE -> Identifier.fromNamespaceAndPath("flipside", "textures/entity/fire_jello_ball.png");
            case WATER -> Identifier.fromNamespaceAndPath("flipside", "textures/entity/water_jello_ball.png");
            case ICE -> Identifier.fromNamespaceAndPath("flipside", "textures/entity/ice_jello_ball.png");
            case WIND -> Identifier.fromNamespaceAndPath("flipside", "textures/entity/wind_jello_ball.png");
            case EARTH -> Identifier.fromNamespaceAndPath("flipside", "textures/entity/ground_jello_ball.png");
            case SHOCK -> Identifier.fromNamespaceAndPath("flipside", "textures/entity/shock_jello_ball.png");
            case SCULK -> Identifier.fromNamespaceAndPath("flipside", "textures/entity/sculk_jello_ball.png");
        };
    }
}
