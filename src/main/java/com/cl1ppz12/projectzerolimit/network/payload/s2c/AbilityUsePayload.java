package com.cl1ppz12.projectzerolimit.network.payload.s2c;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record AbilityUsePayload(Identifier abilityId) implements CustomPayload {

    public static final Id<AbilityUsePayload> ID =
            new Id<>(Identifier.of(ProjectZeroLimit.MOD_ID, "ability_use"));

    public static final PacketCodec<PacketByteBuf, AbilityUsePayload> CODEC =
            PacketCodec.of(AbilityUsePayload::encode, AbilityUsePayload::decode);

    private static void encode(AbilityUsePayload payload, PacketByteBuf buf) {
        buf.writeIdentifier(payload.abilityId);
    }

    private static AbilityUsePayload decode(PacketByteBuf buf) {
        return new AbilityUsePayload(buf.readIdentifier());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
