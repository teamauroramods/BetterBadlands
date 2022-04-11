package com.teamaurora.better_badlands.core.registry;

import com.google.common.collect.ImmutableList;
import com.teamaurora.better_badlands.common.world.feature.SaguaroCactusFeature;
import com.teamaurora.better_badlands.core.BetterBadlands;
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

import java.util.function.Supplier;


public class BetterBadlandsFeatures {
    public static final PollinatedRegistry<Feature<?>> FEATURES = PollinatedRegistry.create(Registry.FEATURE, BetterBadlands.MOD_ID);
    public static final PollinatedRegistry<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = PollinatedRegistry.create(Registry.TREE_DECORATOR_TYPES, BetterBadlands.MOD_ID);
    public static final PollinatedRegistry<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = PollinatedRegistry.create(BuiltinRegistries.CONFIGURED_FEATURE, BetterBadlands.MOD_ID);

    public static final Supplier<Feature<TreeConfiguration>> SAGUARO_CACTUS = FEATURES.register("saguaro_cactus", () -> new SaguaroCactusFeature(TreeConfiguration.CODEC));

    public static final class BlockStates {
        public static final BlockState AIR = Blocks.AIR.defaultBlockState();
    }

    public static final class Configs {
       public static final TreeConfiguration EMPTY = (new TreeConfiguration.TreeConfigurationBuilder(
               new SimpleStateProvider(BlockStates.AIR),
               new StraightTrunkPlacer(0, 0, 0),
               new SimpleStateProvider(BlockStates.AIR),
               new BlobFoliagePlacer(UniformInt.of(0, 0), UniformInt.of(0, 0), 0),
               new TwoLayersFeatureSize(0, 0, 0)
       )).ignoreVines().build();
    }

    public static final class Configured {
        public static final Supplier<ConfiguredFeature<TreeConfiguration, ?>> SAGUARO = () -> BetterBadlandsFeatures.SAGUARO_CACTUS.get().configured(Configs.EMPTY);

        public static void registerConfiguredFeatures() {
            CONFIGURED_FEATURES.register("saguaro", Configured.SAGUARO);
        }
    }
}
