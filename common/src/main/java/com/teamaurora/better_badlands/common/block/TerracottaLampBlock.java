package com.teamaurora.better_badlands.common.block;

import com.teamaurora.better_badlands.core.registry.BetterBadlandsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

/**
 * @author ebo2022
 */
@SuppressWarnings("deprecation")
public class TerracottaLampBlock extends Block {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public TerracottaLampBlock(Properties builder) {
        super(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        Direction dir = state.getValue(TerracottaLampBlock.FACING);
        if (worldIn.isEmptyBlock(pos.offset(dir.getNormal()))) {
            worldIn.setBlockAndUpdate(pos.offset(dir.getNormal()), BetterBadlandsBlocks.INVISIBLE_LIGHT_SOURCE.get().defaultBlockState());
        }
        super.onPlace(state, worldIn, pos, oldState, isMoving);
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        Direction dir = state.getValue(TerracottaLampBlock.FACING);
        if (worldIn.isEmptyBlock(pos.offset(dir.getNormal()))) {
            worldIn.setBlockAndUpdate(pos.offset(dir.getNormal()), BetterBadlandsBlocks.INVISIBLE_LIGHT_SOURCE.get().defaultBlockState());
        }
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        Direction dir = state.getValue(TerracottaLampBlock.FACING);
        if (worldIn.getBlockState(pos.offset(dir.getNormal())).getBlock() == BetterBadlandsBlocks.INVISIBLE_LIGHT_SOURCE.get()) {
            if (!InvisibleLightBlock.canSurviveFromDir(worldIn.getBlockState(pos.offset(dir.getNormal())), worldIn, pos.offset(dir.getNormal()), dir)) {
                worldIn.setBlockAndUpdate(pos.offset(dir.getNormal()), Blocks.AIR.defaultBlockState());
            }
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    public static Direction getDir(BlockState state) {
        return state.getValue(TerracottaLampBlock.FACING);
    }
}
