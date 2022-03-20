package com.teamaurora.better_badlands.core.registry;

import com.teamaurora.better_badlands.common.item.SaguaroFlowerItem;
import com.teamaurora.better_badlands.core.BetterBadlands;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * @author ebo2022
 */
public class BetterBadlandsItems {
    public static final PollinatedRegistry<Item> ITEMS = PollinatedRegistry.create(Registry.ITEM, BetterBadlands.MOD_ID);

    public static final Supplier<Item> SAGUARO_FLOWER = ITEMS.register("saguaro_flower", () -> new SaguaroFlowerItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}
