package com.teamaurora.better_badlands.core.fabric;

import com.teamaurora.better_badlands.core.BetterBadlands;
import com.teamaurora.better_badlands.core.registry.BetterBadlandsFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public class BetterBadlandsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BetterBadlands.PLATFORM.setup();
        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.BADLANDS), GenerationStep.Decoration.VEGETAL_DECORATION, BetterBadlandsFeatures.Configured.SAGUARO_KEY);
    }
}
