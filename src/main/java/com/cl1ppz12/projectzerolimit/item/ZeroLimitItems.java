package com.cl1ppz12.projectzerolimit.item;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

import static net.minecraft.item.Items.register;

public class ZeroLimitItems {
    public static final Item STEEL = register("steel", Item::new, new Item.Settings());

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(ProjectZeroLimit.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ProjectZeroLimit.LOGGER.info("Registering Mod Items for " + ProjectZeroLimit.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(STEEL));
    }
}
