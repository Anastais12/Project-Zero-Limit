package com.cl1ppz12.projectzerolimit.item;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.cl1ppz12.projectzerolimit.item.custom.*;
import com.cl1ppz12.projectzerolimit.item.custom.OniMask;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

import static com.cl1ppz12.projectzerolimit.ProjectZeroLimit.LOGGER;

public class ModItems {

    public static final Item ONI_MASK = register("oni_mask", OniMask::new, new Item.Settings());
    public static final Item BLUE_GEM = register("blue_gem", BlueGem::new, new Item.Settings());
    public static final Item GREEN_GEM = register("green_gem", GreenGem::new, new Item.Settings());
    public static final Item ORANGE_GEM = register("orange_gem", OrangeGem::new, new Item.Settings());
    public static final Item PURPLE_GEM = register("purple_gem", PurpleGem::new, new Item.Settings());
    public static final Item RED_GEM = register("red_gem", RedGem::new, new Item.Settings());
    public static final Item YELLOW_GEM = register("yellow_gem", YellowGem::new, new Item.Settings());
    public static final Item SCROLL_OF_THE_DUAL_VOID = register("scroll_of_the_dual_void", ScrollOfTheDualVoid::new, new Item.Settings());
    public static final Item BLACK_HOLE = register("black_hole", BlackHole::new, new Item.Settings());

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        Identifier id = Identifier.of(ProjectZeroLimit.MOD_ID, name);
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);

        Item item = itemFactory.apply(settings);

        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static void initialize() {
        LOGGER.info("Project Zero Limit Items are initializing!");
    }
}
