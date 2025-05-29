// projectzerolimit/client/gui/screen/ingame/AbilityInventoryScreen.java
package com.cl1ppz12.projectzerolimit.client.gui.screen.ingame;

import com.cl1ppz12.projectzerolimit.inventory.AbilityInventoryScreenHandler;
import com.cl1ppz12.projectzerolimit.client.util.ClientPacketUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class AbilityInventoryScreen extends HandledScreen<AbilityInventoryScreenHandler> {

    public AbilityInventoryScreen(AbilityInventoryScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 222;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        context.fill(x, y, x + backgroundWidth, y + backgroundHeight, 0xA0000000); // Semi-transparent black background
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Check if Z, X, C, or V was pressed
        if (keyCode == GLFW.GLFW_KEY_Z || keyCode == GLFW.GLFW_KEY_X ||
                keyCode == GLFW.GLFW_KEY_C || keyCode == GLFW.GLFW_KEY_V) {

            // Use the protected 'focusedSlot' field directly
            Slot currentlyFocusedSlot = this.focusedSlot;

            if (currentlyFocusedSlot != null && currentlyFocusedSlot.hasStack()) {
                // Determine the target active ability slot index based on the key pressed
                int targetActiveSlotIndex = -1;
                if (keyCode == GLFW.GLFW_KEY_Z) {
                    targetActiveSlotIndex = 0;
                } else if (keyCode == GLFW.GLFW_KEY_X) {
                    targetActiveSlotIndex = 1;
                } else if (keyCode == GLFW.GLFW_KEY_C) {
                    targetActiveSlotIndex = 2;
                } else if (keyCode == GLFW.GLFW_KEY_V) {
                    targetActiveSlotIndex = 3;
                }

                if (targetActiveSlotIndex != -1) {
                    // Get the ItemStack from the hovered slot
                    ItemStack hoveredStack = currentlyFocusedSlot.getStack();
                    // Send the packet to the server to update the active ability slot
                    ClientPacketUtil.sendUpdateActiveAbilitySlotPacket(targetActiveSlotIndex, hoveredStack.copy()); // Send a copy
                    return true; // Indicate that the key press was handled
                }
            }
        }

        // Call the super method for other key presses (like inventory hotkeys, escape, etc.)
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}