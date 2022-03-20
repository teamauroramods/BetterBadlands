package com.teamaurora.better_badlands.common.block;

import com.teamaurora.better_badlands.core.registry.BetterBadlandsParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.ScheduledTick;

import java.util.Random;

/**
 * @author ebo2022, Gkoliver, Sarinsa, Exoplanetary
 */
public interface IKindling {
    IntegerProperty BURN_DISTANCE = IntegerProperty.create("burn_distance", 0, 21);
    BooleanProperty IS_BURNED = BooleanProperty.create("burned");
    int TO_SCHEDULE = 30;
    //public static final int MAX_TIME = getEquation(21*20);

    default void onProjectileHitI(Level worldIn, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!worldIn.isClientSide) {
            Entity entity = projectile.getOwner();
            if (projectile.isOnFire()) {
                BlockPos blockpos = hit.getBlockPos();
                worldIn.setBlockAndUpdate(hit.getBlockPos(), state.setValue(getDistProperty(state),1));
            }
        }
    }

    default void onPlaceI(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        boolean flag = false;
        for (Direction dir : Direction.values()) {
            if (worldIn.getBlockState(pos.offset(dir.getNormal())).getBlock() == Blocks.LAVA) {
                flag = true;
            }

        }
        if (flag) {
            worldIn.destroyBlock(pos, false);
        } else {
            worldIn.scheduleTick(pos, state.getBlock(), TO_SCHEDULE);
        }
        //super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
    }

    default void neighborChangedI(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        boolean flag = false;
        boolean fireFlag = false;
        for (Direction dir : Direction.values()) {
            if (worldIn.getBlockState(pos.offset(dir.getNormal())).getBlock() == Blocks.LAVA) {
                flag = true;
            } else if (worldIn.getBlockState(pos.offset(dir.getNormal())).getBlock() == Blocks.FIRE) {
                fireFlag = true;
            }
        }
        if (flag) {
            worldIn.destroyBlock(pos, false);
        }
        else if (fireFlag) {
            worldIn.setBlockAndUpdate(pos, state.setValue(getDistProperty(state), 1));
        }
        //super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }

    default int getDistFromBlockstate(BlockState state) {
        try {
            return state.getValue(BURN_DISTANCE);
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    default IntegerProperty getDistProperty(BlockState state) {
        return BURN_DISTANCE;
    }

    default void animateTickI(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
        if (getDistFromBlockstate(stateIn)>0) {
            for (int i = 0; i < 20; i++) {
                double d3 = (double) pos.getX() + rand.nextDouble() + (rand.nextDouble()/6);
                double d8 = (double) pos.getY() + rand.nextDouble() + (rand.nextDouble()/6);
                double d13 = (double) pos.getZ() + rand.nextDouble() + (rand.nextDouble()/6);
                worldIn.addParticle(ParticleTypes.FLAME, d3, d8, d13, 0.0, 0.0, 0.0);
            }
            int b = rand.nextInt(35)+5;
            for (int i = 0; i < b; i++) {
                double d3 = (double) pos.getX() + rand.nextDouble() + (rand.nextDouble()/6);
                double d8 = (double) pos.getY() + rand.nextDouble() + (rand.nextDouble()/6);
                double d13 = (double) pos.getZ() + rand.nextDouble() + (rand.nextDouble()/6);
                worldIn.addParticle(ParticleTypes.SMOKE, d3, d8, d13, 0.0, 0.0, 0.0);
            }
            if (rand.nextInt(64) == 0) {
                worldIn.playLocalSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, rand.nextFloat() * 0.25F + 0.75F, rand.nextFloat() + 0.5F, false);
            }
        }

    }
    //Idk what's up with the equation really but it's good to have here
    static int getEquation(int x) {
        int i = 8;
        return (x * (x / 5)) / (25*i);
    }

    default boolean getBurntFromblockstate(BlockState state) {
        try {
            return state.getValue(IS_BURNED);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    default void tickI(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        int dist = getDistFromBlockstate(state);
        /*if (dist > 0) {
            //System.out.println("Got to line 92");
            //worldIn.spawnParticle(ParticleTypes.SMOKE, pos.getX(), pos.getY(), pos.getZ(), rand.nextInt(5)+5, rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 0);
        }*/
        if (getBurntFromblockstate(state)) {
            VoxelShape shapeIn = state.getShape(worldIn, pos);
            double d3 = (double)pos.getX() + Math.min(shapeIn.bounds().maxX, rand.nextDouble()) * (double)0.1F;
            double d8 = (double)pos.getY() + Math.min(shapeIn.bounds().maxY, rand.nextDouble());
            double d13 = (double)pos.getZ() + Math.min(shapeIn.bounds().maxZ, rand.nextDouble());
            worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d3, d8, d13, 0.0D, 0.0D, 0.0D);
            worldIn.sendParticles(ParticleTypes.LAVA, d3, d8, d13, rand.nextInt(5)+1, rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 0);

            worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            worldIn.playSound(null, pos, SoundEvents.GENERIC_BURN, SoundSource.PLAYERS, 1.0F, worldIn.random.nextFloat() * 0.4F + 0.8F);

            for (Direction direction : Direction.values()) {
                BlockState stateo = worldIn.getBlockState(pos.offset(direction.getNormal()));
                if (stateo.getMaterial().isFlammable()) {
                    if(stateo.getMaterial() == Material.AIR) {
                        worldIn.setBlock(pos, (BlockState)stateo.setValue(BlockStateProperties.LIT, true), 11);
                        worldIn.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
                    }
                    else if (stateo.getBlock() instanceof TntBlock) {
                        worldIn.setBlockAndUpdate(pos.offset(direction.getNormal()), Blocks.AIR.defaultBlockState());
                    }
                }
            }
            return;
        }

        if (dist > 0) {
            worldIn.playSound(null, pos, SoundEvents.FIRE_AMBIENT, SoundSource.PLAYERS, 1.0F, worldIn.random.nextFloat() * 0.4F + 0.8F);
            if (dist < 21) {
                for (Direction dir : Direction.values()) {
                    BlockState stateo = worldIn.getBlockState(pos.offset(dir.getNormal()));
                    if (stateo.getBlock() instanceof IKindling) {
                        if (getDistFromBlockstate(stateo) == 0) {
                            worldIn.setBlockAndUpdate(pos.offset(dir.getNormal()), stateo.setValue(getDistProperty(stateo), dist + 1));
                            worldIn.scheduleTick(pos.offset(dir.getNormal()), stateo.getBlock(), TO_SCHEDULE);
                        }
                    } else if (stateo.getBlock() instanceof TntBlock) {
                        worldIn.setBlock(pos, (BlockState)stateo.setValue(BlockStateProperties.LIT, true), 11);
                        worldIn.setBlockAndUpdate(pos.offset(dir.getNormal()), Blocks.AIR.defaultBlockState());
                    }
                }
            }
            worldIn.setBlockAndUpdate(pos, state.setValue(IS_BURNED, true));
            worldIn.scheduleTick(pos, state.getBlock(), getEquation(dist));
            /*if (age >= (dist - 1) * 6) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            } else {
                worldIn.setBlockState(pos, state.with(getAgeProperty(state), age));
            }*/
        } else {
            boolean fireFlag = false;
            for (Direction dir : Direction.values()) {
                if (worldIn.getBlockState(pos.offset(dir.getNormal())).getBlock() == Blocks.FIRE) {
                    fireFlag = true;
                }
            }
            if (fireFlag) {
                worldIn.setBlockAndUpdate(pos, state.setValue(getDistProperty(state), 1));
            }
        }
    }

    default InteractionResult useI(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        Item item = player.getItemInHand(handIn).getItem();
        if (item == Items.FLINT_AND_STEEL || item == Items.FIRE_CHARGE) {
            worldIn.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1.0F, worldIn.random.nextFloat() * 0.4F + 0.8F);
            worldIn.setBlockAndUpdate(pos, state.setValue(getDistProperty(state),1));
            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }
        return InteractionResult.PASS;
    }
}
