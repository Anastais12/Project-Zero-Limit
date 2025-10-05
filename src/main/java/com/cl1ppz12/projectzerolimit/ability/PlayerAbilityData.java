package com.cl1ppz12.projectzerolimit.ability;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.PersistentState;

import java.util.UUID;

/**
 * Per-player ability data (cooldowns, slotted abilities, etc.).
 * Implements PersistentState so it can be attached to a save file if you choose
 * to store it globally instead of per-player NBT.
 */
public class PlayerAbilityData extends PersistentState {

    private final AbilityInventory inventory;
    private final UUID playerUuid;

    public PlayerAbilityData(UUID playerUuid) {
        this.playerUuid = playerUuid;
        this.inventory = new AbilityInventory(null); // inventory manages its own player ref via setter
    }

    /* ---------- Access ---------- */

    public AbilityInventory getInventory() {
        return inventory;
    }

    /* ---------- Tick ---------- */

    public void tick() {
        inventory.tick();
    }

    /* ---------- NBT ---------- */

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        return inventory.writeNbt(nbt);
    }

    public void readNbt(NbtCompound nbt) {
        inventory.readNbt(nbt);
    }

    /* ---------- Static helpers ---------- */

    private static final String KEY = "project_zero_limit.ability_data";

    /* Get-or-create for a player (server side) */
    public static PlayerAbilityData get(ServerPlayerEntity player) {
        NbtCompound playerNbt = player.getPersistentData();
        if (!playerNbt.contains(KEY)) {
            playerNbt.put(KEY, new NbtCompound());
        }
        NbtCompound dataTag = playerNbt.getCompound(KEY);

        PlayerAbilityData data = new PlayerAbilityData(player.getUuid());
        data.readNbt(dataTag);
        return data;
    }

    /* Save back to player NBT (call whenever you mutate data) */
    public static void save(ServerPlayerEntity player, PlayerAbilityData data) {
        NbtCompound playerNbt = player.getPersistentData();
        playerNbt.put(KEY, data.writeNbt(new NbtCompound()));
    }
}