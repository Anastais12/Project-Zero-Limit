package com.cl1ppz12.projectzerolimit.network.payload.c2s;

//C2S
import static com.cl1ppz12.projectzerolimit.ProjectZeroLimit.MOD_ID;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record OpenAbilityInventoryPayload() implements CustomPayload {
    public static final Id<OpenAbilityInventoryPayload> ID =
            new Id<>(Identifier.of(MOD_ID, "open_ability_inv"));
    public static final PacketCodec<PacketByteBuf, OpenAbilityInventoryPayload> CODEC =
            PacketCodec.of((p, b) -> {}, b -> new OpenAbilityInventoryPayload());

    @Override public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
