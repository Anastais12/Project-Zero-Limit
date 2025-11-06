package com.cl1ppz12.projectzerolimit.server.aura;

import com.cl1ppz12.projectzerolimit.effect.ModEffects;
import com.cl1ppz12.projectzerolimit.network.payload.s2c.AuraSyncPayload;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;

public final class AuraManager {

    private static final Map<ServerPlayerEntity, Integer> DATA = new HashMap<>();
    private static final int MAX = 10_000;
    private static final int PER_TICK = 40;

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity p : server.getPlayerManager().getPlayerList()) {
                if (!p.hasStatusEffect(ModEffects.COMBAT_MODE)) continue;

                int old = get(p);
                int neu = Math.min(MAX, old + PER_TICK);
                if (neu != old) DATA.put(p, neu);
            }
        });
    }

    public static int get(ServerPlayerEntity p) {
        return DATA.getOrDefault(p, MAX);
    }

    public static void consume(ServerPlayerEntity p, int amount) {
        int neu = Math.max(0, get(p) - amount);
        DATA.put(p, neu);
        ServerPlayNetworking.send(p, new AuraSyncPayload(neu));
    }

    public static boolean canAfford(ServerPlayerEntity p, int amount) {
        return get(p) >= amount;
    }
}