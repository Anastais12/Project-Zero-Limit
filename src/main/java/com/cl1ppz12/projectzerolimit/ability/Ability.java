package com.cl1ppz12.projectzerolimit.ability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.text.Text;

public abstract class Ability {
    private final Identifier id;
    private final String name;
    private final int cooldown;
    private final int maxLevel;

    public Ability(Identifier id, String name, int cooldown, int maxLevel) {
        this.id = id;
        this.name = name;
        this.cooldown = cooldown;
        this.maxLevel = maxLevel;
    }

    public abstract void use(PlayerEntity player, int level);

    public abstract boolean canUse(PlayerEntity player, int level);

    public Identifier getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public Text getDisplayName() {
        return Text.literal(name);
    }

    public enum AbilityType {
        ACTIVE,
        PASSIVE,
        ULTIMATE
    }
}