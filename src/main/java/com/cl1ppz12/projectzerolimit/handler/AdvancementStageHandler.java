package com.cl1ppz12.projectzerolimit.handler;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


public class AdvancementStageHandler {

    //doesnt work
    public static void checkAdvancementAndSendMessage(ServerPlayerEntity player, Advancement advancement) {
        if (player.getAdvancementTracker().getProgress(new AdvancementEntry(Identifier.of("project_zero_limit:root"), advancement)).isDone()) {
            player.sendMessage(Text.literal("You have completed the advancement!"), false);
        }
    }
}
