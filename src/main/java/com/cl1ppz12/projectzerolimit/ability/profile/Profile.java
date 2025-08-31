package com.cl1ppz12.projectzerolimit.ability.profile;

import com.cl1ppz12.projectzerolimit.ability.Ability;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a player profile, containing a set of four unique abilities.
 */
public class Profile {
    private final Identifier id;
    private final Text name;
    private final Text description;
    private final List<Ability> abilities;

    /**
     * Creates a new Profile.
     * @param id The unique identifier for this profile.
     * @param name The display name of the profile.
     * @param ability1 The first ability.
     * @param ability2 The second ability.
     * @param ability3 The third ability.
     * @param ability4 The fourth ability.
     * @throws IllegalArgumentException If any ability is null.
     */
    public Profile(Identifier id, Text name, Text description , Ability ability1, Ability ability2, Ability ability3, Ability ability4) {
        if (ability1 == null || ability2 == null || ability3 == null || ability4 == null) {
            throw new IllegalArgumentException("All four abilities must be non-null for a profile.");
        }
        this.id = id;
        this.name = name;
        this.description = description;
        this.abilities = Arrays.asList(ability1, ability2, ability3, ability4);
    }

    /**
     * Gets the unique identifier of this profile.
     * @return The Identifier.
     */
    public Identifier getId() {
        return id;
    }

    /**
     * Gets the display name of this profile.
     * @return The display name as Text.
     */
    public Text getName() {
        return name;
    }

    /**
     * Gets the description of this profile.
     * @return The display name as Text.
     */
    public Text getDescription() {
        return description;
    }

    /**
     * Gets the list of abilities for this profile.
     * @return An unmodifiable list of 4 abilities.
     */
    public List<Ability> getAbilities() {
        return abilities;
    }

    /**
     * Gets a specific ability from this profile by its slot index (0-3).
     * @param slotIndex The index of the ability (0 to 3).
     * @return The Ability at the given index.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public Ability getAbilityInSlot(int slotIndex) {
        return abilities.get(slotIndex);
    }
}
