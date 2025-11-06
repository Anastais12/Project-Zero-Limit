package com.cl1ppz12.projectzerolimit.server;

import com.cl1ppz12.projectzerolimit.ability.Ability;
import com.cl1ppz12.projectzerolimit.ability.AbilityRegistry;
import com.cl1ppz12.projectzerolimit.cca.ability.PlayerAbilityComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.*;

/** Persistent per-player data: owned set + 5 bound slots (-1 = empty) */
public class PlayerAbilityStore {

    private static final String OWNED_KEY  = "PZLOwnedAbilities";
    private static final String BOUND_KEY  = "PZLBoundSlots";
    private static final String SELECTED_CAT = "PZLSelectedCat";

    public static Set<Identifier> getOwned(ServerPlayerEntity player) {
        return PlayerAbilityComponent.KEY.get(player).getOwned();
    }

    private static void saveOwned(ServerPlayerEntity player, Set<Identifier> set) {
        NbtCompound tag = player.getAttached(PlayerDataAttachments.ABILITIES);
        if (tag == null) tag = new NbtCompound();
        NbtList list = new NbtList();
        set.forEach(id -> list.add(NbtString.of(id.toString())));
        tag.put(OWNED_KEY, list);
        player.setAttached(PlayerDataAttachments.ABILITIES, tag);
    }

    public static int[] getBoundSlots(ServerPlayerEntity player) {
        NbtCompound tag = player.getAttached(PlayerDataAttachments.ABILITIES);
        if (tag == null) tag = new NbtCompound();
        if (!tag.contains(BOUND_KEY, 11)) return new int[]{-1,-1,-1,-1,-1};
        return tag.getIntArray(BOUND_KEY);
    }

    public static void setBoundSlot(ServerPlayerEntity player, int slot, Identifier id) {
        PlayerAbilityComponent comp = PlayerAbilityComponent.KEY.get(player);
        int index = id == null ? -1 : AbilityRegistry.INDEXED.indexOf(AbilityRegistry.get(id));
        comp.setBoundSlot(slot, index);
    }

    public static void grant(ServerPlayerEntity player, Identifier id) {
        Set<Identifier> set = new HashSet<>(getOwned(player));
        if (set.add(id)) saveOwned(player, set);
    }


    public static void setSelectedCategory(ServerPlayerEntity player, Ability.AbilityType type) {
        NbtCompound tag = player.getAttached(PlayerDataAttachments.ABILITIES);
        if (tag == null) tag = new NbtCompound();
        tag.putString(SELECTED_CAT, type.name());
        player.setAttached(PlayerDataAttachments.ABILITIES, tag);
    }

    public static Ability.AbilityType getSelectedCategory(ServerPlayerEntity player) {
        NbtCompound tag = player.getAttached(PlayerDataAttachments.ABILITIES);
        if (tag == null) return Ability.AbilityType.ONI_ART; // default
        return Ability.AbilityType.valueOf(tag.getString(SELECTED_CAT));
    }
}