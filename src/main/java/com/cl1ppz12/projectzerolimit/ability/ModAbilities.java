package com.cl1ppz12.projectzerolimit.ability;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple registry for all custom abilities in Project Zero Limit.
 * This allows abilities to be looked up by their Identifier.
 */
public class ModAbilities {
    // A map to store abilities, keyed by their Identifier
    private static final Map<Identifier, Ability> ABILITIES = new HashMap<>();

    // --- Define your abilities here ---
    public static final Ability TEST_ABILITY = register(
            new TestAbility(
                    Identifier.of(ProjectZeroLimit.MOD_ID, "test_ability"),
                    Text.translatable("ability.project_zero_limit.test_ability.name"),
                    Text.translatable("ability.project_zero_limit.test_ability.description")
            )
    );


    /**
     * Registers an ability to the internal map.
     * @param ability The ability to register.
     * @param <T> The type of the ability.
     * @return The registered ability.
     */
    private static <T extends Ability> T register(T ability) {
        ABILITIES.put(ability.getId(), ability);
        ProjectZeroLimit.LOGGER.info("Registered ability: {}", ability.getId());
        return ability;
    }

    /**
     * Retrieves an ability by its Identifier.
     * @param id The Identifier of the ability.
     * @return The Ability object, or null if not found.
     */
    @Nullable
    public static Ability getAbility(Identifier id) {
        return ABILITIES.get(id);
    }

    /**
     * Initializes and registers all abilities. Call this in your main mod class.
     */
    public static void registerAbilities() {
        ProjectZeroLimit.LOGGER.info("Initializing ModAbilities for " + ProjectZeroLimit.MOD_ID);

    }
}
