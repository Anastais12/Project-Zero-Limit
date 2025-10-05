package com.cl1ppz12.projectzerolimit.screen.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class MaskScreenHandler extends ScreenHandler {

    private final Inventory maskInv = new SimpleInventory(6);

    public MaskScreenHandler(PlayerInventory playerInv) {
        super(null, 0);
        addSlot(new Slot(maskInv, 0,  54, 28));
        addSlot(new Slot(maskInv, 1,   18, 64));
        addSlot(new Slot(maskInv, 2,  54, 100));
        addSlot(new Slot(maskInv, 3,  108, 100));
        addSlot(new Slot(maskInv, 4, 144, 64));
        addSlot(new Slot(maskInv, 5,  108, 28));
    }

    @Override public boolean canUse(PlayerEntity p) { return true; }
    @Override public ItemStack quickMove(PlayerEntity p, int i) { return ItemStack.EMPTY; }
}