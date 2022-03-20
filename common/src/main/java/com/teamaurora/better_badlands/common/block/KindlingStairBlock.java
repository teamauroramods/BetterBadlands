package com.teamaurora.better_badlands.common.block;

import com.teamaurora.better_badlands.common.block.thatch.ThatchStairBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

/**
 * @author ebo2022, Gkoliver, Sarinsa, Exoplanetary
 */
@SuppressWarnings("deprecation")
public class KindlingStairBlock extends ThatchStairBlock implements IKindling {

    public static final DirectionProperty FACING = StairBlock.FACING;
    public static final EnumProperty<Half> HALF = StairBlock.HALF;
    public static final EnumProperty<StairsShape> SHAPE = StairBlock.SHAPE;
    public static final BooleanProperty WATERLOGGED = StairBlock.WATERLOGGED;

    public KindlingStairBlock(BlockState state, Properties properties) {
        super(state, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(BURN_DISTANCE, 0).setValue(IS_BURNED, false).setValue(FACING, Direction.NORTH).setValue(HALF, Half.BOTTOM).setValue(SHAPE, StairsShape.STRAIGHT).setValue(WATERLOGGED, false));
    }

    @Override
    public void onProjectileHit(Level worldIn, BlockState state, BlockHitResult hit, Projectile projectile) {
        this.onProjectileHitI(worldIn, state, hit, projectile);
        super.onProjectileHit(worldIn, state, hit, projectile);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BURN_DISTANCE, IS_BURNED, FACING, HALF, SHAPE, WATERLOGGED);
    }

    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
        this.animateTickI(stateIn, worldIn, pos, rand);
        super.animateTick(stateIn, worldIn, pos, rand);
    }

    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        this.onPlaceI(state, worldIn, pos, oldState, isMoving);
        super.onPlace(state, worldIn, pos, oldState, isMoving);
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        this.neighborChangedI(state, worldIn, pos, blockIn, fromPos, isMoving);
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        this.tickI(state, worldIn, pos, rand);
        super.tick(state, worldIn, pos, rand);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        return this.useI(state, worldIn, pos, player, handIn, hit);
    }
}
