package com.cl1ppz12.projectzerolimit.ability;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit; // Import your main mod class
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * A concrete implementation of the Ability class for testing purposes.
 * This ability simply sends a message to the player when activated.
 */
public class TestAbility extends Ability {

    public TestAbility(Identifier id, Text name, Text description) {
        super(id, name, description);
    }

    /**
     * Defines the effect of the TestAbility.
     * In this case, it sends a chat message to the player.
     * @param player The player who activated the ability.
     */
    @Override
    public void activate(PlayerEntity player) {
        if (!player.getWorld().isClient()) {
            player.sendMessage(Text.translatable("ability.project_zero_limit.test_ability.activated"), false);
            ProjectZeroLimit.LOGGER.info("Player {} activated Test Ability!", player.getName().getString());
        }
    }
}
