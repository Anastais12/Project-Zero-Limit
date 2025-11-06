package com.cl1ppz12.projectzerolimit.client.hud;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.cl1ppz12.projectzerolimit.client.hud.ui.HotbarAbilityIcons;

public class ModRender {

    public static void renderOnHud() {
        AbilityBarRender.init();
        HotbarAbilityIcons.init();
        AuraHudRenderer.init();

        ProjectZeroLimit.LOGGER.info("Project Zero Limit Hud Renders are Initialized!");
    }
}
