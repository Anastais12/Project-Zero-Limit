package com.cl1ppz12.projectzerolimit.client.util;

import com.cl1ppz12.projectzerolimit.network.UpdateActiveAbilitySlotPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cl1ppz12.projectzerolimit.ProjectZeroLimit; // Import your main mod class for LOGGER

public class ClientPacketUtil {
    // We already have a logger in ProjectZeroLimit, let's reuse it or define a new one here if preferred.
    // For simplicity, I'll use the main mod's logger.
    private static final Logger LOGGER = ProjectZeroLimit.LOGGER;

    /**
     * Sends a packet to the server to update a specific active ability slot.
     * This is typically called when a player assigns an item to an active slot
     * from a custom inventory screen using a keybind.
     *
     * @param slotIndex The index of the active ability slot (0 for Z, 1 for X, 2 for C, 3 for V).
     * @param stack The ItemStack to place into that active slot.
     */
    public static void sendUpdateActiveAbilitySlotPacket(int slotIndex, ItemStack stack) {
        if (ClientPlayNetworking.canSend(UpdateActiveAbilitySlotPayload.ID)) {
            UpdateActiveAbilitySlotPayload payload = new UpdateActiveAbilitySlotPayload(slotIndex, stack);
            ClientPlayNetworking.send(payload);
            LOGGER.info("Client sent UpdateActiveAbilitySlotPayload for slot {}: {}", slotIndex, stack.getName().getString());
        } else {
            LOGGER.warn("Attempted to send UpdateActiveAbilitySlotPayload but client is not connected or packet type not registered.");
        }
    }
}