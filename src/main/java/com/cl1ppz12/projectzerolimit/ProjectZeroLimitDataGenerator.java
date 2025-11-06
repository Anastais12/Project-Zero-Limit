package com.cl1ppz12.projectzerolimit;

import com.cl1ppz12.projectzerolimit.advancement.ModAdvancements;
import com.cl1ppz12.projectzerolimit.tag.ProjectZeroLimitItemTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ProjectZeroLimitDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModAdvancements::new);
		pack.addProvider(ProjectZeroLimitItemTagProvider::new);

	}
}
