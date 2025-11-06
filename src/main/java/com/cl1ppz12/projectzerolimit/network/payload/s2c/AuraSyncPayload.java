package com.cl1ppz12.projectzerolimit.network.payload.s2c;

import static com.cl1ppz12.projectzerolimit.ProjectZeroLimit.MOD_ID;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record AuraSyncPayload(int aura) implements CustomPayload {
    public static final Id<AuraSyncPayload> ID =
            new Id<>(Identifier.of(MOD_ID, "aura_sync"));
    public static final PacketCodec<PacketByteBuf, AuraSyncPayload> CODEC =
            PacketCodec.of(AuraSyncPayload::encode, AuraSyncPayload::decode);

    private static void encode(AuraSyncPayload p, PacketByteBuf b) { b.writeVarInt(p.aura); }
    private static AuraSyncPayload decode(PacketByteBuf b) { return new AuraSyncPayload(b.readVarInt()); }
    @Override public Id<? extends CustomPayload> getId() { return ID; }
}