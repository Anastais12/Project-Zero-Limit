package com.cl1ppz12.projectzerolimit.item.custom;

import com.cl1ppz12.projectzerolimit.sound.ModSounds;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class PurpleGem extends Item {
    public PurpleGem(Item.Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.project_zero_limit.purple_gem.tooltip_1"));
            tooltip.add(Text.translatable("item.project_zero_limit.purple_gem.tooltip_2"));
            tooltip.add(Text.translatable("item.project_zero_limit.purple_gem.tooltip_3"));
        } else {
            tooltip.add(Text.translatable("item.project_zero_limit.tooltip_press_shift").formatted(Formatting.YELLOW));
        }
    }
}