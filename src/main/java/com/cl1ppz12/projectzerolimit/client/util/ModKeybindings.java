package com.cl1ppz12.projectzerolimit.client.util;

import com.cl1ppz12.projectzerolimit.client.hud.AbilityBarRender;
import com.cl1ppz12.projectzerolimit.effect.ModEffects;
import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.lwjgl.glfw.GLFW;

public class ModKeybindings {

    public static KeyBinding keyZ;
    public static KeyBinding keyX;
    public static KeyBinding keyC;
    public static KeyBinding keyV;
    public static KeyBinding keyG;
    public static KeyBinding keyR;
    public static KeyBinding toggleKey;
    public static KeyBinding OPEN_CUSTOM_INVENTORY;

    private static boolean open = false;

    public static void registerModKeybindings() {
        ProjectZeroLimit.LOGGER.info("Registering mod keybindings for " + ProjectZeroLimit.MOD_ID);

        keyZ = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.z", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, "category.project_zero_limit.abilities"));
        keyX = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.x", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X, "category.project_zero_limit.abilities"));
        keyC = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.c", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "category.project_zero_limit.abilities"));
        keyV = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.v", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "category.project_zero_limit.abilities"));
        keyG = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.g", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.project_zero_limit.abilities"));
        keyR = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.r", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.project_zero_limit.abilities"));
        toggleKey = new KeyBinding("key.project_zero_limit.ability_toggle", GLFW.GLFW_KEY_LEFT_ALT, "category.project_zero_limit.abilities");
        OPEN_CUSTOM_INVENTORY = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.i", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_I, "category.project_zero_limit.abilities"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
                while (keyZ.wasPressed()) {
                    if (client.player != null) {
                        if (client.player.hasStatusEffect(ModEffects.COMBAT_MODE)) {
                            //client.player.sendMessage(Text.literal("Z key pressed!"), false);
                        }
                    }
                }

                while (keyX.wasPressed()) {
                    if (client.player != null) {
                        if (client.player.hasStatusEffect(ModEffects.COMBAT_MODE)) {
                            //client.player.sendMessage(Text.literal("X key pressed!"), false);
                        }
                    }
                }

                while (keyC.wasPressed()) {
                    if (client.player != null) {
                        if (client.player.hasStatusEffect(ModEffects.COMBAT_MODE)) {
                            //client.player.sendMessage(Text.literal("C key pressed!"), false);
                        }
                    }
                }

                while (keyV.wasPressed()) {
                    if (client.player != null) {
                        if (client.player.hasStatusEffect(ModEffects.COMBAT_MODE)) {
                            //client.player.sendMessage(Text.literal("V key pressed!"), false);
                        }
                    }
                }

                while (keyG.wasPressed()) {
                    if (client.player != null) {
                        if (client.player.hasStatusEffect(ModEffects.COMBAT_MODE)) {
                            //client.player.sendMessage(Text.literal("V key pressed!"), false);
                        }
                    }
                }

                while (keyR.wasPressed()) {
                    if (client.player != null) {
                        if (client.player.hasStatusEffect(ModEffects.COMBAT_MODE)) {
                            //client.player.sendMessage(Text.literal("R key pressed!"), false);
                        }
                    }

                }

            while (toggleKey.wasPressed()) {
                if (client.player != null) {
                    // Check if Combat Mode is currently active
                    boolean isCombatModeActive = client.player.hasStatusEffect(ModEffects.COMBAT_MODE);

                    if (isCombatModeActive) {
                        // Remove Combat Mode
                        client.player.removeStatusEffect(ModEffects.COMBAT_MODE);
                        // Tell the renderer to close the bar
                        AbilityBarRender.setOpen(false);
                    } else {
                        // Add Combat Mode
                        client.player.addStatusEffect(new StatusEffectInstance(
                                ModEffects.COMBAT_MODE,
                                StatusEffectInstance.INFINITE,
                                0,
                                false,
                                false,
                                true
                        ));
                        AbilityBarRender.setOpen(true);
                    }
                }
            }
        });


    }

}

