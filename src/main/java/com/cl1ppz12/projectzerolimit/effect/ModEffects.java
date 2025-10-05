package com.cl1ppz12.projectzerolimit.effect;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> COMBAT_MODE = registerStatusEffect("combat_mode",
            new CombatModeEffect(StatusEffectCategory.NEUTRAL, 0x36ebab)
    );

    public static final RegistryEntry<StatusEffect> INTERDIMENSIONAL_TRAVEL = registerStatusEffect("interdimensional_travel",
            new InterDimensionalTravel(StatusEffectCategory.BENEFICIAL, 0x36ebab)
    );


    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(ProjectZeroLimit.MOD_ID, name), statusEffect);
    }

    public static void registerEffects() {
        ProjectZeroLimit.LOGGER.info("Registering Mod Effects for " + ProjectZeroLimit.MOD_ID);
    }
}