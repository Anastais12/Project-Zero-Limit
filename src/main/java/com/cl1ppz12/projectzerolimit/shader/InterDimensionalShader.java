package com.cl1ppz12.projectzerolimit.shader;

import net.minecraft.util.Identifier;
import org.ladysnake.satin.api.event.ShaderEffectRenderCallback;
import org.ladysnake.satin.api.managed.ManagedShaderEffect;
import org.ladysnake.satin.api.managed.ShaderEffectManager;

public class InterDimensionalShader {

    private static final ManagedShaderEffect SHADER =
            ShaderEffectManager.getInstance().manage(
                    Identifier.of("minecraft", "shaders/post/interdimensionaltravel.json")
            );

    private static boolean enabled = false;

    public static void init() {
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            if (enabled) {
                SHADER.render(tickDelta);
            }
        });
    }

    public static void enable() {
        enabled = true;
    }

    public static void disable() {
        enabled = false;
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
