package com.cl1ppz12.projectzerolimit.command;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ModCommands {

    public static void registerCommands() {
        ProjectZeroLimit.LOGGER.info("Registering commands for " + ProjectZeroLimit.MOD_ID);

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            LiteralCommandNode<ServerCommandSource> zeroLimitCommand = dispatcher.register(
                    CommandManager.literal("zero-limit")
                            .then(CommandManager.literal("cmd")
                                    .then(CommandManager.argument("cmd_id", IdentifierArgumentType.identifier())

                                    )
                            )
            );




            dispatcher.register(CommandManager.literal("pz").redirect(zeroLimitCommand));
        });

        ProjectZeroLimit.LOGGER.info("Successfully registered commands for " + ProjectZeroLimit.MOD_ID);
    }

}
