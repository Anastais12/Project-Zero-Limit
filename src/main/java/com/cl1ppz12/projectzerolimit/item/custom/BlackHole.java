package com.cl1ppz12.projectzerolimit.item.custom;

import com.cl1ppz12.projectzerolimit.entity.custom.BlackHoleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BlackHole extends Item {

    public BlackHole(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.project_zero_limit.black_hole.tooltip_1"));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient) {
            BlockPos spawn = player.getBlockPos().up();
            BlackHoleEntity bh = new BlackHoleEntity(world, player, player.getRotationVec(1.0F));
            bh.setPosition(spawn.getX() + 0.5, spawn.getY(), spawn.getZ() + 0.5);
            world.spawnEntity(bh);
        }

        if (!player.isCreative()) stack.decrement(1);

        player.getItemCooldownManager().set(this, 400);
        player.swingHand(hand);
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);

        return TypedActionResult.success(stack, world.isClient());
    }
}