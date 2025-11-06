package com.cl1ppz12.projectzerolimit.server;

import com.cl1ppz12.projectzerolimit.network.payload.s2c.AbilityListPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public final class PlayerJoinListener {

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            //PlayerAbilityStore.giveStarterKit(player);
            AbilityListPayload.sendToPlayer(player);
        });
    }
}