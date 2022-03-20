package com.teamaurora.better_badlands.common.world.feature;

import com.mojang.serialization.Codec;
import com.teamaurora.better_badlands.common.util.TreeUtil;
import com.teamaurora.better_badlands.core.registry.BetterBadlandsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
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
    public boolean place(FeaturePlaceContext context) {
        int height = context.random().nextInt(3) + 4;
        Direction dir = Direction.from2DDataValue(context.random().nextInt(4));
        boolean back = context.random().nextBoolean();
        if (context.origin().getY() <= 0 || context.origin().getY() + height + 1 > context.level().getHeight()) {
            return false;
        }
        Block downward = context.level().getBlockState(context.origin().below()).getBlock();
        if (!downward.defaultBlockState().is(BlockTags.SAND)) {
            return false;
        }

        for (BlockPos pos : BlockPos.betweenClosed(context.origin().above().offset(dir.getNormal()), BlockPos.of(context.origin().above(height).offset(back ? -1 : 0, dir)))) {
            if (!TreeUtil.isAirOrLeaves(context.level(), pos)) return false;
        }

        for (int i = 0; i <= height; i++) {
            TreeUtil.setForcedState(context.level(), context.origin().above(i), BetterBadlandsBlocks.SAGUARO_CACTUS.get().defaultBlockState());
        }
        TreeUtil.setForcedState(context.level(), context.origin().above(height+1), BetterBadlandsBlocks.SAGUARO_FLOWER.get().defaultBlockState());
        int height1, height2, depth1, depth2;
        depth1 = 1 + context.random().nextInt(height - 3);
        depth2 = 1 + context.random().nextInt(height - 3);
        height1 = randBetween(depth1 + 1, height - 1, context.random());
        height2 = randBetween(depth2 + 1, height - 1, context.random());
        for (int i = depth1; i <= height1; i++) {
            BlockState sag1 = BetterBadlandsBlocks.SMALL_SAGUARO_CACTUS.get().defaultBlockState();
            if (i == depth1) {
                sag1 = sag1.setValue(PipeBlock.UP, true).setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(dir.getOpposite()), true);
            } else if (i == height1) {
                if (context.random().nextBoolean()) {
                    sag1 = sag1.setValue(PipeBlock.DOWN, true).setValue(PipeBlock.UP, true);
                    TreeUtil.setForcedState(context.level(), context.origin().offset(dir.getNormal()).above(i+1), BetterBadlandsBlocks.SAGUARO_FLOWER.get().defaultBlockState());
                } else {
                    sag1 = sag1.setValue(PipeBlock.DOWN, true);
                }
            } else {
                sag1 = sag1.setValue(PipeBlock.UP, true).setValue(PipeBlock.DOWN, true);
            }
            TreeUtil.setForcedState(context.level(), context.origin().offset(dir.getNormal()).above(i), sag1);
        }
        if (back) {
            for (int i = depth2; i <= height2; i++) {
                BlockState sag2 = BetterBadlandsBlocks.SMALL_SAGUARO_CACTUS.get().defaultBlockState();
                if (i == depth2) {
                    sag2 = sag2.setValue(PipeBlock.UP, true).setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(dir), true);
                } else if (i == height2) {
                    if (context.random().nextBoolean()) {
                        sag2 = sag2.setValue(PipeBlock.DOWN, true).setValue(PipeBlock.UP, true);
                        TreeUtil.setForcedState(context.level(), context.origin().offset(dir.getOpposite().getNormal()).above(i+1), BetterBadlandsBlocks.SAGUARO_FLOWER.get().defaultBlockState());
                    } else {
                        sag2 = sag2.setValue(PipeBlock.DOWN, true);
                    }
                } else {
                    sag2 = sag2.setValue(PipeBlock.UP, true).setValue(PipeBlock.DOWN, true);
                }
                TreeUtil.setForcedState(context.level(), context.origin().offset(dir.getOpposite().getNormal()).above(i), sag2);
            }
        }

        return true;
    }
}
