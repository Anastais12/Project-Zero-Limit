package com.cl1ppz12.projectzerolimit.client;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.cl1ppz12.projectzerolimit.client.util.ModKeybindings;
import com.cl1ppz12.projectzerolimit.client.util.VanillaKeyUnbinder;
import com.cl1ppz12.projectzerolimit.hud.AbilityBarHud;
import com.cl1ppz12.projectzerolimit.network.ModPackets;
import com.cl1ppz12.projectzerolimit.particle.ModParticles;
import com.cl1ppz12.projectzerolimit.screen.ModScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;


public class ProjectZeroLimitClient implements ClientModInitializer {



    @Override
    public void onInitializeClient() {
        ProjectZeroLimit.LOGGER.info("Project Zero Limit Client is initializing!");

//----------------------------------------------------------------------------------------------------------------------//

        ModKeybindings.registerModKeybindings();
        VanillaKeyUnbinder.registerUnbinder();

        ModParticles.registerClientParticles();

        ModScreenHandler.register();

        // Register HUD renderer
        HudRenderCallback.EVENT.register(new AbilityBarHud());

        // Register client packet handlers
        ModPackets.registerClient();

//----------------------------------------------------------------------------------------------------------------------//
        ProjectZeroLimit.LOGGER.info("Client-side initialization complete.");

    }

}