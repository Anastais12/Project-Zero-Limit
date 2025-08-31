package com.cl1ppz12.projectzerolimit.dimension;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

/**
 * Handles the registration keys for custom dimensions.
 * These keys correspond to your JSON-defined dimension, dimension type, and dimension options.
 * The actual runtime creation of the ServerWorld instance is handled by DimensionTypeRegistrar.
 */
public class ModDimensions {


    public static final RegistryKey<World> SHADOW_REALM_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, Identifier.of(ProjectZeroLimit.MOD_ID, "shadow_realm"));
    public static final RegistryKey<DimensionType> SHADOW_REALM_DIMENSION_TYPE_KEY = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, Identifier.of(ProjectZeroLimit.MOD_ID, "shadow_realm_type"));
    public static final RegistryKey<DimensionOptions> SHADOW_REALM_DIMENSION_OPTIONS_KEY = RegistryKey.of(RegistryKeys.DIMENSION, Identifier.of(ProjectZeroLimit.MOD_ID, "shadow_realm"));


    /**
     * Initializes and registers dimension-related keys.
     * This method simply ensures the static fields are initialized.
     * The dimension type and options are expected to be defined via data packs (JSON files).
     */
    public static void registerDimensions() {
        ProjectZeroLimit.LOGGER.info("Registering custom dimension keys for " + ProjectZeroLimit.MOD_ID);
    }
}
