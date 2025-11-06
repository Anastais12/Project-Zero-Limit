package com.cl1ppz12.projectzerolimit.client.hud.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class IconBlit {

    private static final Identifier ATLAS =
            Identifier.of("project_zero_limit", "textures/gui/icons.png");
    private static final int ICON_SIZE = 16;
    private static final int ATLAS_SIZE = 256;

    /* blit above vanilla hot-bar slot 0-4 */
    public static void blitSlot(DrawContext ctx, int iconIndex, int slot) {
        int screenWidth  = ctx.getScaledWindowWidth();
        int screenHeight = ctx.getScaledWindowHeight();
        int hotbarX = (screenWidth - 182) / 2;
        int hotbarY = screenHeight - 22;
        int x = hotbarX + slot * 20 + 2;   // 2 px padding
        int y = hotbarY - 18;              // above slot
        blit(ctx, iconIndex, x, y);
    }

    /* raw coords */
    public static void blit(DrawContext ctx, int iconIndex, int x, int y) {
        int u = (iconIndex & 15) * ICON_SIZE;
        int v = (iconIndex >> 4) * ICON_SIZE;
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        ctx.drawTexture(ATLAS, x, y, u, v, ICON_SIZE, ICON_SIZE, ATLAS_SIZE, ATLAS_SIZE);
    }
}