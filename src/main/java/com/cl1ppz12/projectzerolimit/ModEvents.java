package com.cl1ppz12.projectzerolimit;

import com.cl1ppz12.projectzerolimit.effect.ModEffects;
import com.cl1ppz12.projectzerolimit.shader.InterDimensionalShader;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;

public class ModEvents {

    // Remembers last state so we only print on change
    private static boolean hadEffectLastTick = false;

    public static void registerEvents() {
        checkforInterDimensionalTravel();
    }

    public static void checkforInterDimensionalTravel() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            boolean hasNow = client.player.hasStatusEffect(ModEffects.INTERDIMENSIONAL_TRAVEL);

            if (hasNow != hadEffectLastTick) {
                if (hasNow) {
                    client.player.sendMessage(Text.literal("You HAVE the effect"), false);
                    InterDimensionalShader.enable();
                } else {
                    client.player.sendMessage(Text.literal("You DO NOT have the effect"), false);
                    InterDimensionalShader.disable();
                }
                hadEffectLastTick = hasNow;
            }
        });
    }
}