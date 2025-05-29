// projectzerolimit/client/ActiveAbilityHudManager.java
package com.cl1ppz12.projectzerolimit.client;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.Identifier;

public class ActiveAbilityHudManager {

    // This list will be updated by the server sync packet
    private static final DefaultedList<ItemStack> activeAbilityHudStacks = DefaultedList.ofSize(4, ItemStack.EMPTY);

    // This is the background slot texture, distinct from the ability item's icon
    private static final Identifier CUSTOM_SLOT_TEXTURE = Identifier.of("project_zero_limit", "textures/gui/slot.png");

    public static void registerHudRender() {
        HudRenderCallback.EVENT.register(ActiveAbilityHudManager::renderHud);
    }

    private static void renderHud(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || client.isPaused() || client.options.hudHidden) {
            return;
        }

        int guiWidth = client.getWindow().getScaledWidth();
        int guiHeight = client.getWindow().getScaledHeight();

        int baseStartX = 10;
        int baseStartY = guiHeight - 30;

        for (int i = 0; i < activeAbilityHudStacks.size(); i++) {
            ItemStack stack = activeAbilityHudStacks.get(i); // Get the ItemStack for the slot

            int textureDrawX = baseStartX + (i * 24);
            int textureDrawY = baseStartY;

            // Draw the background slot texture
            context.drawTexture(
                    RenderLayer::getGuiTexturedOverlay,
                    CUSTOM_SLOT_TEXTURE,
                    textureDrawX, textureDrawY,
                    0.0F, 0.0F,
                    22, 22,
                    22, 22
            );

            String keybindChar = switch (i) {
                case 0 -> "Z";
                case 1 -> "X";
                case 2 -> "C";
                case 3 -> "V";
                default -> "";
            };

            int textWidth = client.textRenderer.getWidth(keybindChar);
            int textX = textureDrawX + (22 / 2) - (textWidth / 2);
            int textY = textureDrawY + 22 + 2;

            context.drawTextWithShadow(client.textRenderer, keybindChar, textX, textY, 0xFFFFFF);
        }
    }

    // This method is called by the S2C sync packet handler
    public static void updateActiveAbilities(DefaultedList<ItemStack> newHudStacks) {
        // Ensure the size matches to prevent issues
        if (newHudStacks == null || newHudStacks.size() != activeAbilityHudStacks.size()) {
            ProjectZeroLimit.LOGGER.error("Received mismatched active ability HUD stacks size. Expected {}, got {}", activeAbilityHudStacks.size(), (newHudStacks != null ? newHudStacks.size() : "null"));
            // Clear current slots in case of error
            for (int i = 0; i < activeAbilityHudStacks.size(); i++) {
                activeAbilityHudStacks.set(i, ItemStack.EMPTY);
            }
            return;
        }

        for (int i = 0; i < newHudStacks.size(); i++) {
            activeAbilityHudStacks.set(i, newHudStacks.get(i));
        }
    }
}