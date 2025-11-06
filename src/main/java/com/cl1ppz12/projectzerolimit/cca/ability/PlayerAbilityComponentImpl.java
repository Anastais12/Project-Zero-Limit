package com.cl1ppz12.projectzerolimit.cca.ability;

import com.cl1ppz12.projectzerolimit.ability.Ability;
import com.cl1ppz12.projectzerolimit.ability.AbilityRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Set;


public final class PlayerAbilityComponentImpl implements PlayerAbilityComponent {

    private final PlayerEntity player;

    private Set<Identifier> owned = new HashSet<>();
    private int[] bound = {-1, -1, -1, -1, -1};
    private Ability.AbilityType selected = Ability.AbilityType.ONI_ART;

    public PlayerAbilityComponentImpl(PlayerEntity player) {
        this.player = player;
    }

    /* -------------------- OWNED -------------------- */
    @Override
    public Set<Identifier> getOwned() {
        return Set.copyOf(owned);
    }

    @Override
    public void addOwned(Identifier id) {
        if (owned.add(id)) sync();
    }

    @Override
    public void setOwned(Set<Identifier> ids) {
        owned = new HashSet<>(ids);
        sync();
    }

    /* -------------------- BOUND -------------------- */
    @Override
    public int[] getBoundSlots() {
        return bound.clone();
    }

    @Override
    public void setBoundSlot(int slot, int index) {
        bound[slot] = index;
        sync();
    }

    /* -------------------- CATEGORY -------------------- */
    @Override
    public Ability.AbilityType getSelectedCategory() {
        return selected;
    }

    @Override
    public void setSelectedCategory(Ability.AbilityType type) {
        selected = type;
        sync();
    }

    @Override
    public boolean owns(Ability ability) {
        return PlayerAbilityComponent.super.owns(ability);
    }

    /* -------------------- CCA NBT -------------------- */

    @Override
    public void sync() {
        PlayerAbilityComponent.KEY.sync(player);
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        /* ------- owned ------- */
        owned.clear();
        NbtList list = nbtCompound.getList("Owned", 8);
        for (int i = 0; i < list.size(); i++) {
            owned.add(Identifier.tryParse(list.getString(i)));
        }

        /* ------- bound ------- */
        bound = nbtCompound.getIntArray("Bound");
        if (bound.length != 5) bound = new int[]{-1, -1, -1, -1, -1};

        /* ------- category – DEFENSIVE ------- */
        String catName = nbtCompound.getString("Category");
        try {
            selected = Ability.AbilityType.valueOf(catName);
        } catch (IllegalArgumentException ex) {
            selected = Ability.AbilityType.ONI_ART;
            // optional: log once so you see it in console
        }
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        // owned
        NbtList list = new NbtList();
        owned.forEach(id -> list.add(NbtString.of(id.toString())));
        nbtCompound.put("Owned", list);
        // bound
        nbtCompound.putIntArray("Bound", bound);
        // category
        nbtCompound.putString("Category", selected.name());
    }
}