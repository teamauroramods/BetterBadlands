package com.teamaurora.better_badlands.core.fabric;

import com.teamaurora.better_badlands.core.BetterBadlands;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BetterBadlandsFabricDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        BetterBadlands.PLATFORM.dataSetup(dataGenerator);
    }
}
