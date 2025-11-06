package com.cl1ppz12.projectzerolimit.entity;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.cl1ppz12.projectzerolimit.entity.custom.BlackHoleEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class ModEntities {

    public static final EntityType<BlackHoleEntity> BLACK_HOLE =
            Registry.register(Registries.ENTITY_TYPE,
                    Identifier.of("project_zero_limit", "black_hole"),
                    FabricEntityTypeBuilder.<BlackHoleEntity>create(SpawnGroup.MISC, BlackHoleEntity::new)
                            .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                            .trackRangeBlocks(64)
                            .build());

    public static void registerModEntities() {

        FabricDefaultAttributeRegistry.register(BLACK_HOLE, BlackHoleEntity.createBlackHoleAttributes());

        ProjectZeroLimit.LOGGER.info("Registering Entities for " + ProjectZeroLimit.MOD_ID);
    }
}
