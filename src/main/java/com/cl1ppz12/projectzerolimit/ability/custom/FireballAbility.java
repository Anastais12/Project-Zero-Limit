package com.cl1ppz12.projectzerolimit.ability.custom;

import com.cl1ppz12.projectzerolimit.ability.Ability;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public final class FireballAbility extends Ability {

    public FireballAbility(String id, Text name, Text description, Identifier texture, AbilityType type, float damage, int auraCost, int cooldownTicks) {
        super(id, name, description, texture, type, damage, auraCost, cooldownTicks);
    }


    @Override
    public Identifier getIconTexture() {
        // Return the identifier for the icon texture
        return Identifier.of("project_zero_limit", "textures/ability/fireball.png");
    }

    @Override
    public void playSound(LivingEntity entity, BlockPos pos, ServerWorld world) {

    }

    @Override
    public void use(ServerPlayerEntity player) {
        Vec3d look = player.getRotationVec(1.0F);
        FireballEntity fb = new FireballEntity(EntityType.FIREBALL, player.getServerWorld());
        fb.setPosition(player.getX() + look.x * 2,
                player.getEyeY(),
                player.getZ() + look.z * 2);
        player.getServerWorld().spawnEntity(fb);
    }
}