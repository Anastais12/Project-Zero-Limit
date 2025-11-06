package com.cl1ppz12.projectzerolimit.mixin;

import com.cl1ppz12.projectzerolimit.client.hud.AbilityBarRender;
import net.minecraft.client.Mouse;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseScrollMixin {

    @Inject(method = "onMouseScroll", at = @At(value = "HEAD"), cancellable = true)
    private void projectzerolimit_onMouseScroll(long window, double horizontalScroll, double verticalScroll, CallbackInfo ci) {
        // Only run this logic if the mouse scroll event is from the main game window
        if (window != GLFW.glfwGetCurrentContext()) return;

        // Check if the custom ability bar is currently open and animating
        if (AbilityBarRender.isOpen()) {
            // Pass the scroll delta to the ability bar render class
            AbilityBarRender.scrollSelection(verticalScroll);

            // Cancel the original event so that the vanilla hotbar or other scrollable UIs don't react
            ci.cancel();
        }
    }
}
