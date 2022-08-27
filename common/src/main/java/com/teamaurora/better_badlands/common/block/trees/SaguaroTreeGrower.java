package com.teamaurora.better_badlands.common.block.trees;

import com.teamaurora.better_badlands.core.registry.BetterBadlandsFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SaguaroTreeGrower extends AbstractTreeGrower {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@Nullable Random randomIn, boolean largeHive) {
        return Holder.direct(BetterBadlandsFeatures.Configured.SAGUARO.get());
    }
}