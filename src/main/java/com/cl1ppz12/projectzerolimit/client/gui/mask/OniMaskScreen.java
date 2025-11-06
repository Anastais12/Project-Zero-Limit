package com.cl1ppz12.projectzerolimit.client.gui.mask;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class OniMaskScreen extends HandledScreen<OniMaskScreenHandler> {

    private static final Identifier BACKGROUND =
            Identifier.of("project_zero_limit", "textures/gui/oni_mask_gui.png");

    public OniMaskScreen(OniMaskScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth  = 256;
        this.backgroundHeight = 256;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(BACKGROUND, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }
}