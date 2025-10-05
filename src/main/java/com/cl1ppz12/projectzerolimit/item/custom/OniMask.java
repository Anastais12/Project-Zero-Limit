package com.cl1ppz12.projectzerolimit.item.custom;

import com.cl1ppz12.projectzerolimit.sound.ModSounds;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class OniMask extends Item {

    public OniMask(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("item.project_zero_limit.oni_mask.tooltip_1"));
            tooltip.add(Text.translatable("item.project_zero_limit.oni_mask.tooltip_2"));
            tooltip.add(Text.translatable("item.project_zero_limit.oni_mask.tooltip_3"));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack held = user.getStackInHand(hand);
        ItemStack head = user.getEquippedStack(EquipmentSlot.HEAD);

        if (!head.isEmpty()) {
            user.getInventory().offerOrDrop(head.copy());
        }

        user.equipStack(EquipmentSlot.HEAD, held.split(1));
        user.playSound(ModSounds.ONI_MASK_EQUIP, 1.0F, 1.0F);

        return TypedActionResult.success(user.getStackInHand(hand));
    }

}