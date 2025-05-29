package com.cl1ppz12.projectzerolimit;

import com.cl1ppz12.projectzerolimit.inventory.AbilityInventoryScreenHandler;
import com.cl1ppz12.projectzerolimit.item.ZeroLimitItemGroups;
import com.cl1ppz12.projectzerolimit.item.ZeroLimitItems;
import com.cl1ppz12.projectzerolimit.network.OpenAbilityInventoryPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class ProjectZeroLimit implements ModInitializer {

	public static final String MOD_ID = "project_zero_limit"; // Ensure this matches your fabric.mod.json
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



	public static ScreenHandlerType<AbilityInventoryScreenHandler> ABILITY_INVENTORY_SCREEN_HANDLER;



	@Override

	public void onInitialize() {

		ZeroLimitItemGroups.registerItemGroups();
		ZeroLimitItems.registerModItems();

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		LOGGER.info("Project Zero Limit is initializing!");



		ABILITY_INVENTORY_SCREEN_HANDLER = Registry.register(

				Registries.SCREEN_HANDLER,

				Identifier.of(MOD_ID, "ability_inventory"),

				new ScreenHandlerType<>(AbilityInventoryScreenHandler::new, FeatureFlags.VANILLA_FEATURES)

		);

		LOGGER.info("Registered Screen Handler Type.");



// Register the payload type for networking (S2C and C2S)

		PayloadTypeRegistry.playC2S().register(OpenAbilityInventoryPayload.ID, OpenAbilityInventoryPayload.CODEC);

// If you were to send this payload S2C, also register with playS2C()



// Register Server-Side Packet Handler for the new payload

		ServerPlayNetworking.registerGlobalReceiver(OpenAbilityInventoryPayload.ID, (payload, context) -> {

			ServerPlayNetworking.Context Ctx = (ServerPlayNetworking.Context) context;

// Ensure execution on the main server thread

			Ctx.server().execute(() -> {

				PlayerEntity player = Ctx.player();

				if (player != null) {

					player.openHandledScreen(new NamedScreenHandlerFactory() {

						@Override

						public Text getDisplayName() {

// Make sure to add this to your lang file:

// "container.project_zero_limit.ability_inventory": "Ability Inventory"

							return Text.translatable("container.project_zero_limit.ability_inventory");

						}
						@Override

						public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity playerEntity) {

// The handler itself will create its SimpleInventory

							return new AbilityInventoryScreenHandler(syncId, playerInventory);

						}

					});

				}

			});

		});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		LOGGER.info("Registered packet receiver.");

	}

}