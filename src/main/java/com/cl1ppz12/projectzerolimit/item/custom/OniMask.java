package com.cl1ppz12.projectzerolimit.item.custom;

import com.cl1ppz12.projectzerolimit.client.gui.mask.OniMaskInventory;
import com.cl1ppz12.projectzerolimit.effect.ModEffects;
import com.cl1ppz12.projectzerolimit.item.ModItems;
import com.cl1ppz12.projectzerolimit.network.ModNetworking;
import com.cl1ppz12.projectzerolimit.sound.ModSounds;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
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
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.project_zero_limit.oni_mask.tooltip_1"));
            tooltip.add(Text.translatable("item.project_zero_limit.oni_mask.tooltip_2"));
            tooltip.add(Text.translatable("item.project_zero_limit.oni_mask.tooltip_3"));
        } else {
            tooltip.add(Text.translatable("item.project_zero_limit.tooltip_press_shift").formatted(Formatting.YELLOW));
        }

        if (Screen.hasControlDown()) {
            tooltip.add(Text.translatable("item.project_zero_limit.tooltip_line"));
            tooltip.add(Text.translatable("item.project_zero_limit.oni_mask.ability_1_tooltip"));
        } else {
            tooltip.add(Text.translatable("item.project_zero_limit.tooltip_press_ctrl").formatted(Formatting.YELLOW));
        }
    }

    //TODO: Make the inventory open properly
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack held = user.getStackInHand(hand);

        if (user.isSneaking() && !world.isClient && user instanceof ServerPlayerEntity serverPlayer) {
            OniMaskInventory inv = new OniMaskInventory();
            NbtCompound tag = new NbtCompound();
            held.encode(serverPlayer.getRegistryManager(), tag);
            inv.readFromNbt(tag, serverPlayer.getRegistryManager());
            ModNetworking.openMaskScreen(serverPlayer, inv);
            return TypedActionResult.success(held);
        }

        ItemStack head = user.getEquippedStack(EquipmentSlot.HEAD);
        if (!head.isEmpty()) user.getInventory().offerOrDrop(head.copy());

        user.equipStack(EquipmentSlot.HEAD, held.split(1));
        user.playSound(ModSounds.ONI_MASK_EQUIP, 1.0F, 1.0F);

        return TypedActionResult.success(user.getStackInHand(hand));
    }



}