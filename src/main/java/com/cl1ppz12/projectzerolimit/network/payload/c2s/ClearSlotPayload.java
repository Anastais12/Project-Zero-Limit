package com.cl1ppz12.projectzerolimit.network.payload.c2s;

import static com.cl1ppz12.projectzerolimit.ProjectZeroLimit.MOD_ID;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ClearSlotPayload(int slot) implements CustomPayload {
    public static final Id<ClearSlotPayload> ID = new Id<>(Identifier.of(MOD_ID, "clear_slot"));
    public static final PacketCodec<PacketByteBuf, ClearSlotPayload> CODEC = PacketCodec.of(ClearSlotPayload::encode, ClearSlotPayload::decode);

    private static void encode(ClearSlotPayload p, PacketByteBuf buf) {
        buf.writeByte(p.slot);
    }
    private static ClearSlotPayload decode(PacketByteBuf buf) {
        return new ClearSlotPayload(buf.readUnsignedByte());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}