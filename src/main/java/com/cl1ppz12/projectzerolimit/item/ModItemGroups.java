package com.cl1ppz12.projectzerolimit.item;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final RegistryKey<ItemGroup> ZEROLIMT_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(ProjectZeroLimit.MOD_ID, "zero_limit_item_group"));
    public static final RegistryKey<ItemGroup> ZEROLIMT_GEMS_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(ProjectZeroLimit.MOD_ID, "zero_limit_gems_item_group"));

    public static final ItemGroup ZEROLIMIT_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.ONI_MASK))
            .displayName(Text.translatable("itemgroup.project_zero_limit.zero_limit"))
            .build();

    public static final ItemGroup ZEROLIMIT_GEMS_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.RED_GEM))
            .displayName(Text.translatable("itemgroup.project_zero_limit.zero_limit_gems"))
            .build();


    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, ZEROLIMT_ITEM_GROUP_KEY, ZEROLIMIT_ITEM_GROUP);
        Registry.register(Registries.ITEM_GROUP, ZEROLIMT_GEMS_ITEM_GROUP_KEY, ZEROLIMIT_GEMS_ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(ZEROLIMT_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.ONI_MASK);
        });

        ItemGroupEvents.modifyEntriesEvent(ZEROLIMT_GEMS_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.RED_GEM);
            itemGroup.add(ModItems.YELLOW_GEM);
            itemGroup.add(ModItems.ORANGE_GEM);
            itemGroup.add(ModItems.BLUE_GEM);
            itemGroup.add(ModItems.PURPLE_GEM);
            itemGroup.add(ModItems.GREEN_GEM);
        });
    }
}
