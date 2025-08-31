package com.cl1ppz12.projectzerolimit.client;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.cl1ppz12.projectzerolimit.client.util.ModKeybindings;
import com.cl1ppz12.projectzerolimit.client.util.PlayerSkinRenderer;
import com.cl1ppz12.projectzerolimit.client.util.VanillaKeyUnbinder;
import com.cl1ppz12.projectzerolimit.particle.ModParticles;
import net.fabricmc.api.ClientModInitializer;


public class ProjectZeroLimitClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        ProjectZeroLimit.LOGGER.info("Project Zero Limit Client is initializing!");

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

        ActiveAbilityHudManager.registerHudRender();

        ModKeybindings.registerModKeybindings();
        VanillaKeyUnbinder.registerUnbinder();

        PlayerSkinRenderer.registerPlayerSkinRendering();

        ModParticles.registerClientParticles();

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        ProjectZeroLimit.LOGGER.info("Client-side initialization complete.");

    }

}