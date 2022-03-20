package com.teamaurora.better_badlands.core;

import com.teamaurora.better_badlands.core.registry.BetterBadlandsBlocks;
import com.teamaurora.better_badlands.core.registry.BetterBadlandsEffects;
import com.teamaurora.better_badlands.core.registry.BetterBadlandsItems;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.client.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;

/**
 * @author ebo2022
 */
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
        BetterBadlandsItems.ITEMS.register(BetterBadlands.PLATFORM);
        BetterBadlandsBlocks.BLOCKS.register(BetterBadlands.PLATFORM);
        BetterBadlandsEffects.EFFECTS.register(BetterBadlands.PLATFORM);
        BetterBadlandsEffects.POTIONS.register(BetterBadlands.PLATFORM);
    }

    public static void onCommonPostInit(Platform.ModSetupContext ctx) {
        BetterBadlandsEffects.registerBrewingRecipes();
    }

    public static void onDataInit(Platform.DataSetupContext ctx) {
    }
}
