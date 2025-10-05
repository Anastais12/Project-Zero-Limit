package com.cl1ppz12.projectzerolimit.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class InterDimensionalTravel extends StatusEffect {
    protected InterDimensionalTravel(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // Return true to apply the effect every tick.
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        // Your custom logic can go here. For advanced invisibility, you might need to use Mixins.
        // For example, you could check for this effect and make armor invisible.
        return true;
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        // Logic to run when the effect is first applied
    }
}
