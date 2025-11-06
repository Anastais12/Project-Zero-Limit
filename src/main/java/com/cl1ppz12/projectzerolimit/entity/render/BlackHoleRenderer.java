package com.cl1ppz12.projectzerolimit.entity.render;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.cl1ppz12.projectzerolimit.entity.custom.BlackHoleEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class BlackHoleRenderer extends EntityRenderer<BlackHoleEntity> {

    private static final Identifier CORE  = Identifier.of(ProjectZeroLimit.MOD_ID, "textures/entity/black_hole/black_hole_core.png");
    private static final Identifier OUTER = Identifier.of(ProjectZeroLimit.MOD_ID, "textures/entity/black_hole/black_hole_outer.png");
    private static final Identifier RING  = Identifier.of(ProjectZeroLimit.MOD_ID, "textures/entity/black_hole/black_hole_ring.png");

    public BlackHoleRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(BlackHoleEntity entity) {
        return null;
    }

    @Override
    public void render(BlackHoleEntity entity, float yaw, float tickDelta,
                       MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

        float age = entity.age + tickDelta;

        // OUTER  ×2
        matrices.push();
        matrices.multiply(this.dispatcher.getRotation());
        matrices.scale(2.0F, 2.0F, 1.0F);   // <-- scale
        renderQuad(matrices, vertexConsumers, OUTER, light, true);
        matrices.pop();

        // CORE   ×1 (original size)
        matrices.push();
        matrices.multiply(this.dispatcher.getRotation());
        matrices.translate(0, 0, 0.001f);
        renderQuad(matrices, vertexConsumers, CORE, light, false);
        matrices.pop();

        // RING   ×2
        matrices.push();
        matrices.multiply(this.dispatcher.getRotation());
        matrices.scale(2.0F, 2.0F, 1.0F);   // <-- scale
        matrices.translate(0, 0, 0.002f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(age * 2f));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(80));
        renderQuad(matrices, vertexConsumers, RING, light, true);
        matrices.pop();
    }

    private void renderQuad(MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                            Identifier tex, int light, boolean emissive) {

        RenderLayer layer = emissive
                ? RenderLayer.getEntityTranslucentEmissive(tex)
                : RenderLayer.getEntityTranslucent(tex);

        VertexConsumer vc = vertexConsumers.getBuffer(layer);
        Matrix4f mat = matrices.peek().getPositionMatrix();
        Vector3f normal = new Vector3f(0, 0, 1);

        Vector4f v1 = new Vector4f(-0.5f,  0.5f, 0f, 1f).mul(mat);
        Vector4f v2 = new Vector4f(-0.5f, -0.5f, 0f, 1f).mul(mat);
        Vector4f v3 = new Vector4f( 0.5f, -0.5f, 0f, 1f).mul(mat);
        Vector4f v4 = new Vector4f( 0.5f,  0.5f, 0f, 1f).mul(mat);

        /* quad vertices – no endVertex() */
        vc.vertex(v1.x, v1.y, v1.z).color(255, 255, 255, 255).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal.x, normal.y, normal.z);
        vc.vertex(v2.x, v2.y, v2.z).color(255, 255, 255, 255).texture(0, 1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal.x, normal.y, normal.z);
        vc.vertex(v3.x, v3.y, v3.z).color(255, 255, 255, 255).texture(1, 1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal.x, normal.y, normal.z);
        vc.vertex(v4.x, v4.y, v4.z).color(255, 255, 255, 255).texture(1, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal.x, normal.y, normal.z);
    }

    @Override
    public int getBlockLight(BlackHoleEntity entity, BlockPos pos) {
        return 15;
    }
}