package com.cl1ppz12.projectzerolimit.team;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class TeamManager {

    // Internal scoreboard ID for the team
    public static final String TEAM_ID = "shadow_realm";

    /**
     * Creates the Shadow Realm team if it doesn't already exist.
     */
    public static void createTeam(MinecraftServer server, String interDimenisonalTravel) {
        Scoreboard scoreboard = server.getScoreboard();
        Team team = scoreboard.getTeam(TEAM_ID);

        if (team == null) {
            team = scoreboard.addTeam(TEAM_ID);

            // Pretty display name in green
            team.setDisplayName(Text.literal("§aShadow Realm"));

            // Team color (affects name tags, chat, etc.)
            team.setColor(Formatting.RED);

            // Disable friendly fire
            team.setFriendlyFireAllowed(false);

            // Allow teammates to see invisible teammates
            team.setShowFriendlyInvisibles(true);

            // Broadcast creation message
            server.getPlayerManager().broadcast(
                    Text.literal("Team 'Shadow Realm' has been created!"),
                    false
            );
        }
    }
}
