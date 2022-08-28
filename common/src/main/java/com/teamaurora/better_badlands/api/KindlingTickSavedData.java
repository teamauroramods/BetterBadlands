package com.teamaurora.better_badlands.api;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * Saved data storage for storing kindling ticker data.
 *
 * @author Sarinsa
 * @since 3.0.0
 */
public class KindlingTickSavedData extends SavedData {

    public static final String KEY_GENERATION = "Generation";
    public static final String KEY_POS = "BlockPosition";
    public static final String KEY_BLOCK = "BlockId";
    public static final String KEY_TICK_TIME = "TickTime";
    private static final String KEY_SCHEDULED = "ScheduledTicks";

    private final KindlingTickerManager.Ticker ticker;

    public KindlingTickSavedData(KindlingTickerManager.Ticker ticker) {
        this.ticker = ticker;
    }

    public KindlingTickSavedData load(CompoundTag compoundTag) {
        if (compoundTag.contains(KEY_SCHEDULED, Tag.TAG_LIST)) {
            ListTag listTag = compoundTag.getList(KEY_SCHEDULED, Tag.TAG_COMPOUND);

            if (listTag.isEmpty())
                return this;

            for (int index = 0; index < listTag.size(); index++) {
                CompoundTag tickData = listTag.getCompound(index);

                int generation = tickData.getInt(KEY_GENERATION);
                int[] coords = tickData.getIntArray(KEY_POS);

                // Invalid block position
                if (coords.length != 3) {
                    continue;
                }
                BlockPos pos = new BlockPos(coords[0], coords[1], coords[2]);

                ResourceLocation blockId = ResourceLocation.tryParse(tickData.getString(KEY_BLOCK));

                // Invalid block ID
                if (blockId == null || !Registry.BLOCK.containsKey(blockId)) {
                    continue;
                }
                Block block = Registry.BLOCK.get(blockId);
                long tickTime = tickData.getLong(KEY_TICK_TIME);

                this.ticker.newTick(generation, pos, block, tickTime);
            }
        }
        return this;
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        ListTag listTag = new ListTag();

        for (int i = 0; i < this.ticker.scheduledTicks.size(); i++) {
            KindlingTickerManager.TickData tickData = this.ticker.scheduledTicks.get(i);
            BlockPos pos = tickData.pos;
            CompoundTag dataTag = new CompoundTag();

            dataTag.putInt(KEY_GENERATION, tickData.generation);
            dataTag.putIntArray(KEY_POS, new int[] {pos.getX(), pos.getY(), pos.getZ()});
            dataTag.putString(KEY_BLOCK, Registry.BLOCK.getKey(tickData.block).toString());
            dataTag.putLong(KEY_TICK_TIME, tickData.tickTime);

            listTag.add(i, dataTag);
        }
        compoundTag.put(KEY_SCHEDULED, listTag);
        return compoundTag;
    }
}