package com.cl1ppz12.projectzerolimit.client.gui.mask;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryWrapper;

//TODO: Make the items go in the inventory
public class OniMaskInventory {
    private static final int SIZE = 6;
    private final ItemStack[] items = new ItemStack[SIZE];
    private final SlotType[] slotTypes = {
            SlotType.ORANGE, SlotType.YELLOW, SlotType.RED,
            SlotType.BLUE, SlotType.GREEN, SlotType.PURPLE
    };

    public OniMaskInventory() {
        for (int i = 0; i < SIZE; i++) items[i] = ItemStack.EMPTY;
    }

    public ItemStack getStack(int slot) { return items[slot]; }
    public void setStack(int slot, ItemStack stack) {
        if (slotTypes[slot].test(stack)) {
            items[slot] = stack;
        } else {
            items[slot] = ItemStack.EMPTY;
        }
    }
    public int size() { return SIZE; }

    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup lookup) {
        NbtList list = tag.getList("MaskItems", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < list.size(); i++) {
            NbtCompound t = list.getCompound(i);
            int slot = t.getByte("Slot");
            if (slot >= 0 && slot < SIZE) {
                items[slot] = ItemStack.fromNbt(lookup, t).orElse(ItemStack.EMPTY);
            }
        }
    }

    public NbtCompound writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup lookup) {
        NbtList list = new NbtList();
        for (int i = 0; i < SIZE; i++) {
            if (!items[i].isEmpty()) {
                NbtCompound t = new NbtCompound();
                t.putByte("Slot", (byte) i);
                items[i].encode(lookup, t);
                list.add(t);
            }
        }
        tag.put("MaskItems", list);
        return tag;
    }
}
