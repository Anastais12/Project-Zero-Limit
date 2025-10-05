package com.cl1ppz12.projectzerolimit.gui;

import com.cl1ppz12.projectzerolimit.ability.Ability;
import com.cl1ppz12.projectzerolimit.ability.AbilityRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;

public class AbilityInventory {
    private static final int MAX_SLOTS = 9;
    private final AbilitySlot[] slots;
    private final PlayerEntity player;

    public AbilityInventory(PlayerEntity player) {
        this.player = player;
        this.slots = new AbilitySlot[MAX_SLOTS];
        for (int i = 0; i < MAX_SLOTS; i++) {
            slots[i] = new AbilitySlot();
        }
    }

    public boolean setAbility(int slot, Identifier abilityId, int level) {
        if (slot < 0 || slot >= MAX_SLOTS) return false;

        Ability ability = AbilityRegistry.get(abilityId);
        if (ability == null) return false;

        slots[slot].setAbility(ability, level);
        return true;
    }

    public AbilitySlot getSlot(int index) {
        if (index < 0 || index >= MAX_SLOTS) return null;
        return slots[index];
    }

    public void useAbility(int slot) {
        if (slot < 0 || slot >= MAX_SLOTS) return;

        AbilitySlot abilitySlot = slots[slot];
        if (abilitySlot.hasAbility() && abilitySlot.canUse(player)) {
            abilitySlot.getAbility().use(player, abilitySlot.getLevel());
            abilitySlot.setCooldown(abilitySlot.getAbility().getCooldown());
        }
    }

    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList slotsNbt = new NbtList();
        for (int i = 0; i < MAX_SLOTS; i++) {
            NbtCompound slotNbt = new NbtCompound();
            slots[i].writeNbt(slotNbt);
            slotsNbt.add(slotNbt);
        }
        nbt.put("AbilitySlots", slotsNbt);
        return nbt;
    }

    public void readNbt(NbtCompound nbt) {
        if (nbt.contains("AbilitySlots")) {
            NbtList slotsNbt = nbt.getList("AbilitySlots", 10);
            for (int i = 0; i < Math.min(slotsNbt.size(), MAX_SLOTS); i++) {
                slots[i].readNbt(slotsNbt.getCompound(i));
            }
        }
    }

    public static class AbilitySlot {
        private Ability ability;
        private int level;
        private int cooldown;

        public boolean hasAbility() {
            return ability != null;
        }

        public Ability getAbility() {
            return ability;
        }

        public int getLevel() {
            return level;
        }

        public int getCooldown() {
            return cooldown;
        }

        public void setCooldown(int cooldown) {
            this.cooldown = cooldown;
        }

        public boolean canUse(PlayerEntity player) {
            return hasAbility() && cooldown <= 0 && ability.canUse(player, level);
        }

        public void setAbility(Ability ability, int level) {
            this.ability = ability;
            this.level = level;
            this.cooldown = 0;
        }

        public void tick() {
            if (cooldown > 0) {
                cooldown--;
            }
        }

        public void writeNbt(NbtCompound nbt) {
            if (ability != null) {
                nbt.putString("AbilityId", ability.getId().toString());
                nbt.putInt("Level", level);
                nbt.putInt("Cooldown", cooldown);
            }
        }

        public void readNbt(NbtCompound nbt) {
            if (nbt.contains("AbilityId")) {
                Identifier abilityId = Identifier.of(nbt.getString("AbilityId"));
                this.ability = AbilityRegistry.get(abilityId);
                this.level = nbt.getInt("Level");
                this.cooldown = nbt.getInt("Cooldown");
            }
        }
    }
}