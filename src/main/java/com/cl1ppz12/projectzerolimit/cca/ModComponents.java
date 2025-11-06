package com.cl1ppz12.projectzerolimit.cca;

import com.cl1ppz12.projectzerolimit.cca.ability.PlayerAbilityComponent;
import com.cl1ppz12.projectzerolimit.cca.ability.PlayerAbilityComponentImpl;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

import static com.cl1ppz12.projectzerolimit.ProjectZeroLimit.MOD_ID;

public final class ModComponents implements EntityComponentInitializer {

    public static final ComponentKey<PlayerAbilityComponent> KEY =
            ComponentRegistry.getOrCreate(Identifier.of(MOD_ID, "abilities"), PlayerAbilityComponent.class);


    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
        entityComponentFactoryRegistry.registerForPlayers(KEY,
                PlayerAbilityComponentImpl::new,
                RespawnCopyStrategy.ALWAYS_COPY);
    }
}