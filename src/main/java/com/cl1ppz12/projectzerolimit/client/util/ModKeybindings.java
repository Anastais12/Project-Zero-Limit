package com.cl1ppz12.projectzerolimit.client.util;

import com.cl1ppz12.projectzerolimit.effect.ModEffects;
import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.cl1ppz12.projectzerolimit.item.ModItems;
import com.cl1ppz12.projectzerolimit.screen.custom.MaskScreenHandler;
import com.cl1ppz12.projectzerolimit.screen.custom.OniMaskScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
    public static KeyBinding OPEN_CUSTOM_INVENTORY;

    private static final String CATEGORY = "key.categories.abilitiesmod";

    public static final KeyBinding[] ABILITY_KEYS = new KeyBinding[9];

    public static void registerModKeybindings() {
        ProjectZeroLimit.LOGGER.info("Registering mod keybindings for " + ProjectZeroLimit.MOD_ID);

        keyZ = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.z", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, "category.project_zero_limit.abilities"));
        keyX = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.x", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X, "category.project_zero_limit.abilities"));
        keyC = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.c", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "category.project_zero_limit.abilities"));
        keyV = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.v", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "category.project_zero_limit.abilities"));
        keyG = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.g", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.project_zero_limit.abilities"));
        keyR = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.r", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.project_zero_limit.abilities"));
        OPEN_CUSTOM_INVENTORY = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.i", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_I, "category.project_zero_limit.abilities"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
                while (keyZ.wasPressed()) {
                    if (client.player != null) {
                        if (client.player.hasStatusEffect(ModEffects.COMBAT_MODE)) {
                            client.player.sendMessage(Text.literal("Z key pressed!"), false);
                        }
                    }
                }

                while (keyX.wasPressed()) {
                    if (client.player != null) {
                        if (client.player.hasStatusEffect(ModEffects.COMBAT_MODE)) {
                            client.player.sendMessage(Text.literal("X key pressed!"), false);
                        }
                    }
                }

                while (keyC.wasPressed()) {
                    if (client.player != null) {
                        if (client.player.hasStatusEffect(ModEffects.COMBAT_MODE)) {
                            client.player.sendMessage(Text.literal("C key pressed!"), false);
                        }
                    }
                }

                while (keyV.wasPressed()) {
                    if (client.player != null) {
                        if (client.player.hasStatusEffect(ModEffects.COMBAT_MODE)) {
                            client.player.sendMessage(Text.literal("V key pressed!"), false);
                        }
                    }
                }

                while (keyG.wasPressed()) {
                    if (client.player != null) {
                        ItemStack head = client.player.getInventory().getArmorStack(3);
                        if (head.isOf(ModItems.ONI_MASK)) {
                            client.setScreen(new OniMaskScreen(new MaskScreenHandler(client.player.getInventory())));
                        }
                    }
                }

                while (keyR.wasPressed()) {
                    if (client.player != null) {
                        ClientWorld world = client.world;
                        PlayerEntity player = client.player;
                        Vec3d pos = player.getPos();

                        int numParticles = 30;
                        double radius = 1.5;
                        Random random = world.getRandom();

                        if (client.player.hasStatusEffect(ModEffects.COMBAT_MODE)) {
                            client.player.removeStatusEffect(ModEffects.COMBAT_MODE);
                            client.player.sendMessage(Text.literal("Combat Mode: ").append(Text.literal("DEACTIVATED!").formatted(Formatting.RED)), true);
                        } else {
                            client.player.addStatusEffect(new StatusEffectInstance(
                                    ModEffects.COMBAT_MODE,
                                    StatusEffectInstance.INFINITE,
                                    0,
                                    false,
                                    false,
                                    true
                            ));
                            client.player.sendMessage(Text.literal("Combat Mode: ").append(Text.literal("ACTIVATED!").formatted(Formatting.GREEN)), true);
                        }
                        for (int i = 0; i < numParticles; i++) {
                            double angle = Math.toRadians((double) i / numParticles * 360.0);
                            double offsetX = radius * Math.cos(angle) + (random.nextDouble() - 0.5) * 0.2;
                            double offsetZ = radius * Math.sin(angle) + (random.nextDouble() - 0.5) * 0.2;
                            double offsetY = (random.nextDouble() - 0.5) * 0.5 + 1.0;

                        }
                    }

                }

            for (int i = 0; i < 9; i++) {
                String keyName = "key.abilitiesmod.ability_" + (i + 1);
                int defaultKey = getDefaultKeyForSlot(i);

                ABILITY_KEYS[i] = new KeyBinding(
                        keyName,
                        InputUtil.Type.KEYSYM,
                        defaultKey,
                        CATEGORY
                );

                KeyBindingHelper.registerKeyBinding(ABILITY_KEYS[i]);
            }
        });


    }

    private static int getDefaultKeyForSlot(int slot) {
        // Default keybinds: 1-9 keys
        return GLFW.GLFW_KEY_1 + slot;
    }

    public static void checkKeyPresses() {
        for (int i = 0; i < ABILITY_KEYS.length; i++) {
            if (ABILITY_KEYS[i].wasPressed()) {
                // Send packet to server to use ability
                ModPackets.sendToServer(new AbilityUsePacket(i));
                break;
            }
        }
    }
}

