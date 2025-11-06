package com.cl1ppz12.projectzerolimit.advancement;

import com.cl1ppz12.projectzerolimit.ProjectZeroLimit;
import com.cl1ppz12.projectzerolimit.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancements extends FabricAdvancementProvider {
    public ModAdvancements(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    /**
     * This method is called by the data generator to create advancements.
     * @param wrapperLookup A lookup for registry wrappers.
     * @param consumer A consumer to accept the created advancements.
     */
    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup wrapperLookup, Consumer<AdvancementEntry> consumer) {
        // Build and save the root advancement
        AdvancementEntry ZeroLimit = Advancement.Builder.create()
                .display(
                        ModItems.ONI_MASK,
                        Text.literal("Project Zero Limit"),
                        Text.translatable("advancement.project_zero_limit.main_desc"),
                        Identifier.of("project_zero_limit", "textures/gui/background.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("impossible", net.minecraft.advancement.criterion.Criteria.IMPOSSIBLE.create(new net.minecraft.advancement.criterion.ImpossibleCriterion.Conditions()))
                .build(consumer, "project_zero_limit:root");

        // The previous advancement's return value is now used as the parent.
        Advancement.Builder oniMaskAdv = Advancement.Builder.create()
                .parent(ZeroLimit)
                .display(
                        ModItems.ONI_MASK,
                        Text.literal("An Unsettling Presence"),
                        Text.literal("Obtain the Oni Mask"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_oni_mask", InventoryChangedCriterion.Conditions.items(ModItems.ONI_MASK));

        oniMaskAdv.build(consumer, "project_zero_limit:got_oni_mask");
    }
}