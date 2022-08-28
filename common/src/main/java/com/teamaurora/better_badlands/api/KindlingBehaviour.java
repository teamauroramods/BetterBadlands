package com.teamaurora.better_badlands.api;

import com.teamaurora.better_badlands.core.registry.BetterBadlandsParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

/**
 * Shared interface for all Kindling blocks.
 *
 * @author ebo2022, Gkoliver, Sarinsa, Exoplanetary
 */
public interface KindlingBehaviour {

    BooleanProperty IS_BURNING = BooleanProperty.create("is_burning");
    int TO_SCHEDULE = 30;


    /**
     * Schedules the kindling for ignition when hit by a flaming projectile.
     */
    default void onProjectileHitI(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!level.isClientSide) {
            if (projectile.isOnFire()) {
                KindlingTickerManager.INSTANCE.scheduleTick(level, hit.getBlockPos(), state, 0, 0);
            }
        }
    }

    /**
     * Checks for neighbor blocks that are either lava or fire.<br><br>
     *
     * If a neighbor is lava, the kindling is destroyed.<br><br>
     * If a neighbor is fire, the kindling is scheduled for ignition.
     */
    default void onPlaceI(BlockState state, Level level, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.relative(dir);

            if (level.getBlockState(neighborPos).getBlock() == Blocks.LAVA) {
                level.destroyBlock(pos, false);
                break;
            }
            else if (level.getBlockState(neighborPos).getBlock() == Blocks.FIRE) {
                KindlingTickerManager.INSTANCE.scheduleTick(level, pos, state, 0, TO_SCHEDULE);
            }
        }
    }

    default void neighborChangedI(BlockState state, Level level, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            Block neighborBlock = level.getBlockState(pos.relative(dir)).getBlock();

            if (neighborBlock == Blocks.LAVA) {
                level.destroyBlock(pos, false);
                break;
            }
            else if (neighborBlock == Blocks.FIRE) {
                // If kindling is not burning it should be
                // safe to assume it is not currently in a
                // tick chain reaction.
                if (!isBurning(state)) {
                    KindlingTickerManager.INSTANCE.scheduleTick(level, pos, state, 0, TO_SCHEDULE);
                }
            }
        }
    }

    /**
     * Whether the parsed state (should be kindling) is burning or not.<br><br>
     * Used primarily as a marker for animation tick.
     */
    static boolean isBurning(BlockState state) {
        return state.getValue(IS_BURNING);
    }

    /**
     * Cool render effects for when the kindling is burning :O
     */
    default void kindlingAnimationTick(BlockState state, Level worldIn, BlockPos pos, Random rand) {
        if (isBurning(state)) {
            for (int i = 0; i < 20; i++) {
                double x = (double) pos.getX() + rand.nextDouble() + (rand.nextDouble() / 6);
                double y = (double) pos.getY() + rand.nextDouble() + (rand.nextDouble() / 6);
                double z = (double) pos.getZ() + rand.nextDouble() + (rand.nextDouble() / 6);
                worldIn.addParticle(ParticleTypes.FLAME, x, y, z, 0.0, 0.0, 0.0);
            }
            int smokeCount = rand.nextInt(35) + 5;

            for (int i = 0; i < smokeCount; i++) {
                double x = (double) pos.getX() + rand.nextDouble() + (rand.nextDouble() / 6);
                double y = (double) pos.getY() + rand.nextDouble() + (rand.nextDouble() / 6);
                double z = (double) pos.getZ() + rand.nextDouble() + (rand.nextDouble() / 6);
                worldIn.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
            }
            if (rand.nextInt(64) == 0) {
                worldIn.playLocalSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, rand.nextFloat() * 0.25F + 0.75F, rand.nextFloat() + 0.5F, false);
            }
        }
    }

    /**
     * Calculates the amount of ticks to
     * wait for a scheduled kindling tick.
     */
    static int getTickWaitTime(int generation) {
        return (generation * (generation / 5)) / 200;
    }


    /**
     * Handles the ticking logic for kindling.<br><br>
     *
     * Called from {@link KindlingTickerManager.Ticker#tickKindling(ServerLevel, BlockPos, Block, int)}
     */
    static void kindlingTick(BlockState state, ServerLevel level, BlockPos pos, Random rand, int generation) {
        // Mark the kindling as burning if it is not.
        if (!isBurning(state)) {
            level.setBlock(pos, state.setValue(IS_BURNING, true), 3);
        }

        // When the kindling has reached the final generation and should burn up.
        if (generation >= KindlingTickerManager.MAX_GENERATION) {
            VoxelShape shape = state.getShape(level, pos);
            double x = (double)pos.getX() + Math.min(shape.bounds().maxX, rand.nextDouble()) * (double)0.1F;
            double y = (double)pos.getY() + Math.min(shape.bounds().maxY, rand.nextDouble());
            double z = (double)pos.getZ() + Math.min(shape.bounds().maxZ, rand.nextDouble());

            level.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
            level.sendParticles(ParticleTypes.LAVA, x, y, z, rand.nextInt(5) + 1, rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 0);
            level.sendParticles(BetterBadlandsParticles.TWIG.get(), x, y, z, rand.nextInt(10)+5, rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 0);

            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            level.playSound(null, pos, SoundEvents.GENERIC_BURN, SoundSource.PLAYERS, 0.55F, level.random.nextFloat() * 0.4F + 0.8F);

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.offset(direction.getNormal());
                BlockState neighborState = level.getBlockState(neighborPos);

                if (neighborState.getMaterial().isFlammable()) {
                    if(neighborState.getMaterial() == Material.AIR) {
                        level.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
                    }
                    else if (neighborState.getBlock() instanceof TntBlock) {
                        level.setBlockAndUpdate(neighborPos, Blocks.AIR.defaultBlockState());
                    }
                }
            }
            return;
        }
        // Burnin' sound
        level.playSound(null, pos, SoundEvents.FIRE_AMBIENT, SoundSource.PLAYERS, 0.7F, level.random.nextFloat() * 0.4F + 0.8F);

        int spreadGeneration = generation + 1;

        // Look for neighbor fire blocks. If we find one,
        // restart the kindling fire spread cycle from
        // generation 0 to trigger a fresh chain reaction.
        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.offset(dir.getNormal());

            if (level.getBlockState(neighborPos).getBlock() == Blocks.FIRE) {
                spreadGeneration = 0;
                break;
            }
        }
        int tickWaitTime = getTickWaitTime(spreadGeneration);

        // Look for neighbor kindling to ignite or TNT to activate
        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.offset(dir.getNormal());
            BlockState neighborState = level.getBlockState(neighborPos);

            if (neighborState.getBlock() instanceof KindlingBehaviour) {
                // If the neighbor kindling is already burning, do nothing
                if (!isBurning(neighborState)) {
                    KindlingTickerManager.INSTANCE.scheduleTick(level, neighborPos, neighborState, spreadGeneration, TO_SCHEDULE);
                }
            }
            else if (neighborState.getBlock() instanceof TntBlock) {
                level.setBlockAndUpdate(neighborPos, Blocks.AIR.defaultBlockState());
            }
        }
        // Schedule a new tick for this kindling for it to burn up.
        KindlingTickerManager.INSTANCE.scheduleTick(level, pos, state, KindlingTickerManager.MAX_GENERATION, tickWaitTime);
    }

    /**
     * Schedules the kindling for ignition if flint and steel or a fire charge is used on it.
     */
    default InteractionResult useI(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn) {
        Item item = player.getItemInHand(handIn).getItem();

        if (item == Items.FLINT_AND_STEEL || item == Items.FIRE_CHARGE) {
            level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1.0F, level.random.nextFloat() * 0.4F + 0.8F);
            level.setBlockAndUpdate(pos, state.setValue(IS_BURNING, true));
            KindlingTickerManager.INSTANCE.scheduleTick(level, pos, state, 0, TO_SCHEDULE);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }
}
