// CategorySwitchPayload.java
package com.cl1ppz12.projectzerolimit.network.payload.c2s;

import com.cl1ppz12.projectzerolimit.ability.Ability;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static com.cl1ppz12.projectzerolimit.ProjectZeroLimit.MOD_ID;

public record CategorySwitchPayload(Ability.AbilityType category) implements CustomPayload {

    public static final Id<CategorySwitchPayload> ID =
            new Id<>(Identifier.of(MOD_ID, "category_switch"));

    public static final PacketCodec<PacketByteBuf, CategorySwitchPayload> CODEC =
            PacketCodec.of(CategorySwitchPayload::encode, CategorySwitchPayload::decode);

    /*  write enum → network  */
    private static void encode(CategorySwitchPayload p, PacketByteBuf buf) {
        buf.writeEnumConstant(p.category);
    }

    private static CategorySwitchPayload decode(PacketByteBuf buf) {
        return new CategorySwitchPayload(buf.readEnumConstant(Ability.AbilityType.class));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}