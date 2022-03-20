package com.teamaurora.better_badlands.common.block;

import com.teamaurora.better_badlands.core.registry.BetterBadlandsBlocks;
import com.teamaurora.better_badlands.core.registry.BetterBadlandsEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;


import java.util.Random;

/**
 * @author ebo2022, Exoplanetary
 */
public class SmallSaguaroCactusBlock extends PipeBlock {
    protected static final VoxelShape COLLISION_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);

    public SmallSaguaroCactusBlock(BlockBehaviour.Properties properties) {
        super(0.25F, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(NORTH, false).setValue(SOUTH, false).setValue(EAST, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false));
    }

    public boolean canConnect(Block block) {
        return block.defaultBlockState().is(BlockTags.SAND) || block == this || block == BetterBadlandsBlocks.SAGUARO_CACTUS.get() || block == BetterBadlandsBlocks.SAGUARO_FLOWER.get();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        /*BlockState connected = makeConnections(context.getWorld(), context.getPos());
        Direction placedOnDir = context.getFace().getOpposite();
        if (context.getWorld().getBlockState(context.getPos().offset(placedOnDir)).getBlock() == BetterBadlandsBlocks.SAGUARO_CACTUS.get()) {
            return connected.with(SixWayBlock.FACING_TO_PROPERTY_MAP.get(placedOnDir), true);
        }
        return connected;*/
        BlockState connected = this.defaultBlockState();
        Direction placedOnDir = context.getClickedFace().getOpposite();
        BlockState faceState = context.getLevel().getBlockState(context.getClickedPos().offset(placedOnDir.getNormal()));
        return canConnect(faceState.getBlock()) ? connected.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(placedOnDir), true) : null;
    }

    /*public BlockState makeConnections(IBlockReader blockReader, BlockPos pos) {
        Block block = blockReader.getBlockState(pos.down()).getBlock();
        Block block1 = blockReader.getBlockState(pos.up()).getBlock();
        Block block2 = blockReader.getBlockState(pos.north()).getBlock();
        Block block3 = blockReader.getBlockState(pos.east()).getBlock();
        Block block4 = blockReader.getBlockState(pos.south()).getBlock();
        Block block5 = blockReader.getBlockState(pos.west()).getBlock();
        return this.getDefaultState().with(DOWN, block == this || block.getDefaultState().isSolid()).with(UP, block1 == this).with(NORTH, block2 == this).with(EAST, block3 == this).with(SOUTH, block4 == this).with(WEST, block5 == this);
    }*/

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        if (!worldIn.hasChunksAt(pos, pos)) return; // Forge: prevent growing cactus from loading unloaded chunks with block update
        if (!state.canSurvive(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }

    }

    /**
     * Performs a random tick on a block.
     */

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        BlockState newState = (stateIn.getValue(PROPERTY_BY_DIRECTION.get(facing)) && !this.canConnect(facingState.getBlock())) ? stateIn : stateIn.setValue(PROPERTY_BY_DIRECTION.get(facing), this.canConnect(facingState.getBlock()));
        if (!newState.canSurvive(worldIn, currentPos)) {
            worldIn.scheduleTick(currentPos, this, 1);
            //worldIn.destroyBlock(currentPos, true);
        }

        return newState;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        boolean flag = false;
        for (Direction dir : Direction.values()) {
            if (dir != Direction.UP && state.getValue(PipeBlock.PROPERTY_BY_DIRECTION.get(dir))) {
                Block block = worldIn.getBlockState(pos.offset(dir.getNormal())).getBlock();
                if (canConnect(block)) flag = true;
            }
        }
        return flag;
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        entityIn.hurt(DamageSource.CACTUS, 1.0F);
        if (entityIn instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entityIn;
            living.addEffect(new MobEffectInstance(BetterBadlandsEffects.SUCCUMBING.get(), 1200, 0, false, true, true));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN));
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
        return false;
    }
}
