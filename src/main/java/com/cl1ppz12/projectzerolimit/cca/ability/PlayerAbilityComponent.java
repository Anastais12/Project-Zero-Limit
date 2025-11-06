package com.cl1ppz12.projectzerolimit.cca.ability;

import com.cl1ppz12.projectzerolimit.ability.Ability;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.ComponentV3;

import java.util.Set;

import static com.cl1ppz12.projectzerolimit.ProjectZeroLimit.MOD_ID;

public interface PlayerAbilityComponent extends ComponentV3 {

    public static final ComponentKey<PlayerAbilityComponent> KEY =
            ComponentRegistry.getOrCreate(Identifier.of(MOD_ID, "abilities"), PlayerAbilityComponent.class);

    /* OWNED ------------------------------------------------------------ */
    Set<Identifier> getOwned();

    void addOwned(Identifier id);

    void setOwned(Set<Identifier> ids);

    /* BOUND SLOTS (0-4) ----------------------------------------------- */
    int[] getBoundSlots();          // -1 = empty

    void setBoundSlot(int slot, int abilityIndex); // -1 to clear

    /* SELECTED CATEGORY (for inventory tab) -------------------------- */
    Ability.AbilityType getSelectedCategory();

    void setSelectedCategory(Ability.AbilityType type);

    /* HELPERS --------------------------------------------------------- */
    default boolean owns(Ability ability) {
        return getOwned().contains(ability.id());
    }

    /* mark dirty → auto-sync */
    void sync();
}