package com.cl1ppz12.projectzerolimit.network;

import com.cl1ppz12.projectzerolimit.ability.Ability;
import com.cl1ppz12.projectzerolimit.ability.AbilityRegistry;
import com.cl1ppz12.projectzerolimit.client.aura.ClientAuraData;
import com.cl1ppz12.projectzerolimit.client.gui.mask.OniMaskInventory;
import com.cl1ppz12.projectzerolimit.client.gui.mask.OniMaskScreen;
import com.cl1ppz12.projectzerolimit.client.gui.mask.OniMaskScreenHandler;
import com.cl1ppz12.projectzerolimit.network.payload.c2s.CategorySwitchPayload;
import com.cl1ppz12.projectzerolimit.network.payload.s2c.AbilityListPayload;
import com.cl1ppz12.projectzerolimit.network.payload.s2c.AbilityUsePayload;
import com.cl1ppz12.projectzerolimit.network.payload.c2s.OpenAbilityInventoryPayload;
import com.cl1ppz12.projectzerolimit.network.payload.s2c.AuraSyncPayload;
import com.cl1ppz12.projectzerolimit.network.payload.s2c.OpenOniMaskPayload;
import com.cl1ppz12.projectzerolimit.server.PlayerAbilityStore;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


public class ModNetworking {

    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(AbilityUsePayload.ID, AbilityUsePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(AbilityUsePayload.ID,
                (payload, context) -> {
                    ServerPlayerEntity player = context.player();
                    var ability = AbilityRegistry.get(payload.abilityId());
                    if (ability != null && ability.canUse(player)) {
                        ability.use(player);
                        ability.onUsed(player);
                    }
                });


        PayloadTypeRegistry.playC2S().register(OpenAbilityInventoryPayload.ID, OpenAbilityInventoryPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(OpenAbilityInventoryPayload.ID,
                (payload, ctx) -> {
                    ServerPlayerEntity player = ctx.player();
                    //PlayerAbilityStore.giveStarterKit(player);
                    AbilityListPayload.sendToPlayer(player);
                });

        PayloadTypeRegistry.playC2S().register(CategorySwitchPayload.ID, CategorySwitchPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(CategorySwitchPayload.ID,
                (payload, context) -> {
                    ServerPlayerEntity player = context.player();
                    Ability.AbilityType newTab = payload.category();
                    PlayerAbilityStore.setSelectedCategory(player, newTab);
                    AbilityListPayload.sendToPlayer(player);
                });
    }

    @Environment(EnvType.CLIENT)
    public static void registerS2CPackets() {

        PayloadTypeRegistry.playS2C().register(AbilityUsePayload.ID, AbilityUsePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(AbilityListPayload.ID, AbilityListPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(OpenOniMaskPayload.ID, OpenOniMaskPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(AuraSyncPayload.ID, AuraSyncPayload.CODEC);

        ClientPlayNetworking.registerGlobalReceiver(OpenOniMaskPayload.ID,
                (payload, context) -> context.client().execute(() -> {
                    MinecraftClient client = MinecraftClient.getInstance();
                    OniMaskInventory inv = new OniMaskInventory();
                    inv.readFromNbt(payload.inventoryNbt(), client.world.getRegistryManager());

                    client.setScreen(new OniMaskScreen(
                            new OniMaskScreenHandler(0, client.player.getInventory(), inv),
                            client.player.getInventory(),
                            Text.literal("")
                    ));
                }));

        ClientPlayNetworking.registerGlobalReceiver(AuraSyncPayload.ID,
                (payload, ctx) -> ctx.client().execute(() ->
                        ClientAuraData.set(payload.aura())));
    }

    public static void openMaskScreen(ServerPlayerEntity player, OniMaskInventory inv) {
        OpenOniMaskPayload payload =
                OpenOniMaskPayload.fromInventory(inv, player.getRegistryManager());
        ServerPlayNetworking.send(player, payload);
    }
}
