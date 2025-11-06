package com.cl1ppz12.projectzerolimit.client.hud;

import com.cl1ppz12.projectzerolimit.client.aura.ClientAuraData;
import com.cl1ppz12.projectzerolimit.effect.ModEffects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class AuraHudRenderer implements HudRenderCallback {

    private static final Identifier ICON_TEXTURE =
            Identifier.of("project_zero_limit", "textures/gui/hud/aura_energy.png");
    private static final Identifier INACTIVE_ICON_TEXTURE =
            Identifier.of("project_zero_limit", "textures/gui/hud/aura_energy_inactive.png");

    public static void init() { HudRenderCallback.EVENT.register(new AuraHudRenderer()); }

    @Override
    public void onHudRender(DrawContext ctx, RenderTickCounter counter) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.hudHidden) return;

        int aura   = ClientAuraData.get();
        int x      = (ctx.getScaledWindowWidth()  - 16) / 2;
        int y      =  ctx.getScaledWindowHeight() - 30;

        /* ---------- texture ---------- */
        Identifier tex = mc.player.hasStatusEffect(ModEffects.COMBAT_MODE)
                ? ICON_TEXTURE
                : INACTIVE_ICON_TEXTURE;
        ctx.drawTexture(tex, x, y, 0, 0, 16, 16, 16, 16);

        /* ---------- text ---------- */
        String text = String.valueOf(aura);
        int tx = x + 16 + 3;
        int ty = y + 14;
        ctx.drawText(mc.textRenderer, text, tx, ty, 0xFFFFFF, true);
    }
}