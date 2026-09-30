package io.gremstudio.flipside.client.render.model;

import io.gremstudio.flipside.client.render.state.JelloBallRenderState;
import io.gremstudio.flipside.entities.jello.AbstractJelloBall;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

import java.util.function.Function;

public class JelloBallModel extends Model<JelloBallRenderState> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("flipside", "textures/entity/jello_ball.png");

    private final ModelPart jelloBallModel;

    public JelloBallModel(ModelPart root) {
        super(root, RenderTypes::entityTranslucent);
        this.jelloBallModel = root.getChild("bb_main");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDef = new MeshDefinition();
        PartDefinition partDef = meshDef.getRoot();

        PartDefinition bb_main = partDef.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, 0.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(16, 16).addBox(-6.0F, -6.0F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4f, 10f, -4f));

        return LayerDefinition.create(meshDef, 32, 32);
    }

    public static ModelLayerLocation getLayerLocation(AbstractJelloBall.JelloStates state) {
        Identifier location = switch (state) {
            case BASE -> Identifier.fromNamespaceAndPath("flipside", "jello_ball");
            case FIRE -> Identifier.fromNamespaceAndPath("flipside", "fire_jello_ball");
            case WATER -> Identifier.fromNamespaceAndPath("flipside", "water_jello_ball");
            case ICE -> Identifier.fromNamespaceAndPath("flipside", "ice_jello_ball");
            case WIND -> Identifier.fromNamespaceAndPath("flipside", "wind_jello_ball");
            case EARTH -> Identifier.fromNamespaceAndPath("flipside", "ground_jello_ball");
            case SHOCK -> Identifier.fromNamespaceAndPath("flipside", "shock_jello_ball");
            case SCULK -> Identifier.fromNamespaceAndPath("flipside", "sculk_jello_ball");
        };
        return new ModelLayerLocation(location, "main");
    }

}
