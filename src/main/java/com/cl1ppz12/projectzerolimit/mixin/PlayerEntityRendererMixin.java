package com.cl1ppz12.projectzerolimit.mixin;

import com.cl1ppz12.projectzerolimit.client.util.PlayerSkinRenderer; // Import your custom skin identifier
import com.cl1ppz12.projectzerolimit.effect.ModEffects; // Import your custom status effects
import com.mojang.authlib.GameProfile; // Import GameProfile for the super constructor (if needed, but we'll remove it)
import net.minecraft.client.MinecraftClient; // Import MinecraftClient to check for local player
import net.minecraft.client.network.AbstractClientPlayerEntity; // The target class for the Mixin
import net.minecraft.client.util.SkinTextures; // Import SkinTextures record
import net.minecraft.entity.player.PlayerEntity; // Base class for AbstractClientPlayerEntity
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos; // For super constructor (if needed)
import net.minecraft.world.World; // For super constructor (if needed)
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin to override the player's skin textures (specifically the main skin)
 * based on the Combat Mode effect. This ensures the custom skin is applied reliably.
 */
@Mixin(AbstractClientPlayerEntity.class)
public abstract class PlayerEntityRendererMixin extends PlayerEntity {
    public PlayerEntityRendererMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }


    @Inject(method = "getSkinTextures", at = @At("RETURN"), cancellable = true)
    private void projectzerolimit_getCustomSkinTextures(CallbackInfoReturnable<SkinTextures> cir) {
        if (this.equals(MinecraftClient.getInstance().player)) {
            if (this.hasStatusEffect(ModEffects.COMBAT_MODE)) {
                SkinTextures originalSkinTextures = cir.getReturnValue();

                cir.setReturnValue(new SkinTextures(
                        PlayerSkinRenderer.COMBAT_SKIN_TEXTURE,
                        originalSkinTextures.textureUrl(),
                        originalSkinTextures.capeTexture(),
                        originalSkinTextures.capeTexture(),
                        originalSkinTextures.model(),
                        originalSkinTextures.secure()
                ));
            }
        }
    }
}
