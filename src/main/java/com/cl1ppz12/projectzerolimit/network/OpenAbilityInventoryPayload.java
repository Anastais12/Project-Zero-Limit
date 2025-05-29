package com.cl1ppz12.projectzerolimit.network;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

// This record represents the payload for opening the ability inventory.
// It doesn't need any data for a simple open request.
public record OpenAbilityInventoryPayload() implements CustomPayload {
    public static final CustomPayload.Id<OpenAbilityInventoryPayload> ID =
            new CustomPayload.Id<>(Identifier.of(ProjectZeroLimit.MOD_ID, "open_ability_inventory"));

    // PacketCodec to serialize/deserialize the payload.
    // Since this payload has no data, the encode/decode methods are empty.
    public static final PacketCodec<PacketByteBuf, OpenAbilityInventoryPayload> CODEC =
            PacketCodec.of((value, buf) -> {
                // No data to encode
            }, buf -> {
                // No data to decode
                return new OpenAbilityInventoryPayload();
            });

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}