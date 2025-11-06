package com.cl1ppz12.projectzerolimit.client;

import com.cl1ppz12.projectzerolimit.ability.Ability;
import com.cl1ppz12.projectzerolimit.ability.AbilityRegistry;
import com.cl1ppz12.projectzerolimit.network.payload.s2c.AbilityListPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public final class AbilityDebugPrinter {

    public static void init() {
        /* we piggy-back on the SAME payload you already listen to */
        ClientPlayNetworking.registerGlobalReceiver(
                AbilityListPayload.ID,
                (payload, ctx) -> ctx.client().execute(() -> {
                    MinecraftClient mc = MinecraftClient.getInstance();
                    if (mc.player == null) return;

                    /* ---- print header ---- */
                    mc.player.sendMessage(Text.literal("§6[Ability-Debug] §eyou own:"), false);

                    /* ---- walk the 5 bound slots ---- */
                    int[] boundSlots = payload.boundSlots();
                    for (int slot = 0; slot < 5; slot++) {
                        int idx = boundSlots[slot];
                        if (idx == -1) {
                            mc.player.sendMessage(
                                    Text.literal("  §7slot " + slot + " : §8<empty>"), false);
                            continue;
                        }
                        Ability ab = AbilityRegistry.get(idx);
                        if (ab == null) continue;

                        mc.player.sendMessage(
                                Text.literal("  §7slot " + slot + " : §a" + ab.getID() +
                                        " §ficon=§b" + ab.getIconTexture()), false);
                    }
                }));
    }
}