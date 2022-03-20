package com.teamaurora.better_badlands.common.block.thatch;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author ebo2022, JustinPlayzz
 */
public class ThatchSlabBlock extends SlabBlock {
    public ThatchSlabBlock(Properties properties) {
        super(properties);
    }

    public float getAmbientOcclusionLightLevel(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 1.0F;
    }

    public boolean isTranslucent(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }
}
