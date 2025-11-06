package com.cl1ppz12.projectzerolimit.ability;

import net.minecraft.util.Identifier;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class AbilityUUIDRegistry {
    private static final Map<UUID, Ability> BY_UUID = new ConcurrentHashMap<>();
    private static final Map<Identifier, UUID> BY_ID = new ConcurrentHashMap<>();

    public static void register(Ability ability) {
        UUID uuid = UUID.nameUUIDFromBytes(ability.id().toString().getBytes());
        BY_UUID.put(uuid, ability);
        BY_ID.put(ability.id(), uuid);
    }

    public static Ability get(UUID uuid) {
        return BY_UUID.get(uuid);
    }

    public static UUID getUuid(Ability ability) {
        return BY_ID.get(ability.id());
    }

    public static void bootstrap() {
        AbilityRegistry.INDEXED.forEach(AbilityUUIDRegistry::register);
    }
}