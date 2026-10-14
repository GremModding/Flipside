package io.gremstudio.flipside.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.gremstudio.flipside.Flipside;
import io.gremstudio.flipside.client.render.model.JelloBallModel;
import io.gremstudio.flipside.client.render.state.JelloBallRenderState;
import io.gremstudio.flipside.entities.jello.AbstractJelloBall;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class JelloBallEntityRenderer extends EntityRenderer<AbstractJelloBall, JelloBallRenderState> {
    public static final Identifier JELLO_BALL_TEXTURE = Identifier.fromNamespaceAndPath("flipside", "textures/entity/jello_ball.png");
    public static final ModelLayerLocation JELLO_BALL = new ModelLayerLocation(Flipside.INSTANCE.createId("jello_ball"), "main");
    private final JelloBallModel model;

    public JelloBallEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new JelloBallModel(context.bakeLayer(JELLO_BALL));
    }

    public void submit(JelloBallRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot+90));
        poseStack.mulPose(Axis.ZN.rotationDegrees(state.xRot+90));
        poseStack.scale(1.0f-(float)Math.abs(state.deltMove.y*0.25),  0.5f + (float)Math.abs(state.deltMove.y*1f), 1.0f-(float)Math.abs(state.deltMove.y*0.25));
        //poseStack.scale((state.xRot/180)+1, (state.yRot/180)+1, (state.xRot/180)+1);
        //VertexConsumer vertexConsumer = ItemFeatureRenderer.getFoilBuffer(multiBufferSource, this.model.renderType(getLocation(state.state)), false, false);
        int tintedColor = ARGB.multiply(654311423, -1);
        nodeCollector.submitModel(this.model, state, poseStack, JELLO_BALL_TEXTURE, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
        poseStack.popPose();
        super.submit(state, poseStack, nodeCollector, camera);
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

    public static void createTextures(BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>> register) {
        register.accept(JELLO_BALL, JelloBallModel::createLayer);
    }

    public Identifier getTextureLocation() {
        return Identifier.fromNamespaceAndPath("flipside", "textures/entity/jello_ball.png");
    }
}
