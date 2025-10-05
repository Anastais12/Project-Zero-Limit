package com.cl1ppz12.projectzerolimit.hud;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class AbilityBarHud implements HudRenderCallback {
    private static final Identifier ABILITY_BAR_TEXTURE = Identifier.of(ProjectZeroLimit.MOD_ID, "textures/gui/ability_bar.png");
    private static final int SLOT_SIZE = 20;
    private static final int SLOT_SPACING = 2;
    private static final int BAR_WIDTH = 9 * (SLOT_SIZE + SLOT_SPACING) - SLOT_SPACING;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;

        if (player == null) return;

        PlayerAbilityData abilityData = PlayerAbilityData.get(player);
        if (abilityData == null) return;

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        // Position at bottom center of screen
        int x = (screenWidth - BAR_WIDTH) / 2;
        int y = screenHeight - 30;

        // Render ability bar background
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ABILITY_BAR_TEXTURE);

        // Draw slots
        for (int i = 0; i < 9; i++) {
            int slotX = x + i * (SLOT_SIZE + SLOT_SPACING);
            int slotY = y;

            // Draw slot background
            drawContext.drawTexture(ABILITY_BAR_TEXTURE, slotX, slotY, 0, 0, SLOT_SIZE, SLOT_SIZE);

            // Draw ability icon if present
            var slot = abilityData.getInventory().getSlot(i);
            if (slot != null && slot.hasAbility()) {
                // Draw ability icon (placeholder - you would use actual ability icons)
                drawContext.fill(slotX + 2, slotY + 2, slotX + SLOT_SIZE - 2, slotY + SLOT_SIZE - 2, 0xFF00FF00);

                // Draw cooldown overlay
                if (slot.getCooldown() > 0) {
                    float cooldownPercent = (float) slot.getCooldown() / slot.getAbility().getCooldown();
                    int overlayHeight = MathHelper.ceil(SLOT_SIZE * cooldownPercent);
                    drawContext.fill(slotX, slotY, slotX + SLOT_SIZE, slotY + overlayHeight, 0x88000000);
                }

                // Draw level indicator
                String levelText = String.valueOf(slot.getLevel());
                drawContext.drawText(client.textRenderer, levelText,
                        slotX + SLOT_SIZE - client.textRenderer.getWidth(levelText) - 1,
                        slotY + SLOT_SIZE - client.textRenderer.fontHeight - 1,
                        0xFFFFFF, true);
            }

            // Draw keybind indicator
            String keyText = String.valueOf(i + 1);
            drawContext.drawText(client.textRenderer, keyText,
                    slotX + 2, slotY - client.textRenderer.fontHeight - 1,
                    0xFFFFA0, true);
        }
    }
}
