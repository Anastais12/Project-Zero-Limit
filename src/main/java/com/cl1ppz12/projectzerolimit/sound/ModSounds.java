package com.cl1ppz12.projectzerolimit.sound;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent ONI_MASK_EQUIP = registerSoundEvent("oni_mask_equip");



    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(ProjectZeroLimit.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        ProjectZeroLimit.LOGGER.info("Registering Mod Sounds for " + ProjectZeroLimit.MOD_ID);
    }
}