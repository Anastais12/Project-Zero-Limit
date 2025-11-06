package com.cl1ppz12.projectzerolimit.entity.custom;

import com.cl1ppz12.projectzerolimit.entity.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.UUID;

public class BlackHoleEntity extends MobEntity {

    public BlackHoleEntity(EntityType<? extends BlackHoleEntity> type, World world) {
        super(type, world);
    }

    public BlackHoleEntity(World world, PlayerEntity player, Vec3d rotationVec) {
        this(ModEntities.BLACK_HOLE, world);
        this.setPosition(player.getX(), player.getY(), player.getZ());
        this.setOwner(player);
    }

    private UUID owner;

    public void setOwner(PlayerEntity player) {
        this.owner = player.getUuid();
    }

    public UUID getOwner() {
        return this.owner;
    }

    public static DefaultAttributeContainer.Builder createBlackHoleAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0D);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false; // immune to everything
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }
}
