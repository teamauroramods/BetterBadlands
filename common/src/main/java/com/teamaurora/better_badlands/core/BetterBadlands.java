package com.teamaurora.better_badlands.core;

import com.teamaurora.better_badlands.core.registry.BetterBadlandsBlocks;
import com.teamaurora.better_badlands.core.registry.BetterBadlandsItems;
import gg.moonflower.pollen.api.platform.Platform;

public class BetterBadlands {
    public static final String MOD_ID = "better_badlands";
    public static final Platform PLATFORM = Platform.builder(MOD_ID)
            .clientInit(BetterBadlands::onClientInit)
            .clientPostInit(BetterBadlands::onClientPostInit)
            .commonInit(BetterBadlands::onCommonInit)
            .commonPostInit(BetterBadlands::onCommonPostInit)
            .dataInit(BetterBadlands::onDataInit)
            .build();

    public static void onClientInit() {
    }

    public static void onClientPostInit(Platform.ModSetupContext ctx) {
    }

    public static void onCommonInit() {
        BetterBadlandsBlocks.BLOCKS.register(BetterBadlands.PLATFORM);
        BetterBadlandsItems.ITEMS.register(BetterBadlands.PLATFORM);
    }

    public static void onCommonPostInit(Platform.ModSetupContext ctx) {
    }

    public static void onDataInit(Platform.DataSetupContext ctx) {
    }
}
