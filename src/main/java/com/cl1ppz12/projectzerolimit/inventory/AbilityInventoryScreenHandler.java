package com.cl1ppz12.projectzerolimit.inventory;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class AbilityInventoryScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    // Existing ability grid (e.g., for learned abilities)
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 3;
    public static final int GRID_SLOTS = GRID_WIDTH * GRID_HEIGHT; // 30 slots

    // NEW: The TOTAL_SLOTS for *this* screen handler will ONLY be the grid slots.
    // The 4 active slots will be handled separately on the client.
    public static final int TOTAL_SLOTS = GRID_SLOTS; // 30 slots

    public AbilityInventoryScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(TOTAL_SLOTS));
    }

    public AbilityInventoryScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ProjectZeroLimit.ABILITY_INVENTORY_SCREEN_HANDLER, syncId);
        checkSize(inventory, TOTAL_SLOTS);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        int i;
        int j;

        // Ability Grid Slots (30 slots)
        int gridSlotStartX = 38;
        int gridSlotStartY = 20;

        for (i = 0; i < GRID_HEIGHT; ++i) {
            for (j = 0; j < GRID_WIDTH; ++j) {
                // Add the 30 grid slots (indices 0-29)
                this.addSlot(new Slot(inventory, j + i * GRID_WIDTH, gridSlotStartX + j * 18, gridSlotStartY + i * 18));
            }
        }

        // REMOVE THE CODE THAT ADDED ACTIVE ABILITY SLOTS HERE.
        // These slots will now be rendered by a custom HUD system.
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            return ItemStack.EMPTY; // Prevent quick-move
        }
        return newStack;
    }
}