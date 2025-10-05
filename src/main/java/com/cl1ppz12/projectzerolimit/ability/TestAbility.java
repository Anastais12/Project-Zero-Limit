package com.cl1ppz12.projectzerolimit.ability;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class TestAbility extends Ability {

    public TestAbility() {
        super(Identifier.of(ProjectZeroLimit.MOD_ID, "test_ability"), "Test Ability", 100, 5);
    }

    @Override
    public void use(PlayerEntity player, int level) {
        World world = player.getWorld();
        if (!world.isClient) {
            player.addVelocity(0, 0.5 * level, 0);
            player.velocityModified = true;

            player.sendMessage(Text.literal("Test Ability used! Level: " + level), false);
        }
    }

    @Override
    public boolean canUse(PlayerEntity player, int level) {
        return player.isOnGround() && level > 0;
    }
}