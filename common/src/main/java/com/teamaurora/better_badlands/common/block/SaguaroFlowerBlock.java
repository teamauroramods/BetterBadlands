package com.teamaurora.better_badlands.common.block;

import com.teamaurora.better_badlands.core.registry.BetterBadlandsBlocks;
import com.teamaurora.better_badlands.core.registry.BetterBadlandsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SaguaroFlowerBlock extends BushBlock {
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);

    public SaguaroFlowerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(BetterBadlandsItems.SAGUARO_FLOWER.get());
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        Block below = worldIn.getBlockState(pos.below()).getBlock();
        return below == BetterBadlandsBlocks.SAGUARO_CACTUS.get() || below == BetterBadlandsBlocks.SMALL_SAGUARO_CACTUS.get();
    }
}
