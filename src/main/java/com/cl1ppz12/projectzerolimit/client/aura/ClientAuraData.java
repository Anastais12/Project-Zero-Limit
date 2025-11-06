package com.cl1ppz12.projectzerolimit.client.aura;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/** Client-copy of aura (synced by packet) */
@Environment(EnvType.CLIENT)
public final class ClientAuraData {
    private static int value = 10_000;
    public static void set(int v) { value = Math.max(0, Math.min(10_000, v)); }
    public static int get()      { return value; }
}