package com.cl1ppz12.projectzerolimit.client.util;

import com.cl1ppz12.projectzerolimit.ability.Ability;
import com.cl1ppz12.projectzerolimit.ability.AbilityRegistry;
import com.cl1ppz12.projectzerolimit.client.hud.AbilityBarRender;
import com.cl1ppz12.projectzerolimit.network.payload.s2c.AbilityUsePayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

/**
 * Central place for LEFT / RIGHT click abilities while the bar is open.
 */
@Environment(EnvType.CLIENT)
public final class AbilityInputHandler {

    private static final MinecraftClient MC = MinecraftClient.getInstance();

    /* ---------- LEFT-CLICK SPAM ---------- */
    private int leftHoldTimer   = 0;   // ticks
    private int leftSecondCounter = 0; // seconds

    /* ---------- RIGHT-CLICK ---------- */
    private boolean wasRightDown = false;

    /* ---------- INIT ---------- */
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> new AbilityInputHandler().tick());
    }

    /* ---------- TICK ---------- */
    private void tick() {
        if (MC.player == null || MC.currentScreen != null) return;
        if (!AbilityBarRender.isOpen()) return;   // only while bar is up

        /* LEFT HOLD – spam numbers every second */
        boolean leftDown = MC.options.attackKey.isPressed();
        if (leftDown) {
            leftHoldTimer++;
            if (leftHoldTimer % 20 == 0) {          // 1 second
                leftSecondCounter++;
                MC.player.sendMessage(
                        Text.literal(String.valueOf(leftSecondCounter)), false);
            }
        } else {
            leftHoldTimer   = 0;
            leftSecondCounter = 0;
        }

        /* RIGHT TAP – use ability */
        boolean rightDown = MC.options.useKey.isPressed();
        if (rightDown && !wasRightDown) {           // rising edge
            Ability ability = AbilityRegistry.get(AbilityBarRender.getSelectedIndex());
            if (ability != null)
                ClientPlayNetworking.send(new AbilityUsePayload(ability.id()));
        }
        wasRightDown = rightDown;
    }
}