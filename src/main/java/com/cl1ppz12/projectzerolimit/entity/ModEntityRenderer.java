package com.cl1ppz12.projectzerolimit.entity;

import com.cl1ppz12.projectzerolimit.entity.render.BlackHoleRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ModEntityRenderer {
    public static void register() {
        EntityRendererRegistry.register(ModEntities.BLACK_HOLE, BlackHoleRenderer::new);
    }
}
