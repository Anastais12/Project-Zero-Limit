package com.cl1ppz12.projectzerolimit.network;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public final class ModPackets {

    /* Channel IDs */
    public static final Identifier ABILITY_USE = id("ability_use");

    /* Server-side packet registration */
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(
                ABILITY_USE,
                (server, player, handler, buf, responseSender) -> {
                    AbilityUsePacket packet = new AbilityUsePacket(buf);
                    server.execute(() -> packet.handle(player));
                }
        );
    }

    /* Client-side packet registration (add more client handlers here if needed) */
    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        // Empty for now; extend if you add server→client packets later.
    }

    /* Helper to send an AbilityUsePacket to the server */
    @Environment(EnvType.CLIENT)
    public static void sendToServer(AbilityUsePacket packet) {
        var buf = PacketByteBufs.create();
        packet.write(buf);
        ClientPlayNetworking.send(ABILITY_USE, buf);
    }

    /* Convenience helper for creating Identifiers under our mod id */
    private static Identifier id(String path) {
        return Identifier.of(ProjectZeroLimit.MOD_ID, path);
    }
}