package com.cl1ppz12.projectzerolimit.client.util;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class VanillaKeyUnbinder {

    private static boolean hasUnboundVanillaKeys = false;

    public static void registerUnbinder() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!hasUnboundVanillaKeys && client.player != null && client.options != null) {
                GameOptions options = client.options;

                KeyBinding saveToolbarKey = options.saveToolbarActivatorKey;
                if (saveToolbarKey != null && saveToolbarKey.isDefault()) {
                    saveToolbarKey.setBoundKey(InputUtil.UNKNOWN_KEY);
                    ProjectZeroLimit.LOGGER.info("Project Zero Limit: Successfully unbound 'Save Hotbar'.");
                    options.write();
                }

                KeyBinding loadToolbarKey = options.loadToolbarActivatorKey;
                if (loadToolbarKey != null && loadToolbarKey.isDefault()) {
                    loadToolbarKey.setBoundKey(InputUtil.UNKNOWN_KEY);
                    ProjectZeroLimit.LOGGER.info("Project Zero Limit: Successfully unbound 'Load Hotbar'.");
                    options.write();
                }

                hasUnboundVanillaKeys = true;
            }
        });
    }
}