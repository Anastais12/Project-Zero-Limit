package com.cl1ppz12.projectzerolimit.editor;

import foundry.veil.api.client.editor.SingleWindowInspector;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class EditorMain extends SingleWindowInspector {

    public static final MutableText TITLE = Text.literal("Shader Editor");



    @Override
    protected void renderComponents() {

    }

    @Override
    public Text getDisplayName() {
        return TITLE;
    }
}
