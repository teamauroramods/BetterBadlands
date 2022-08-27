package com.teamaurora.better_badlands.core.registry;

import com.teamaurora.better_badlands.common.item.SaguaroFlowerItem;
import com.teamaurora.better_badlands.core.BetterBadlands;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

/**
 * @author ebo2022
 */
public class BetterBadlandsItems {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final PollinatedRegistry<Item> ITEMS = PollinatedRegistry.create(Registry.ITEM, BetterBadlands.MOD_ID);

    public static void load(Platform platform) {
        LOGGER.debug("Registered to platform");
        ITEMS.register(platform);
    }
}
