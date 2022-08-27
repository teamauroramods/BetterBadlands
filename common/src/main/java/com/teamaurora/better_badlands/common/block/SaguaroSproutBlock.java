package com.teamaurora.better_badlands.common.block;

import com.teamaurora.better_badlands.core.registry.BetterBadlandsBlocks;
import com.teamaurora.better_badlands.core.registry.BetterBadlandsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author ebo2022, Exoplanetary
 */
public class SaguaroSproutBlock extends SaplingBlock {
    public SaguaroSproutBlock(AbstractTreeGrower tree, Properties properties) {
        super(tree, properties);
    }

    public ItemStack getItem(BlockGetter worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(BetterBadlandsBlocks.SAGUARO_FLOWER.get().asItem());
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        Block below = worldIn.getBlockState(pos.below()).getBlock();
        return below.defaultBlockState().is(BlockTags.SAND);
    }
}
