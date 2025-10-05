package com.cl1ppz12.projectzerolimit.ability;

import net.minecraft.util.Identifier;
import java.util.HashMap;
import java.util.Map;

public class AbilityRegistry {
    private static final Map<Identifier, Ability> ABILITIES = new HashMap<>();

    public static void register(Ability ability) {
        ABILITIES.put(ability.getId(), ability);
    }

    public static Ability get(Identifier id) {
        return ABILITIES.get(id);
    }

    public static void init() {
        register(new TestAbility());

    }

    public static Map<Identifier, Ability> getAllAbilities() {
        return new HashMap<>(ABILITIES);
    }
}