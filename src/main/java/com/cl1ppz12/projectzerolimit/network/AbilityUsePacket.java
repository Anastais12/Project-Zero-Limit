package com.cl1ppz12.projectzerolimit.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class AbilityUsePacket {
    private final int slotIndex;

    public AbilityUsePacket(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    public AbilityUsePacket(PacketByteBuf buf) {
        this.slotIndex = buf.readInt();
    }

    public void write(PacketByteBuf buf) {
        buf.writeInt(slotIndex);
    }

    public void handle(ServerPlayerEntity player) {
        if (player != null) {
            // Handle ability use on server
            var abilityData = com.example.abilitiesmod.system.ability.PlayerAbilityData.get(player);
            if (abilityData != null) {
                abilityData.getInventory().useAbility(slotIndex);
            }
        }
    }
}