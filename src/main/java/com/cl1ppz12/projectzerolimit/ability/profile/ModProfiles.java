package com.cl1ppz12.projectzerolimit.ability.profile;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.cl1ppz12.projectzerolimit.ability.Ability;
import com.cl1ppz12.projectzerolimit.ability.ModAbilities;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

/**
 * A central registry for all predefined player profiles in Project Zero Limit.
 * This registry is strictly limited to a maximum of 4 profiles.
 */
public class ModProfiles {
    private static final Map<Identifier, Profile> PROFILES = new HashMap<>();
    private static final int MAX_PROFILES = 4; // Enforce a maximum of 4 profiles



    public static final Profile MIND_PHANTOM = register(
            new Profile(
                    Identifier.of(ProjectZeroLimit.MOD_ID, "mind_phantom"),
                    Text.translatable("profile.project_zero_limit.mind_phantom.name"),
                    Text.translatable("profile.project_zero_limit.mind_phantom.description"),
                    ModAbilities.TEST_ABILITY,//Soul Mirage
                    ModAbilities.TEST_ABILITY,//Emotion Stealth
                    ModAbilities.TEST_ABILITY,//Hall of Eyes
                    ModAbilities.TEST_ABILITY//Curse Thread Marionette
            )
    );


    public static final Profile CHRONO_SOVERGN = register(
            new Profile(
                    Identifier.of(ProjectZeroLimit.MOD_ID, "chrono_sovergn"),
                    Text.translatable("profile.project_zero_limit.chrono_sovergn.name"),
                    Text.translatable("profile.project_zero_limit.chrono_sovergn.description"),
                    ModAbilities.TEST_ABILITY,//Heaven’s Divide
                    ModAbilities.TEST_ABILITY,//Parallel Cross
                    ModAbilities.TEST_ABILITY,//Shadow Realm
                    ModAbilities.TEST_ABILITY//Nightmare Slip
            )
    );


    public static final Profile SPIRIT_WARDEN = register(
            new Profile(
                    Identifier.of(ProjectZeroLimit.MOD_ID, "spirit_warden"),
                    Text.translatable("profile.project_zero_limit.spirit_warden.name"),
                    Text.translatable("profile.project_zero_limit.spirit_warden.description"),
                    ModAbilities.TEST_ABILITY,//Spirit Pulse Edge
                    ModAbilities.TEST_ABILITY,//Blood Pact Reversal
                    ModAbilities.TEST_ABILITY,//Mirror Seal Cage
                    ModAbilities.TEST_ABILITY//Ancestral Glyph Drive
            )
    );


    public static final Profile RIFT_BREAKER = register(
            new Profile(
                    Identifier.of(ProjectZeroLimit.MOD_ID, "rift_breaker"),
                    Text.translatable("profile.project_zero_limit.rift_breaker.name"),
                    Text.translatable("profile.project_zero_limit.rift_breaker.description"),
                    ModAbilities.TEST_ABILITY,//Twilight Sweep
                    ModAbilities.TEST_ABILITY,//Ghostline Strikes
                    ModAbilities.TEST_ABILITY,//Limit Fracture
                    ModAbilities.TEST_ABILITY//Ego Rift
            )
    );

    /**
     * Registers a profile to the internal map.
     * Enforces a maximum of {@link #MAX_PROFILES} profiles.
     * @param profile The profile to register.
     * @param <T> The type of the profile.
     * @return The registered profile.
     * @throws IllegalStateException if the maximum number of profiles has been reached.
     * @throws IllegalArgumentException if an ability with the same ID is already registered.
     */
    private static <T extends Profile> T register(T profile) {
        if (PROFILES.size() >= MAX_PROFILES) {
            throw new IllegalStateException("Cannot register more than " + MAX_PROFILES + " profiles. Attempted to register: " + profile.getId());
        }
        if (PROFILES.containsKey(profile.getId())) {
            throw new IllegalArgumentException("Attempted to register duplicate profile ID: " + profile.getId());
        }
        PROFILES.put(profile.getId(), profile);
        ProjectZeroLimit.LOGGER.info("Registered profile: {}", profile.getId());
        return profile;
    }

    /**
     * Retrieves a profile by its Identifier.
     * @param id The Identifier of the profile.
     * @return The Profile object, or null if not found.
     */
    @Nullable
    public static Profile getProfile(Identifier id) {
        return PROFILES.get(id);
    }

    /**
     * Gets a collection of all registered profiles.
     * @return An unmodifiable collection of all profiles.
     */
    public static Collection<Profile> getAllProfiles() {
        return java.util.Collections.unmodifiableCollection(PROFILES.values());
    }

    /**
     * Initializes and registers all profiles. Call this in your main mod class.
     */
    public static void registerProfiles() {
        ProjectZeroLimit.LOGGER.info("Initializing ModProfiles for " + ProjectZeroLimit.MOD_ID);
    }
}
