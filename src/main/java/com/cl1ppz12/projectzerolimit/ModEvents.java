package com.cl1ppz12.projectzerolimit;

import com.cl1ppz12.projectzerolimit.team.TeamManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class ModEvents {

    public static void registerEvents() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            TeamManager.createTeam(server, "InterDimenisonalTravel");
        });
    }
}