package com.teamaurora.better_badlands.api;

import com.teamaurora.better_badlands.common.block.thatch.ThatchSlabBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

/**
 * @author ebo2022, Gkoliver, Sarinsa, Exoplanetary
 */
@SuppressWarnings("deprecation")
public class KindlingSlabBlock extends ThatchSlabBlock implements KindlingBehaviour {

    public static final EnumProperty<SlabType> TYPE = SlabBlock.TYPE;
    public static final BooleanProperty WATERLOGGED = SlabBlock.WATERLOGGED;

    public KindlingSlabBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(IS_BURNING, false)
                .setValue(TYPE, SlabType.BOTTOM)
                .setValue(WATERLOGGED, false));
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
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
        this.kindlingAnimationTick(stateIn, worldIn, pos, rand);
        super.animateTick(stateIn, worldIn, pos, rand);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED, IS_BURNING);
    }

    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        this.onPlaceI(state, worldIn, pos);
        super.onPlace(state, worldIn, pos, oldState, isMoving);
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        this.neighborChangedI(state, worldIn, pos);
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        return this.useI(state, worldIn, pos, player, handIn);
    }
}