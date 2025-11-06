package com.cl1ppz12.projectzerolimit.client.gui;

import com.cl1ppz12.projectzerolimit.ability.Ability;
import com.cl1ppz12.projectzerolimit.network.payload.c2s.CategorySwitchPayload;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;

@Environment(EnvType.CLIENT)
public class AbilityInventoryScreen extends Screen {

    /* -------------------- DATA -------------------- */
    private final List<Ability> owned;
    private List<Ability> filtered = new ArrayList<>();
    private Ability.AbilityType currentTab = Ability.AbilityType.ONI_ART;

    /* -------------------- TEXTURES -------------------- */
    private static final Identifier BACKGROUND =
            Identifier.of("project_zero_limit", "textures/gui/ability_inventory.png");

    /* 16×16 tab buttons (click area) */
    private static final Identifier TAB_ONI_ART =
            Identifier.of("project_zero_limit", "textures/gui/inventory/ability/oni_art_tab.png");
    private static final Identifier TAB_ONI_PROTOCOL =
            Identifier.of("project_zero_limit", "textures/gui/inventory/ability/oni_protocol_tab.png");
    private static final Identifier TAB_PASSIVE =
            Identifier.of("project_zero_limit", "textures/gui/inventory/ability/passive_tab.png");
    private static final Identifier TAB_INNATE =
            Identifier.of("project_zero_limit", "textures/gui/inventory/ability/innate_tab.png");

    /* 53×53 big icons (top-left region) – drop your 53×53 png files here */
    private static final Identifier ICON_ONI_ART = Identifier.of("project_zero_limit", "textures/gui/inventory/ability/oni_art_icon.png");
    private static final Identifier ICON_ONI_PROTOCOL = Identifier.of("project_zero_limit", "textures/gui/inventory/ability/oni_protocol_icon.png");
    private static final Identifier ICON_PASSIVE = Identifier.of("project_zero_limit", "textures/gui/inventory/ability/passive_icon.png");
    private static final Identifier ICON_INNATE = Identifier.of("project_zero_limit", "textures/gui/inventory/ability/innate_icon.png");

    /* -------------------- CONSTANTS -------------------- */
    private static final int BG_W = 384;
    private static final int BG_H = 248;

    public AbilityInventoryScreen(List<Ability> owned) {
        super(Text.translatable("screen.project_zero_limit.abilities"));
        this.owned = owned;
        rebuildFiltered();
    }

    /* ===============================================================
                               RENDER
       =============================================================== */
    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx, mouseX, mouseY, delta);

        int bgX = (width - BG_W) / 2;
        int bgY = (height - BG_H) / 2;

        /* background */
        ctx.drawTexture(BACKGROUND, bgX, bgY, 0, 0, BG_W, BG_H, BG_W, BG_H);

        /* 53×53 icon at 41,44 (top-left of texture) */
        drawBigIcon(ctx, ICON_ONI_ART,      bgX + 41, bgY + 44, currentTab == Ability.AbilityType.ONI_ART);
        drawBigIcon(ctx, ICON_ONI_PROTOCOL, bgX + 41, bgY + 44, currentTab == Ability.AbilityType.ONI_PROTOCOL);
        drawBigIcon(ctx, ICON_PASSIVE,      bgX + 41, bgY + 44, currentTab == Ability.AbilityType.PASSIVE);
        drawBigIcon(ctx, ICON_INNATE,       bgX + 41, bgY + 44, currentTab == Ability.AbilityType.INNATE);

        /* 16×16 tab buttons (click targets) – same positions you already had */
        drawTab(ctx, TAB_ONI_ART,      bgX + 52,  bgY + 17, currentTab == Ability.AbilityType.ONI_ART);
        drawTab(ctx, TAB_ONI_PROTOCOL, bgX + 100, bgY + 17, currentTab == Ability.AbilityType.ONI_PROTOCOL);
        drawTab(ctx, TAB_PASSIVE,      bgX + 148, bgY + 17, currentTab == Ability.AbilityType.PASSIVE);
        drawTab(ctx, TAB_INNATE,       bgX + 196, bgY + 17, currentTab == Ability.AbilityType.INNATE);

        /* filtered ability grid */
        for (int i = 0; i < filtered.size(); i++) {
            Ability ab = filtered.get(i);
            int slotX = bgX + 40 + (i % 8) * 22;
            int slotY = bgY + 40 + (i / 8) * 22;

            ctx.drawTexture(ab.getIconTexture(), slotX, slotY, 0, 0, 16, 16, 16, 16);

            if (mouseX >= slotX && mouseX < slotX + 16 &&
                    mouseY >= slotY && mouseY < slotY + 16) {
                ctx.drawTooltip(textRenderer, ab.getDescription(), mouseX, mouseY);
            }
        }
    }

    /* ===============================================================
                               CLICKS
       =============================================================== */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (client == null || client.player == null) return super.mouseClicked(mouseX, mouseY, button);

        int bgX = (width - BG_W) / 2;
        int bgY = (height - BG_H) / 2;
        int mx = (int)(mouseX - bgX);
        int my = (int)(mouseY - bgY);

        /* -------- 16×16 tab buttons -------- */
        Ability.AbilityType hit = null;
        if (mx >= 52  && mx < 68  && my >= 17 && my < 33) hit = Ability.AbilityType.ONI_ART;
        if (mx >= 100 && mx < 116 && my >= 17 && my < 33) hit = Ability.AbilityType.ONI_PROTOCOL;
        if (mx >= 148 && mx < 164 && my >= 17 && my < 33) hit = Ability.AbilityType.PASSIVE;
        if (mx >= 196 && mx < 212 && my >= 17 && my < 33) hit = Ability.AbilityType.INNATE;

        if (hit != null && button == 0) {
            currentTab = hit;
            rebuildFiltered();
            // OPTIONAL: notify server only when you REALLY need it
            // ClientPlayNetworking.send(new CategorySwitchPayload(hit));
            return true;
        }

        /* -------- old grid / delete logic stays here -------- */
        return super.mouseClicked(mouseX, mouseY, button);
    }

    /* ===============================================================
                               HELPERS
       =============================================================== */
    private void rebuildFiltered() {
        filtered.clear();
        for (Ability a : owned) if (a.getType() == currentTab) filtered.add(a);
    }

    /* 53×53 icon – top-left region of texture – at gui xy 41,44 */
    private void drawBigIcon(DrawContext ctx, Identifier texture, int x, int y, boolean active) {
        ctx.getMatrices().push();
        if (active) {
            ctx.getMatrices().translate(0, -1, 0);               // 1 px lift
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        } else {
            RenderSystem.setShaderColor(0.7f, 0.7f, 0.7f, 1f); // greyed out
        }
        ctx.drawTexture(texture, x, y, 0, 0, 53, 53, 53, 53);
        ctx.getMatrices().pop();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

    /* 16×16 tab button (click area) */
    private void drawTab(DrawContext ctx, Identifier texture, int x, int y, boolean active) {
        ctx.getMatrices().push();
        if (active) {
            ctx.getMatrices().translate(0, -1, 0);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        } else {
            RenderSystem.setShaderColor(0.7f, 0.7f, 0.7f, 1f);
        }
        ctx.drawTexture(texture, x, y, 0, 0, 16, 16, 16, 16);
        ctx.getMatrices().pop();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}