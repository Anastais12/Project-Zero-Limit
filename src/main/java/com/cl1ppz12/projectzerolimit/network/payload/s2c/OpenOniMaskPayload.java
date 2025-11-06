package com.cl1ppz12.projectzerolimit.network.payload.s2c;

import com.cl1ppz12.projectzerolimit.client.gui.mask.OniMaskInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

public record OpenOniMaskPayload(NbtCompound inventoryNbt) implements CustomPayload {

    public static final Id<OpenOniMaskPayload> ID =
            new Id<>(Identifier.of("project_zero_limit", "open_oni_mask"));

    public static final PacketCodec<PacketByteBuf, OpenOniMaskPayload> CODEC =
            PacketCodec.of(OpenOniMaskPayload::encode, OpenOniMaskPayload::decode);


    private static void encode(OpenOniMaskPayload p, PacketByteBuf buf) {
        buf.writeNbt(p.inventoryNbt);
    }
    private static OpenOniMaskPayload decode(PacketByteBuf buf) {
        return new OpenOniMaskPayload(buf.readNbt());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    /* factory helper */
    public static OpenOniMaskPayload fromInventory(OniMaskInventory inv, RegistryWrapper.WrapperLookup lookup) {
        NbtCompound tag = inv.writeToNbt(new NbtCompound(), lookup); // void → tag is filled
        return new OpenOniMaskPayload(tag);
    }
}
