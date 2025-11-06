package com.cl1ppz12.projectzerolimit.client.gui.mask;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class OniMaskScreenHandler extends ScreenHandler {

    public OniMaskScreenHandler(int syncId, PlayerInventory playerInventory, OniMaskInventory inventory) {
        super(null, syncId);
        SimpleInventory inv = new SimpleInventory();
        for (int i = 0; i < 6; i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                inv.setStack(i, stack);
            }
        }

        /* ---------- your exact pixel positions ---------- */
        addSlot(new Slot(inv, 0, 30, 30));   // orange gem
        addSlot(new Slot(inv, 1, 30, 96));   // yellow gem
        addSlot(new Slot(inv, 2, 79, 46));   // red gem
        addSlot(new Slot(inv, 3, 79, 80));   // blue gem
        addSlot(new Slot(inv, 4, 128, 30));  // green gem
        addSlot(new Slot(inv, 5, 128, 96));  // purple gem

        /* ---------- player inventory (standard) ---------- */
        int startY = 174;
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, startY + row * 18));
        for (int col = 0; col < 9; col++)
            addSlot(new Slot(playerInventory, col, 8 + col * 18, startY + 58));
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}