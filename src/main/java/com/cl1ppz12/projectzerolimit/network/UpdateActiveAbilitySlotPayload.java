package com.cl1ppz12.projectzerolimit.network;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs; // <--- THIS MUST BE THIS EXACT IMPORT
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static com.cl1ppz12.projectzerolimit.network.OpenAbilityInventoryPayload.ID;

/**
 * A custom payload (packet) sent from the client to the server to update an active ability slot.
 */
public record UpdateActiveAbilitySlotPayload(int slotIndex, ItemStack stack) implements CustomPayload {


    public static final CustomPayload.Id<UpdateActiveAbilitySlotPayload> ID =
            new CustomPayload.Id<>(Identifier.of(ProjectZeroLimit.MOD_ID, "update_active_ability_slot"));
    /*
        public static final PacketCodec<PacketByteBuf, UpdateActiveAbilitySlotPayload> CODEC =
                PacketCodec.of((value, buf) -> {
                    buf.writeInt(value.slotIndex);
                    PacketCodecs.RAW_ITEM_STACK.encode(buf, value.stack);
                }, buf -> {
                    int slotIndex = buf.readInt();
                    ItemStack stack = PacketCodecs.RAW_ITEM_STACK.decode(buf);
                    return new UpdateActiveAbilitySlotPayload(slotIndex, stack);
                });
    */
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}