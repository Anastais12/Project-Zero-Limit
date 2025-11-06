package com.cl1ppz12.projectzerolimit.server;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public final class PlayerDataAttachments {
    public static final AttachmentType<NbtCompound> ABILITIES =
            AttachmentRegistry.<NbtCompound>builder()
                    .initializer(() -> new NbtCompound())
                    .buildAndRegister(Identifier.of("abilities"));
}