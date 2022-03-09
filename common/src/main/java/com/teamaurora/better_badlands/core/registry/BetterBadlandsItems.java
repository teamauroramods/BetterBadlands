package com.teamaurora.better_badlands.core.registry;

import com.teamaurora.better_badlands.core.BetterBadlands;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public class BetterBadlandsItems {
    public static final PollinatedRegistry<Item> ITEMS = PollinatedRegistry.create(Registry.ITEM, BetterBadlands.MOD_ID);
}
