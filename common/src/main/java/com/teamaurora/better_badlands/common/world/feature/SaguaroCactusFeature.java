package com.teamaurora.better_badlands.common.world.feature;

import com.mojang.serialization.Codec;
import com.teamaurora.better_badlands.common.util.TreeUtil;
import com.teamaurora.better_badlands.core.registry.BetterBadlandsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.Random;

public class SaguaroCactusFeature extends Feature<TreeConfiguration> {
    public SaguaroCactusFeature(Codec<TreeConfiguration> config) {
        super(config);
    }

    private int randBetween(int low, int high, Random rand) {
        return low + rand.nextInt(high - low + 1);
    }

    @Override
    public boolean place(FeaturePlaceContext<TreeConfiguration> ctx) {
        int height = ctx.random().nextInt(3) + 4;
        Direction dir = Direction.from2DDataValue(ctx.random().nextInt(4));
        boolean back = ctx.random().nextBoolean();
        if (ctx.origin().getY() <= -64 || ctx.origin().getY() + height + 1 > ctx.level().getHeight()) {
            return false;
        }
        Block downward = ctx.level().getBlockState(ctx.origin().below()).getBlock();
        if (!downward.defaultBlockState().is(BlockTags.SAND)) {
            return false;
        }

        for (BlockPos pos : BlockPos.betweenClosed(ctx.origin().above().relative(dir), ctx.origin().above(height).relative(dir, back ? -1 : 0))) {
            if (!TreeUtil.isAirOrLeaves(ctx.level(), pos)) return false;
        }

        for (int i = 0; i <= height; i++) {
            TreeUtil.setForcedState(ctx.level(), ctx.origin().above(i), BetterBadlandsBlocks.SAGUARO_CACTUS.get().defaultBlockState());
        }
        TreeUtil.setForcedState(ctx.level(), ctx.origin().above(height+1), BetterBadlandsBlocks.SAGUARO_FLOWER.get().defaultBlockState());
        int height1, height2, depth1, depth2;
        depth1 = 1 + ctx.random().nextInt(height - 3);
        depth2 = 1 + ctx.random().nextInt(height - 3);
        height1 = randBetween(depth1 + 1, height - 1, ctx.random());
        height2 = randBetween(depth2 + 1, height - 1, ctx.random());
        for (int i = depth1; i <= height1; i++) {
            BlockState sag1 = BetterBadlandsBlocks.SMALL_SAGUARO_CACTUS.get().defaultBlockState();
            if (i == depth1) {
                sag1 = sag1.setValue(PipeBlock.UP, true).setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(dir.getOpposite()), true);
            } else if (i == height1) {
                if (ctx.random().nextBoolean()) {
                    sag1 = sag1.setValue(PipeBlock.DOWN, true).setValue(PipeBlock.UP, true);
                    TreeUtil.setForcedState(ctx.level(), ctx.origin().relative(dir).above(i+1), BetterBadlandsBlocks.SAGUARO_FLOWER.get().defaultBlockState());
                } else {
                    sag1 = sag1.setValue(PipeBlock.DOWN, true);
                }
            } else {
                sag1 = sag1.setValue(PipeBlock.UP, true).setValue(PipeBlock.DOWN, true);
            }
            TreeUtil.setForcedState(ctx.level(), ctx.origin().relative(dir).above(i), sag1);
        }
        if (back) {
            for (int i = depth2; i <= height2; i++) {
                BlockState sag2 = BetterBadlandsBlocks.SMALL_SAGUARO_CACTUS.get().defaultBlockState();
                if (i == depth2) {
                    sag2 = sag2.setValue(PipeBlock.UP, true).setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(dir), true);
                } else if (i == height2) {
                    if (ctx.random().nextBoolean()) {
                        sag2 = sag2.setValue(PipeBlock.DOWN, true).setValue(PipeBlock.UP, true);
                        TreeUtil.setForcedState(ctx.level(), ctx.origin().relative(dir.getOpposite()).above(i+1), BetterBadlandsBlocks.SAGUARO_FLOWER.get().defaultBlockState());
                    } else {
                        sag2 = sag2.setValue(PipeBlock.DOWN, true);
                    }
                } else {
                    sag2 = sag2.setValue(PipeBlock.UP, true).setValue(PipeBlock.DOWN, true);
                }
                TreeUtil.setForcedState(ctx.level(), ctx.origin().relative(dir.getOpposite()).above(i), sag2);
            }
        }

        return true;
    }
}
