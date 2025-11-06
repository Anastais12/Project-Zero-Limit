package com.cl1ppz12.projectzerolimit.command;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.cl1ppz12.projectzerolimit.ability.Ability;
import com.cl1ppz12.projectzerolimit.server.PlayerAbilityStore;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import com.cl1ppz12.projectzerolimit.ability.AbilityRegistry;
import net.minecraft.util.Identifier;

public class ModCommands {

    private static final SimpleCommandExceptionType NOT_FOUND =
            new SimpleCommandExceptionType(Text.literal("Unknown ability"));
    private static final SimpleCommandExceptionType BAD_SLOT =
            new SimpleCommandExceptionType(Text.literal("Slot must be 0-4"));

    private static final SuggestionProvider<ServerCommandSource> ABILITY_SUGGESTIONS =
            (ctx, builder) -> CommandSource.suggestIdentifiers(
                    AbilityRegistry.ENTRIES.keySet().stream(),
                    builder
            );

    public static void registerCommands(Event<CommandRegistrationCallback> event) {
        ProjectZeroLimit.LOGGER.info("Registering commands for " + ProjectZeroLimit.MOD_ID);

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            LiteralCommandNode<ServerCommandSource> zeroLimitCommand = dispatcher.register(
                    CommandManager.literal("project-zero-limit")
                            .then(CommandManager.literal("ability"))
                            .requires(src -> src.hasPermissionLevel(2))
                            .then(assignSub())
                            .then(clearSub())
            );

            dispatcher.register(CommandManager.literal("project-zero-limit").redirect(zeroLimitCommand));
        });
    }

    private static LiteralArgumentBuilder<ServerCommandSource> assignSub() {
        return CommandManager.literal("assign")
                .then(CommandManager.argument("ability", IdentifierArgumentType.identifier())
                        .suggests(ABILITY_SUGGESTIONS)
                        .executes(ctx -> assign(ctx, -1))
                        .then(CommandManager.argument("slot", IntegerArgumentType.integer(0, 4))
                                .executes(ctx -> assign(ctx, IntegerArgumentType.getInteger(ctx, "slot")))
                        )
                );
    }

    private static LiteralArgumentBuilder<ServerCommandSource> clearSub() {
        return CommandManager.literal("clear")
                .executes(ctx -> clear(ctx, -1))                        // clear all
                .then(CommandManager.argument("slot", IntegerArgumentType.integer(0, 4))
                        .executes(ctx -> clear(ctx, IntegerArgumentType.getInteger(ctx, "slot")))
                );
    }

    /* ---- logic ---- */

    private static int assign(CommandContext<ServerCommandSource> ctx, int slot) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        Identifier id = Identifier.tryParse(StringArgumentType.getString(ctx, "ability"));
        Ability ability = AbilityRegistry.get(id);
        if (ability == null) throw NOT_FOUND.create();

        // grant ownership (idempotent)
        PlayerAbilityStore.grant(player, id);

        int targetSlot = slot;
        if (targetSlot == -1) {                                 // auto-pick first empty
            int[] bound = PlayerAbilityStore.getBoundSlots(player);
            for (int i = 0; i < 5; i++) if (bound[i] == -1) { targetSlot = i; break; }
            if (targetSlot == -1) targetSlot = 0;               // fallback: overwrite slot 0
        }

        PlayerAbilityStore.setBoundSlot(player, targetSlot, id);
        int finalTargetSlot = targetSlot;
        ctx.getSource().sendFeedback(
                () -> Text.literal("Assigned §b" + id + " §rto slot §6" + finalTargetSlot), true);
        return 1;
    }

    private static int clear(CommandContext<ServerCommandSource> ctx, int slot) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        if (slot == -1) {                                       // clear whole bar
            for (int i = 0; i < 5; i++) PlayerAbilityStore.setBoundSlot(player, i, null);
            ctx.getSource().sendFeedback(() -> Text.literal("Cleared all ability slots"), true);
        } else {
            PlayerAbilityStore.setBoundSlot(player, slot, null);
            ctx.getSource().sendFeedback(() -> Text.literal("Cleared slot §6" + slot), true);
        }
        return 1;
    }

}
