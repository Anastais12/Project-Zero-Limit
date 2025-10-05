package com.cl1ppz12.projectzerolimit.tag;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.cl1ppz12.projectzerolimit.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ProjectZeroLimitItemTagProvider extends FabricTagProvider<Item> {
    public ProjectZeroLimitItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ITEM, registriesFuture);
    }

    public static final TagKey<Item> GEMS = TagKey.of(RegistryKeys.ITEM, Identifier.of(ProjectZeroLimit.MOD_ID, "gems"));

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(GEMS)
                .add(ModItems.RED_GEM)
                .add(ModItems.YELLOW_GEM)
                .add(ModItems.ORANGE_GEM)
                .add(ModItems.BLUE_GEM)
                .add(ModItems.PURPLE_GEM)
                .add(ModItems.GREEN_GEM)
                .setReplace(true);
    }
}
