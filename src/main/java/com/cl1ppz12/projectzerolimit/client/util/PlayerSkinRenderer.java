package com.cl1ppz12.projectzerolimit.client.util;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerSkinRenderer {
    public static final Logger LOGGER = LoggerFactory.getLogger("ProjectZeroLimit-PlayerSkinRenderer");

    public static final Identifier COMBAT_SKIN_TEXTURE = Identifier.of(ProjectZeroLimit.MOD_ID, "textures/entity/player/zero_limit_skin.png");


    public static void registerPlayerSkinRendering() {
        LOGGER.info("Player skin rendering setup (via Mixin) initialized.");
    }
}
