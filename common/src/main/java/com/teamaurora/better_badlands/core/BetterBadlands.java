package com.teamaurora.better_badlands.core;

import com.teamaurora.better_badlands.core.registry.BetterBadlandsBlocks;
import com.teamaurora.better_badlands.core.registry.BetterBadlandsEffects;
import com.teamaurora.better_badlands.core.registry.BetterBadlandsItems;
import gg.moonflower.pollen.api.config.ConfigManager;
import gg.moonflower.pollen.api.config.PollinatedConfigType;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.client.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;

/**
 * @author ebo2022
 */
public class BetterBadlands {
    public static final String MOD_ID = "better_badlands";
    public static final BetterBadlandsCommonConfig CONFIG = ConfigManager.register(MOD_ID, PollinatedConfigType.COMMON, BetterBadlandsCommonConfig::new);

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
        ctx.enqueueWork(() -> {
            RenderTypeRegistry.register(BetterBadlandsBlocks.SAGUARO_FLOWER.get(), RenderType.cutout());
            RenderTypeRegistry.register(BetterBadlandsBlocks.SAGUARO_SPROUT.get(), RenderType.cutout());
            RenderTypeRegistry.register(BetterBadlandsBlocks.POTTED_SAGUARO_SPROUT.get(), RenderType.cutout());
        });
    }

    public static void onCommonInit() {
        BetterBadlandsItems.ITEMS.register(BetterBadlands.PLATFORM);
        BetterBadlandsBlocks.BLOCKS.register(BetterBadlands.PLATFORM);
        BetterBadlandsEffects.MOB_EFFECTS.register(BetterBadlands.PLATFORM);
        BetterBadlandsEffects.POTIONS.register(BetterBadlands.PLATFORM);
    }

    public static void onCommonPostInit(Platform.ModSetupContext ctx) {
        BetterBadlandsEffects.registerBrewingRecipes();
    }

    public static void onDataInit(Platform.DataSetupContext ctx) {
    }
}
