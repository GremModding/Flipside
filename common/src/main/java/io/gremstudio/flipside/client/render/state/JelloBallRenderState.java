package io.gremstudio.flipside.client.render.state;

import io.gremstudio.flipside.entities.jello.AbstractJelloBall;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.phys.Vec3;

public class JelloBallRenderState extends EntityRenderState {
    public Vec3 deltMove;
    public float xRot;
    public float yRot;
    public AbstractJelloBall.JelloStates state;
}
