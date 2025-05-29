package com.cl1ppz12.projectzerolimit.item;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ZeroLimitItemGroups {
    public static final ItemGroup ZERO_LIMIT_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(ProjectZeroLimit.MOD_ID, "steel"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ZeroLimitItems.STEEL))
                    .displayName(Text.translatable("itemgroup.project_zero_limit.zero_limit"))
                    .entries((displayContext, entries) -> {
                        entries.add(ZeroLimitItems.STEEL);
                    }).build());


    public static void registerItemGroups() {
        ProjectZeroLimit.LOGGER.info("Registering Item Groups for " + ProjectZeroLimit.MOD_ID);
    }
}
