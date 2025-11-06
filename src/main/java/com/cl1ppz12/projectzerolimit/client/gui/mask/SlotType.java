package com.cl1ppz12.projectzerolimit.client.gui.mask;

import com.cl1ppz12.projectzerolimit.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.item.ItemPredicate;

public enum SlotType {
    ORANGE(ModItems.ORANGE_GEM),
    YELLOW(ModItems.YELLOW_GEM),
    RED(ModItems.RED_GEM),
    BLUE(ModItems.BLUE_GEM),
    GREEN(ModItems.GREEN_GEM),
    PURPLE(ModItems.PURPLE_GEM);

    private final Item item;

    SlotType(Item item) {
        this.item = item;
    }

    public boolean test(ItemStack stack) {
        return stack.getItem().equals(item);
    }
}