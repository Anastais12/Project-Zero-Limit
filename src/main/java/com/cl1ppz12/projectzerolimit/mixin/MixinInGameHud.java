package com.cl1ppz12.projectzerolimit.mixin;

import com.cl1ppz12.projectzerolimit.client.hud.AbilityBarRender;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {

    /* cancel vanilla hot-bar render when our ability bar is open */
    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    private void projectzerolimit$hideVanillaHotbar(CallbackInfo ci) {
        if (AbilityBarRender.isOpen()) ci.cancel();
    }
}