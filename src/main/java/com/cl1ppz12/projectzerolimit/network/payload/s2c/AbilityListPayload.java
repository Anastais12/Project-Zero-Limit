package com.cl1ppz12.projectzerolimit.network.payload.s2c;

//S2C
import static com.cl1ppz12.projectzerolimit.ProjectZeroLimit.MOD_ID;

import com.cl1ppz12.projectzerolimit.cca.ability.PlayerAbilityComponent;
import com.cl1ppz12.projectzerolimit.server.PlayerAbilityStore;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record AbilityListPayload(List<Identifier> owned, int[] boundSlots) implements CustomPayload {

    public static final Id<AbilityListPayload> ID =
            new Id<>(Identifier.of(MOD_ID, "ability_list"));
    public static final PacketCodec<PacketByteBuf, AbilityListPayload> CODEC =
            PacketCodec.of(AbilityListPayload::encode, AbilityListPayload::decode);

    private static void encode(AbilityListPayload p, PacketByteBuf buf) {
        buf.writeCollection(p.owned, PacketByteBuf::writeIdentifier);
        buf.writeIntArray(p.boundSlots);        // length 5
    }
    private static AbilityListPayload decode(PacketByteBuf buf) {
        List<Identifier> owned = buf.readList(PacketByteBuf::readIdentifier);
        int[] bound            = buf.readIntArray(5);
        return new AbilityListPayload(owned, bound);
    }

    @Override public Id<? extends CustomPayload> getId() { return ID; }

    /* helper – build from server data */
    public static AbilityListPayload create(ServerPlayerEntity player) {
        PlayerAbilityComponent comp = PlayerAbilityComponent.KEY.get(player);
        List<Identifier> owned = new ArrayList<>(comp.getOwned());
        int[] bound = comp.getBoundSlots();
        return new AbilityListPayload(owned, bound);
    }

    public static void sendToPlayer(ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, create(player));
    }
}
