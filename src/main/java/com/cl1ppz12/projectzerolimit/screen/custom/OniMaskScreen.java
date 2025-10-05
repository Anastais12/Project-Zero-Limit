package com.cl1ppz12.projectzerolimit.screen.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.cl1ppz12.projectzerolimit.ProjectZeroLimit.MOD_ID;

@Environment(EnvType.CLIENT)
public class OniMaskScreen extends HandledScreen<MaskScreenHandler> {

    public OniMaskScreen(MaskScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    private static final Identifier TEXTURE =
            Identifier.of(MOD_ID, "textures/gui/mask.png");

    public OniMaskScreen(MaskScreenHandler handler) {
        super(handler, MinecraftClient.getInstance().player != null ? MinecraftClient.getInstance().player.getInventory() : null, Text.literal("Mask Storage"));
        backgroundWidth  = 176;
        backgroundHeight = 134;
    }

    @Override
    protected void drawBackground(DrawContext ctx,
                                  float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        ctx.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(DrawContext ctx,
                       int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        super.render(ctx, mouseX, mouseY, delta);
        drawMouseoverTooltip(ctx, mouseX, mouseY);
    }
}

