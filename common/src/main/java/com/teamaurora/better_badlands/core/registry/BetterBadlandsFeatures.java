package com.teamaurora.better_badlands.core.registry;

import com.google.common.collect.ImmutableList;
import com.teamaurora.better_badlands.common.world.feature.SaguaroCactusFeature;
import com.teamaurora.better_badlands.core.BetterBadlands;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;


public class BetterBadlandsFeatures {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final PollinatedRegistry<Feature<?>> FEATURES = PollinatedRegistry.create(Registry.FEATURE, BetterBadlands.MOD_ID);

    public static final Supplier<Feature<TreeConfiguration>> SAGUARO_CACTUS = FEATURES.register("saguaro_cactus", () -> new SaguaroCactusFeature(TreeConfiguration.CODEC));

    public static void load(Platform platform) {
        LOGGER.debug("Registered to platform");
        FEATURES.register(platform);
    }

    public static class BlockStates {
        public static final BlockState AIR = Blocks.AIR.defaultBlockState();
    }

    public static class Configs {
       public static final TreeConfiguration EMPTY = (new TreeConfiguration.TreeConfigurationBuilder(
               new SimpleStateProvider(BlockStates.AIR),
               new StraightTrunkPlacer(0, 0, 0),
               new SimpleStateProvider(BlockStates.AIR),
               new BlobFoliagePlacer(UniformInt.of(0, 0), UniformInt.of(0, 0), 0),
               new TwoLayersFeatureSize(0, 0, 0)
       )).ignoreVines().build();
    }

    public static class Configured {

        private static final Logger LOGGER = LogManager.getLogger();
        public static final PollinatedRegistry<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = PollinatedRegistry.create(BuiltinRegistries.CONFIGURED_FEATURE, BetterBadlands.MOD_ID);

        public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> SAGUARO = CONFIGURED_FEATURES.register("saguaro", () -> new ConfiguredFeature<>(SAGUARO_CACTUS.get(), Configs.EMPTY));


        public static void load(Platform platform) {
            LOGGER.debug("Registered to platform");
            CONFIGURED_FEATURES.register(platform);
        }
    }
}
