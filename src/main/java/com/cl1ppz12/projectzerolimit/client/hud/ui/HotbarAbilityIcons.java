package com.cl1ppz12.projectzerolimit.client.hud.ui;

import com.cl1ppz12.projectzerolimit.ability.Ability;
import com.cl1ppz12.projectzerolimit.client.ClientAbilityCache;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

@Environment(EnvType.CLIENT)
public final class HotbarAbilityIcons implements HudRenderCallback {

    public static void init() {
        HudRenderCallback.EVENT.register(new HotbarAbilityIcons());
    }

    @Override
    public void onHudRender(DrawContext ctx, RenderTickCounter tick) {
        MinecraftClient client = MinecraftClient.getInstance();   // <-- add this
        if (client.options.hudHidden) return;

        int left = (ctx.getScaledWindowWidth() - 182) / 2;
        int top  = ctx.getScaledWindowHeight() - 22 - 18;

        for (int slot = 0; slot < 5; slot++) {
            Ability ab = ClientAbilityCache.getBound(slot);
            if (ab == null) continue;

            int x = left + slot * 20 + 3;
            ctx.drawTexture(ab.getIconTexture(), x, top,
                    0, 0, 16, 16, 16, 16);
        }
    }
}