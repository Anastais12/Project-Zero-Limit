package com.cl1ppz12.projectzerolimit.mixin;

import com.cl1ppz12.projectzerolimit.client.hud.AbilityBarRender;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMouseCancel {
    /* cancel vanilla left / right click while ability bar is open */
    @Inject(method = "handleInputEvents", at = @At("HEAD"), cancellable = true)
    private void projectzerolimit$swallowClicks(CallbackInfo ci) {
        if (AbilityBarRender.isOpen()) ci.cancel();
    }
}
