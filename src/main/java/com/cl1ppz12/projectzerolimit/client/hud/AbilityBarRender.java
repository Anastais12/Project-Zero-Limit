package com.cl1ppz12.projectzerolimit.client.hud;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.cl1ppz12.projectzerolimit.ability.Ability;
import com.cl1ppz12.projectzerolimit.client.ClientAbilityCache;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public final class AbilityBarRender implements HudRenderCallback {

    private static final Identifier HOTBAR_TEX =
            Identifier.of(ProjectZeroLimit.MOD_ID, "textures/gui/hud/ability_hotbar.png");
    private static final Identifier POINTER_TEX =
            Identifier.of(ProjectZeroLimit.MOD_ID, "textures/gui/hud/pointer.png");

    private static final int[] PTR_X = {39, 67, 101, 135, 163};
    private static final int[] PTR_Y = {10, 10, 6, 10, 10};

    private static final int BAR_TEX_W = 223;
    private static final int BAR_TEX_H = 34;
    private static final int PTR_TEX_W = 18;
    private static final int PTR_TEX_H = 18;

    private static final float SCALE = 1.3F;

    private static final int SLOT_COUNT = 5;

    private static final int ANIM_TICKS = 10;
    private int animTicks = 0;
    private boolean open = false;

    private final MinecraftClient mc = MinecraftClient.getInstance();

    private int selectedIndex = 0;

    public static void init() {
        HudRenderCallback.EVENT.register(new AbilityBarRender());
        ClientTickEvents.END_CLIENT_TICK.register(client -> instance().tick());
    }

    private static AbilityBarRender INSTANCE;
    private AbilityBarRender() { INSTANCE = this; }
    private static AbilityBarRender instance() { return INSTANCE; }

    public static void setOpen(boolean shouldOpen) {
        if (INSTANCE != null) {
            INSTANCE.open = shouldOpen;
        }
    }

    public static void scrollSelection(double verticalScroll) {
        if (INSTANCE == null || !INSTANCE.open) return;

        int dir = (int) -Math.signum(verticalScroll);   // negated so “up” = previous slot
        if (dir == 0) return;

        INSTANCE.selectedIndex += dir;

        /* wrap instead of modulo – keeps index always positive */
        while (INSTANCE.selectedIndex < 0) INSTANCE.selectedIndex += SLOT_COUNT;
        while (INSTANCE.selectedIndex >= SLOT_COUNT) INSTANCE.selectedIndex -= SLOT_COUNT;
    }


    public void tick() {
        if (mc.player == null || mc.currentScreen != null) return;

        if (open && animTicks < ANIM_TICKS) animTicks++;
        if (!open && animTicks > 0)         animTicks--;
    }

    private boolean wasPressed(int key) {
        return GLFW.glfwGetKey(mc.getWindow().getHandle(), key) == GLFW.GLFW_PRESS;
    }

    public static boolean isOpen() { return INSTANCE != null && INSTANCE.animTicks > 0; }

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter counter) {
        if (mc.player == null || mc.options.hudHidden) return;

        float prog = (float) animTicks / ANIM_TICKS;
        if (prog == 0f) return;

        int screenWidth  = drawContext.getScaledWindowWidth();
        int screenHeight = drawContext.getScaledWindowHeight();

        int drawW = (int) (BAR_TEX_W * SCALE);
        int drawH = (int) (BAR_TEX_H * SCALE);

        int baseY = screenHeight - 22 - drawH - 4;
        int startY = screenHeight;
        int barX = (screenWidth - drawW) / 2;
        int barY = (int) (startY + (baseY - startY) * prog);

        drawContext.getMatrices().push();
        drawContext.getMatrices().translate(barX, barY, 0);
        drawContext.getMatrices().scale(SCALE, SCALE, 1f);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        drawContext.drawTexture(HOTBAR_TEX, 0, 0,
                0, 0, BAR_TEX_W, BAR_TEX_H, BAR_TEX_W, BAR_TEX_H);
        drawContext.getMatrices().pop();

        int ptrDrawW = (int) (PTR_TEX_W * SCALE);
        int ptrDrawH = (int) (PTR_TEX_H * SCALE);
        int ptrX = barX + (int) (PTR_X[selectedIndex] * SCALE);
        int ptrY = barY + (int) (PTR_Y[selectedIndex] * SCALE);

        drawContext.getMatrices().push();
        drawContext.getMatrices().translate(ptrX, ptrY, 0);
        drawContext.getMatrices().scale(SCALE, SCALE, 1f);
        drawContext.drawTexture(POINTER_TEX, 0, 0,
                0, 0, PTR_TEX_W, PTR_TEX_H, PTR_TEX_W, PTR_TEX_H);
        drawContext.getMatrices().pop();

        Ability ability = ClientAbilityCache.getBound(selectedIndex);
        if (ability != null) {
            int hotbarX = (drawContext.getScaledWindowWidth() - 182) / 2;
            int hotbarY = drawContext.getScaledWindowHeight() - 22;
            int iconX   = hotbarX + selectedIndex * 20 + 2;
            int iconY   = hotbarY - 18;
            drawContext.drawTexture(
                    ability.getIconTexture(),
                    iconX, iconY,
                    0, 0, 16, 16, 16, 16);
        }
    }

    public static int getSelectedIndex() {
        return INSTANCE == null ? 0 : INSTANCE.selectedIndex;
    }
}
