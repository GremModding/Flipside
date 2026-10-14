package io.gremstudio.flipside.client;

import io.gremstudio.flipside.Flipside;
import io.gremstudio.flipside.client.render.JelloBallEntityRenderer;
import io.gremstudio.flipside.registry.FlipsideEntities;
import io.gremstudio.gremlib.api.client.mod.GremModClient;
import io.gremstudio.gremlib.api.multiloader.client.EntityRendererAPI;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class FlipsideClient extends GremModClient<Flipside> {

    public FlipsideClient(Flipside parent) {
        super(parent);

        JelloBallEntityRenderer.createTextures(EntityRendererAPI::addLayer);
        EntityRenderers.register(FlipsideEntities.TRANSFORMABLE_JELLO_BALL, JelloBallEntityRenderer::new);
    }
}
