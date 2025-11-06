package com.cl1ppz12.projectzerolimit.editor;

import foundry.veil.api.client.editor.SingleWindowInspector;
import imgui.ImGui;
import net.minecraft.text.Text;

public class BlackHoleEditor extends SingleWindowInspector {

    public static final Text TITLE = Text.literal("Black Hole editor");

    public static final float[] stepSize = new float[]{1.35f};
    public static final int[] maxIterations = new int[]{200};
    public static final float[] accuracy = new float[]{0.001f};
    public static final float[] shadowStepSize = new float[]{0.09f};
    public static final int[] shadowMaxIterations = new int[]{32};
    public static final float[] noiseStrength = new float[]{1.0f};

    public static final float[] exponentialFactor = new float[]{1.0f};
    public static final float[] anisotropyForward = new float[]{0.5f};
    public static final float[] anisotropyBackward = new float[]{-0.2f};
    public static final float[] lobeWeight = new float[]{0.3f};


    @Override
    protected void renderComponents() {
        if (ImGui.collapsingHeader("Rendering & noise options")) {
            ImGui.dragFloat("Steps size", stepSize, 0.01f, 0.0f, Float.MAX_VALUE);
            ImGui.dragInt("Max iterations", maxIterations, 1f, 0, 10000);
            ImGui.dragFloat("Accuracy", accuracy, 0.001f, 0.0f, Float.MAX_VALUE);
            ImGui.dragFloat("Shadow steps size", shadowStepSize, 0.01f, 0.0f, Float.MAX_VALUE);
            ImGui.dragInt("Max shadow iterations", shadowMaxIterations, 0.01f, 0, 10000);
            ImGui.dragFloat("Noise strength", noiseStrength, 0.01f, 0.0f, Float.MAX_VALUE);
        }

        if (ImGui.collapsingHeader("Volumetric & lighting options")) {
            ImGui.dragFloat("Exponential factor", exponentialFactor, 0.01f, 0.0f, Float.MAX_VALUE);
            ImGui.dragFloat("Anisotropy forward", anisotropyForward, 0.01f, 0.0f, Float.MAX_VALUE);
            ImGui.dragFloat("Anisotropy backward", anisotropyBackward, 0.01f, 0.0f, Float.MAX_VALUE);
            ImGui.dragFloat("Lobe weight", lobeWeight, 0.01f, 0.0f, Float.MAX_VALUE);
        }
    }

    @Override
    public Text getDisplayName() {
        return TITLE;
    }
}
