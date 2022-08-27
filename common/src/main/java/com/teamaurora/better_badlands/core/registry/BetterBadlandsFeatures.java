package com.teamaurora.better_badlands.core.registry;

import com.google.common.collect.ImmutableList;
import com.teamaurora.better_badlands.common.world.feature.SaguaroCactusFeature;
import com.teamaurora.better_badlands.core.BetterBadlands;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
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
               BlockStateProvider.simple(BlockStates.AIR),
               new StraightTrunkPlacer(0, 0, 0),
               BlockStateProvider.simple(BlockStates.AIR),
               new BlobFoliagePlacer(UniformInt.of(0, 0), UniformInt.of(0, 0), 0),
               new TwoLayersFeatureSize(0, 0, 0)
       )).ignoreVines().build();
    }

    public static class Configured {

        private static final Logger LOGGER = LogManager.getLogger();
        public static final PollinatedRegistry<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = PollinatedRegistry.create(BuiltinRegistries.CONFIGURED_FEATURE, BetterBadlands.MOD_ID);
        public static final PollinatedRegistry<PlacedFeature> PLACEMENTS = PollinatedRegistry.create(BuiltinRegistries.PLACED_FEATURE, BetterBadlands.MOD_ID);

        public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> SAGUARO = CONFIGURED_FEATURES.register("saguaro", () -> new ConfiguredFeature<>(SAGUARO_CACTUS.get(), Configs.EMPTY));
        public static final Supplier<PlacedFeature> SAGUARO_PLACED = PLACEMENTS.register("saguaro", () -> new PlacedFeature(Holder.direct(SAGUARO.get()), VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1F, 1), BetterBadlandsBlocks.SAGUARO_SPROUT.get())));
        public static final ResourceKey<PlacedFeature> SAGUARO_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, BetterBadlands.location("saguaro"));

        public static void load(Platform platform) {
            LOGGER.debug("Registered to platform");
            CONFIGURED_FEATURES.register(platform);
            PLACEMENTS.register(platform);
        }
    }
}
