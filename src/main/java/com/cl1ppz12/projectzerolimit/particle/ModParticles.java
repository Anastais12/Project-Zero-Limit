package com.cl1ppz12.projectzerolimit.particle;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.cl1ppz12.projectzerolimit.ProjectZeroLimit.MOD_ID;

public class ModParticles {

    public static final SimpleParticleType MAGIC_CIRCLE_PURPLE = FabricParticleTypes.simple();

    public static void registerParticles() {
        ProjectZeroLimit.LOGGER.info("Registering Mod Particles for " + MOD_ID);

        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "magic_circle_purple"), MAGIC_CIRCLE_PURPLE);
        ParticleFactoryRegistry.getInstance().register(ModParticles.MAGIC_CIRCLE_PURPLE, EndRodParticle.Factory::new);
    }

    public static void registerClientParticles() {

        ParticleFactoryRegistry.getInstance().register(ModParticles.MAGIC_CIRCLE_PURPLE, EndRodParticle.Factory::new);
    }
}
