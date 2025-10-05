package com.cl1ppz12.projectzerolimit;

import com.cl1ppz12.projectzerolimit.ability.AbilityRegistry;
import com.cl1ppz12.projectzerolimit.command.ModCommands;
import com.cl1ppz12.projectzerolimit.effect.ModEffects;
import com.cl1ppz12.projectzerolimit.handler.PlayerStageDataHandler;
import com.cl1ppz12.projectzerolimit.item.ModItemGroups;
import com.cl1ppz12.projectzerolimit.item.ModItems;
import com.cl1ppz12.projectzerolimit.network.ModNetworking;
import com.cl1ppz12.projectzerolimit.network.ModPackets;
import com.cl1ppz12.projectzerolimit.particle.ModParticles;
import com.cl1ppz12.projectzerolimit.sound.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.cl1ppz12.projectzerolimit.handler.PlayerStageDataHandler.addPlayer;

public class ProjectZeroLimit implements ModInitializer {

	public static final String MOD_ID = "project_zero_limit";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Project Zero Limit is initializing!");
//--------------------------------------------------------------------------------------------------------//

		ModItems.initialize();
		ModItemGroups.initialize();

		ModEffects.registerEffects();

		ModCommands.registerCommands();

		ModParticles.registerParticles();

		ModEvents.registerEvents();

		ModSounds.registerSounds();

		ModNetworking.registerC2SPackets();
		ModNetworking.registerS2CPackets();

		AbilityRegistry.init();

		ModPackets.register();

		// Register server tick event to update ability cooldowns
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			server.getPlayerManager().getPlayerList().forEach(player -> {
				PlayerAbilityData abilityData = PlayerAbilityData.get(player);
				if (abilityData != null) {
					abilityData.tick();
				}
			});
		});


//--------------------------------------------------------------------------------------------------------//

		ServerWorldEvents.LOAD.register(PlayerStageDataHandler::onWorldLoad);
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				addPlayer(server, player.getUuid());
			}
		});

//--------------------------------------------------------------------------------------------------------//

		LOGGER.info("Project Zero Limit Initialized!");
	}
}
