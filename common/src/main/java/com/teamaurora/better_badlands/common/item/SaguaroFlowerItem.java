package com.teamaurora.better_badlands.common.item;

import com.teamaurora.better_badlands.core.registry.BetterBadlandsBlocks;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * @author ebo2022, Exoplanetary
 */
public class SaguaroFlowerItem extends BlockItem {
    public SaguaroFlowerItem(Item.Properties properties) {
        super(BetterBadlandsBlocks.SAGUARO_FLOWER.get(), properties);
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext context) {
        if (context.getClickedFace() != Direction.UP) return null;
        Level world = context.getLevel();
        Block blockDown = world.getBlockState(context.getClickedPos().below()).getBlock();
        BlockState blockstate = null;
        if (blockDown.defaultBlockState().is(BlockTags.SAND)) {
            blockstate = BetterBadlandsBlocks.SAGUARO_SPROUT.get().getStateForPlacement(context);
        } else if (blockDown == BetterBadlandsBlocks.SAGUARO_CACTUS.get() || blockDown == BetterBadlandsBlocks.SMALL_SAGUARO_CACTUS.get()) {
            blockstate = BetterBadlandsBlocks.SAGUARO_FLOWER.get().getStateForPlacement(context);
        }
        return blockstate != null && this.canPlace(context, blockstate) ? blockstate : null;
    }
}
