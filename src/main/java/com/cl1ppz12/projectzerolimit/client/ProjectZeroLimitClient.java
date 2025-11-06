package com.cl1ppz12.projectzerolimit.client;

import com.cl1ppz12.projectzerolimit.ModEvents;
import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.cl1ppz12.projectzerolimit.ability.Ability;
import com.cl1ppz12.projectzerolimit.ability.AbilityRegistry;
import com.cl1ppz12.projectzerolimit.ability.AbilityUUIDRegistry;
import com.cl1ppz12.projectzerolimit.client.gui.AbilityInventoryScreen;
import com.cl1ppz12.projectzerolimit.client.hud.ModRender;
import com.cl1ppz12.projectzerolimit.client.util.AbilityInputHandler;
import com.cl1ppz12.projectzerolimit.client.util.ModKeybindings;
import com.cl1ppz12.projectzerolimit.client.util.VanillaKeyUnbinder;
import com.cl1ppz12.projectzerolimit.editor.BlackHoleEditor;
import com.cl1ppz12.projectzerolimit.editor.VeilInspector;
import com.cl1ppz12.projectzerolimit.entity.ModEntityRenderer;
import com.cl1ppz12.projectzerolimit.network.ModNetworking;
import com.cl1ppz12.projectzerolimit.network.payload.c2s.CategorySwitchPayload;
import com.cl1ppz12.projectzerolimit.network.payload.s2c.AbilityListPayload;
import com.cl1ppz12.projectzerolimit.network.payload.c2s.OpenAbilityInventoryPayload;
import com.cl1ppz12.projectzerolimit.particle.ModParticles;
import com.cl1ppz12.projectzerolimit.shader.ModShaders;
import foundry.veil.fabric.event.FabricVeilRendererAvailableEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.UUID;



public class ProjectZeroLimitClient implements ClientModInitializer {


    private static KeyBinding abilityMenuKeybind;


    @Override
    public void onInitializeClient() {
        ProjectZeroLimit.LOGGER.info("Project Zero Limit Client is initializing!");

//--------------------------------------------------------------------------------------------------------//

        ModKeybindings.registerModKeybindings();
        AbilityInputHandler.init();
        VanillaKeyUnbinder.registerUnbinder();

        ModParticles.registerClientParticles();

        ModNetworking.registerS2CPackets();

        ModEntityRenderer.register();

        ModShaders.registerShaders();

        ModEvents.registerEvents();

        ModRender.renderOnHud();

        AbilityUUIDRegistry.bootstrap();

        AbilityDebugPrinter.init();

        FabricVeilRendererAvailableEvent.EVENT.register(renderer -> renderer.getEditorManager().add(new VeilInspector()));
        FabricVeilRendererAvailableEvent.EVENT.register(renderer -> renderer.getEditorManager().add(new BlackHoleEditor()));

//--------------------------------------------------------------------------------------------------------//

        KeyBinding invKey = new KeyBinding("key.project_zero_limit.ability_inv",
                GLFW.GLFW_KEY_RIGHT_ALT, "category.project_zero_limit.abilities");
        KeyBindingHelper.registerKeyBinding(invKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (invKey.wasPressed()) {
                System.out.println(">>> RIGHT-ALT pressed – sending packet");
                ClientPlayNetworking.send(new OpenAbilityInventoryPayload());
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(
                AbilityListPayload.ID,
                (payload, context) -> context.client().execute(() -> {
                    /* ---------- debug ---------- */
                    System.out.println(">>> Client received ability list – opening screen");

                    /* ---------- fill cache ---------- */
                    int[] slots = payload.boundSlots();
                    for (int i = 0; i < 5; i++) {
                        int idx = slots[i];
                        UUID uuid = idx == -1 ? null
                                : AbilityUUIDRegistry.getUuid(AbilityRegistry.get(idx));
                        ClientAbilityCache.updateSlot(i, uuid);
                    }

                    /* ---------- open screen ---------- */
                    List<Ability> list = payload.owned()
                            .stream()
                            .map(AbilityRegistry::get)
                            .toList();
                    MinecraftClient.getInstance().setScreen(new AbilityInventoryScreen(list));
                })
        );

//--------------------------------------------------------------------------------------------------------//


        ProjectZeroLimit.LOGGER.info("Client-side initialization complete.");

    }

}