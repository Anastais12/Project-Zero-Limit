package com.cl1ppz12.projectzerolimit.ability.custom.oni_arts;

import com.cl1ppz12.projectzerolimit.ability.Ability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ShadowOfTheLivingWorld extends Ability {

    public ShadowOfTheLivingWorld(String id, Text name, Text description, Identifier texture, AbilityType type, float damage, int auraCost, int cooldownTicks) {
        super(id, name, description, texture, type, damage, auraCost, cooldownTicks);
    }

    @Override
    public Identifier getIconTexture() {
        return Identifier.of("project_zero_limit", "textures/ability/oni_art/shadow_of_the_living_world.png");
    }

    @Override
    public void playSound(LivingEntity entity, BlockPos pos, ServerWorld world) {

    }

    @Override
    public void use(ServerPlayerEntity player) {

    }
}
