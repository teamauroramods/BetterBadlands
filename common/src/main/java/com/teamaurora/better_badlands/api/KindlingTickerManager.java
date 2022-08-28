package com.teamaurora.better_badlands.api;

import gg.moonflower.pollen.api.event.events.lifecycle.ServerLifecycleEvents;
import gg.moonflower.pollen.api.event.events.lifecycle.TickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A utility class that manages kindling ticking logic.
 *
 * @author Sarinsa
 * @since 3.0.0
 */
public class KindlingTickerManager {

    private static final Logger LOGGER = LogManager.getLogger();

    /** The global instance of the tick manager. */
    public static final KindlingTickerManager INSTANCE = new KindlingTickerManager();

    /** A Map that contains each server level's kindling ticker. */
    private final Map<ServerLevel, Ticker> tickers = new HashMap<>();

    public static final int MIN_GENERATION = 0;
    // TODO - This could perhaps be turned into a config option
    public static final int MAX_GENERATION = 21;

    private KindlingTickerManager() {}

    @ApiStatus.Internal
    public void init() {
        TickEvents.LEVEL_POST.register(this::onLevelTick);
        ServerLifecycleEvents.STOPPING.register(this::onServerStopping);
    }

    public void onLevelTick(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            Ticker ticker = this.tickers.get(serverLevel);
            if (ticker != null) {
                ticker.runScheduledTicks();
                ticker.clearFinishedTicks();
            }
        }
    }

    public void onServerStopping(MinecraftServer server) {
        for (ServerLevel serverLevel : server.getAllLevels()) {
            removeForLevel(serverLevel);
        }
    }

    public void createForLevel(ServerLevel serverLevel) {
        if (this.tickers.containsKey(serverLevel)) {
            LOGGER.error("Failed to create a ticker for this level since one is already present");
        } else {
            this.tickers.put(serverLevel, new Ticker(serverLevel));
        }
    }

    private void removeForLevel(ServerLevel serverLevel) {
        if (this.tickers.containsKey(serverLevel)) {
            this.tickers.get(serverLevel).markDirty();
            this.tickers.remove(serverLevel);
        } else {
            LOGGER.error("No ticker was found for this level during removal");
        }
    }

    /**
     * Schedules a new kindling tick.
     *
     * @param level The Level in which the tick is scheduled to happen. The tick is <strong>canceled</strong> on client side.<br><br>
     * @param generation An integer representing this tick's position in the kindling burning chain reaction.<br><br>
     * @param waitTicks The amount of ticks to wait before running the scheduled tick.
     */
    public void scheduleTick(Level level, BlockPos pos, BlockState state, int generation, int waitTicks) {
        if (level.isClientSide)
            return;

        if (generation < MIN_GENERATION || generation > MAX_GENERATION) {
            LOGGER.error("Invalid generation parameters");
            return;
        }

        if (!(state.getBlock() instanceof KindlingBehaviour)) {
            LOGGER.error("{} is not a valid Kindling block", Registry.BLOCK.getKey(state.getBlock()).toString());
            return;
        }

        ServerLevel serverLevel = (ServerLevel) level;
        if (this.tickers.containsKey(serverLevel)) {
            final long tickTime = serverLevel.getGameTime() + waitTicks;
            this.tickers.get(serverLevel).newTick(generation, pos, state.getBlock(), tickTime);
        }
        LOGGER.info("Scheduled tick at {}", pos.toShortString());
    }

    public static class Ticker {

        protected final KindlingTickSavedData dataStorage;
        protected final List<TickData> scheduledTicks = new ArrayList<>();
        private final ServerLevel level;

        protected Ticker(ServerLevel serverLevel) {
            this.level = serverLevel;
            this.dataStorage = serverLevel.getDataStorage().computeIfAbsent(this::load, this::create, "kindling_tick_data");
        }

        private KindlingTickSavedData load(CompoundTag compoundTag) {
            return this.create().load(compoundTag);
        }

        private KindlingTickSavedData create() {
            return new KindlingTickSavedData(this);
        }

        protected void markDirty() {
            if (this.dataStorage == null) {
                // Oh nO
            }
            else {
                this.dataStorage.setDirty();
            }
        }

        protected void newTick(int generation, BlockPos pos, Block block, long tick) {
            this.scheduledTicks.add(new TickData(generation, pos, block, tick));
        }

        protected void runScheduledTicks() {
            if (this.scheduledTicks.isEmpty())
                return;

            try {
                // TODO - Find out where the concurrency problem occurs.
                //        The ticking logic is seemingly able to operate normally
                //        despite the comodification happening, but it is not ideal.
                for (TickData tickData : this.scheduledTicks) {
                    if (this.level.getGameTime() >= tickData.tickTime) {

                        // Only run the tick if the area is loaded,
                        // if not, save the tick for later.
                        if (this.level.hasChunksAt(tickData.pos.offset(-1, -1, -1), tickData.pos.offset(1, 1, 1))) {
                            this.tickKindling(this.level, tickData.pos, tickData.block, tickData.generation);
                            tickData.ticked = true;
                            LOGGER.info("Ran a tick at {}", tickData.pos.toShortString());
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected void clearFinishedTicks() {
            if (this.scheduledTicks.isEmpty())
                return;

            this.scheduledTicks.removeIf((tickData) -> tickData.ticked);
        }

        /**
         * <strong>Note</strong>: the parsed block is always expected to be a kindling block.
         */
        private void tickKindling(ServerLevel serverLevel, BlockPos pos, Block block, int generation) {
            BlockState state = serverLevel.getBlockState(pos);

            // Make sure we only perform the tick if the block
            // where we are ticking is the same as when the tick
            // was scheduled. There is a tiny chance a player or other
            // external sources changed the block after the tick was scheduled.
            if (state.is(block)) {
                KindlingBehaviour.kindlingTick(state, serverLevel, pos, serverLevel.random, generation);
            }
        }
    }

    protected static class TickData {

        protected final int generation;
        protected final BlockPos pos;
        protected final Block block;
        protected final long tickTime;
        protected boolean ticked;

        protected TickData(int generation, BlockPos pos, Block block, long tickTime) {
            this.generation = generation;
            this.pos = pos;
            this.block = block;
            this.tickTime = tickTime;
            this.ticked = false;
        }
    }
}