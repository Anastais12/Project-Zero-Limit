package com.cl1ppz12.projectzerolimit.ability;

import com.cl1ppz12.projectzerolimit.ability.custom.FireballAbility;
import com.cl1ppz12.projectzerolimit.ability.custom.oni_arts.ChainsOfTheJudge;
import com.cl1ppz12.projectzerolimit.ability.custom.oni_arts.ShadowOfTheLivingWorld;
import com.cl1ppz12.projectzerolimit.ability.custom.oni_protocols.AbyssHeartbeat;
import com.cl1ppz12.projectzerolimit.ability.custom.oni_protocols.InterdimensionalTravel;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;



public final class AbilityRegistry {

    public static final Map<Identifier, Ability> ENTRIES = new ConcurrentHashMap<>();
    public static final List<Ability> INDEXED = new ArrayList<>();

    public static void register(Ability ability) {
        if (ENTRIES.putIfAbsent(ability.id(), ability) != null)
            throw new IllegalArgumentException("Duplicate ability " + ability.id());
        INDEXED.add(ability);
    }

    public static @Nullable Ability get(Identifier id) {
        return ENTRIES.get(id);
    }

    public static Ability get(int index) {
        return INDEXED.size() > index ? INDEXED.get(index) : null;
    }

    public static int size() {
        return INDEXED.size();
    }



    /* ---------- BUILT-IN ---------- */
    public static void bootstrap() {
        register(new FireballAbility("fireball", Text.translatable("ability.projectzerolimit.fireball"), Text.translatable("ability.projectzerolimit.fireball.desc"), Identifier.of("project_zero_limit","textures/ability/fireball.png"), Ability.AbilityType.NULL, 6.0f, 800, 40));
        register(new InterdimensionalTravel("interdimensional_travel", Text.translatable("ability.projectzerolimit.interdimensional_travel"), Text.translatable("ability.projectzerolimit.interdimensional_travel.desc"), Identifier.of("project_zero_limit","textures/ability/interdimensional_travel.png"), Ability.AbilityType.ONI_PROTOCOL, 6.0f, 800, 40));
        register(new AbyssHeartbeat("abyss_heartbeat", Text.translatable("ability.projectzerolimit.abyss_heartbeat"), Text.translatable("ability.projectzerolimit.abyss_heartbeat.desc"), Identifier.of("project_zero_limit","textures/ability/abyss_heartbeat.png"), Ability.AbilityType.ONI_PROTOCOL, 6.0f, 800, 40));
        register(new ShadowOfTheLivingWorld("shadow_of_the_living_world", Text.translatable("ability.projectzerolimit.shadow_of_the_living_world"), Text.translatable("ability.projectzerolimit.shadow_of_the_living_world.desc"), Identifier.of("project_zero_limit","textures/ability/shadow_of_the_living_world.png"), Ability.AbilityType.ONI_ART, 6.0f, 800, 40));
        register(new ChainsOfTheJudge("chains_of_the_judge", Text.translatable("ability.projectzerolimit.chains_of_the_judge"), Text.translatable("ability.projectzerolimit.chains_of_the_judge.desc"), Identifier.of("project_zero_limit","textures/ability/chains_of_the_judge.png"), Ability.AbilityType.ONI_ART, 6.0f, 800, 40));
    }
}