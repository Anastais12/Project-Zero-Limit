package com.cl1ppz12.projectzerolimit;

import com.cl1ppz12.projectzerolimit.ability.ModAbilities;
import com.cl1ppz12.projectzerolimit.ability.profile.ModProfiles;
import com.cl1ppz12.projectzerolimit.command.ModCommands;
import com.cl1ppz12.projectzerolimit.dimension.ModDimensions;
import com.cl1ppz12.projectzerolimit.effect.ModEffects;
import com.cl1ppz12.projectzerolimit.item.ZeroLimitItems;
import com.cl1ppz12.projectzerolimit.networking.ModPacketsRegistry;
import com.cl1ppz12.projectzerolimit.particle.ModParticles;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectZeroLimit implements ModInitializer {

	public static final String MOD_ID = "project_zero_limit";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Project Zero Limit is initializing!");
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

		ZeroLimitItems.initialize();

		ModEffects.registerEffects();

		ModAbilities.registerAbilities();
		ModProfiles.registerProfiles();

		ModCommands.registerCommands();

		ModDimensions.registerDimensions();

		ModParticles.registerParticles();

		ModPacketsRegistry.registerC2SPackets();

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

		LOGGER.info("Project Zero Limit Initialized!");
	}
}
