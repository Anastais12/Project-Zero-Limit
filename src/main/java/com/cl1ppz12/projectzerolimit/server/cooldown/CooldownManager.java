package com.cl1ppz12.projectzerolimit.server.cooldown;

import com.cl1ppz12.projectzerolimit.ability.Ability;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public final class CooldownManager {

    private static final Map<ServerPlayerEntity, Map<Identifier, Integer>> MAP = new HashMap<>();

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (var entry : MAP.entrySet()) {
                entry.getValue().entrySet().removeIf(e -> {
                    e.setValue(e.getValue() - 1);
                    return e.getValue() <= 0;
                });
            }
        });
    }

    public static void put(ServerPlayerEntity p, Ability a, int ticks) {
        MAP.computeIfAbsent(p, k -> new HashMap<>()).put(a.id(), ticks);
    }

    public static boolean isOnCooldown(ServerPlayerEntity p, Ability a) {
        return MAP.getOrDefault(p, Map.of()).getOrDefault(a.id(), 0) > 0;
    }

    public static int getRemaining(ServerPlayerEntity p, Ability a) {
        return MAP.getOrDefault(p, Map.of()).getOrDefault(a.id(), 0);
    }
}