package com.cl1ppz12.projectzerolimit.client;

import com.cl1ppz12.projectzerolimit.ability.Ability;
import com.cl1ppz12.projectzerolimit.ability.AbilityRegistry;
import com.cl1ppz12.projectzerolimit.ability.AbilityUUIDRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.UUID;

public enum ClientAbilityCache {
    ;

    private static final Ability[] bound = new Ability[5];

    public static void updateSlot(int slot, UUID uuid) {
        bound[slot] = uuid == null ? null : AbilityUUIDRegistry.get(uuid);
    }

    public static Ability getBound(int slot) {
        return bound[slot];
    }
}