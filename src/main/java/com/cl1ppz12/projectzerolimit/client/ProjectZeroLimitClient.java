package com.cl1ppz12.projectzerolimit.client; // Ensure this is your client package

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.cl1ppz12.projectzerolimit.client.gui.screen.ingame.AbilityInventoryScreen;
import com.cl1ppz12.projectzerolimit.network.OpenAbilityInventoryPayload; // <<< IMPORT THIS
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking; // <<< IMPORT THIS
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text; // For messages
import org.lwjgl.glfw.GLFW;



public class ProjectZeroLimitClient implements ClientModInitializer {



    public static KeyBinding keyZ, keyX, keyC, keyV, keyG;



    @Override

    public void onInitializeClient() {

        ProjectZeroLimit.LOGGER.info("Project Zero Limit Client is initializing!");



        keyZ = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.z", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, "category.project_zero_limit.abilities"));
        keyX = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.x", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X, "category.project_zero_limit.abilities"));
        keyC = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.c", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "category.project_zero_limit.abilities"));
        keyV = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.v", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "category.project_zero_limit.abilities"));
        keyG = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.project_zero_limit.g", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.project_zero_limit.abilities"));

        HandledScreens.register(ProjectZeroLimit.ABILITY_INVENTORY_SCREEN_HANDLER, AbilityInventoryScreen::new);

        ActiveAbilityHudManager.registerHudRender();



        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            while (keyZ.wasPressed()) {

                if (client.player != null) client.player.sendMessage(Text.literal("Z key pressed!"), false);

            }

            while (keyX.wasPressed()) {

                if (client.player != null) client.player.sendMessage(Text.literal("X key pressed!"), false);

            }

            while (keyC.wasPressed()) {

                if (client.player != null) client.player.sendMessage(Text.literal("C key pressed!"), false);

            }

            while (keyV.wasPressed()) {

                if (client.player != null) client.player.sendMessage(Text.literal("V key pressed!"), false);

            }

            while (keyG.wasPressed()) {

                ClientPlayNetworking.send(new OpenAbilityInventoryPayload());
            }

        });

        ProjectZeroLimit.LOGGER.info("Keybindings and screen registered. Packet sender configured.");

    }

}