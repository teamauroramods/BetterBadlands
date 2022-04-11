package com.teamaurora.better_badlands.common.block.trees;

import com.teamaurora.better_badlands.core.registry.BetterBadlandsFeatures;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SaguaroTreeGrower extends AbstractTreeGrower {
    @Override
    @Nullable
    protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(@Nullable Random randomIn, boolean largeHive) {
        return BetterBadlandsFeatures.Configured.SAGUARO.get();
    }
}