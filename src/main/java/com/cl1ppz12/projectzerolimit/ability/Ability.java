package com.cl1ppz12.projectzerolimit.ability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * Represents a generic ability in Project Zero Limit.
 * This class holds the core data for an ability and defines its activation logic.
 * Abilities should be registered to a custom registry if you have many.
 */
public abstract class Ability {
    private final Identifier id;
    private final Text name;
    private final Text description;

    /**
     * Constructor for a new Ability.
     * @param id The unique Identifier for this ability.
     * @param name The display name of the ability.
     * @param description The description of the ability.
     */
    public Ability(Identifier id, Text name, Text description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the unique identifier of this ability.
     * @return The Identifier.
     */
    public Identifier getId() {
        return id;
    }

    /**
     * Gets the display name of this ability.
     * @return The display name as Text.
     */
    public Text getName() {
        return name;
    }

    /**
     * Gets the description of this ability.
     * @return The description as Text.
     */
    public Text getDescription() {
        return description;
    }

    /**
     * Abstract method to define the effect of the ability when activated.
     * This method MUST be implemented by concrete ability classes.
     * @param player The player who activated the ability.
     */
    public abstract void activate(PlayerEntity player);

}
