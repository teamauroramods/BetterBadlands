package com.teamaurora.better_badlands.core;

import com.teamaurora.better_badlands.core.registry.BetterBadlandsBlocks;
import com.teamaurora.better_badlands.core.registry.BetterBadlandsEffects;
import com.teamaurora.better_badlands.core.registry.BetterBadlandsFeatures;
import com.teamaurora.better_badlands.core.registry.BetterBadlandsItems;
import gg.moonflower.pollen.api.config.ConfigManager;
import gg.moonflower.pollen.api.config.PollinatedConfigType;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.client.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

/**
 * @author ebo2022
 */
public class BetterBadlands {
    public static final String MOD_ID = "better_badlands";
    public static final BetterBadlandsCommonConfig CONFIG = ConfigManager.register(MOD_ID, PollinatedConfigType.COMMON, BetterBadlandsCommonConfig::new);
    public static final Platform PLATFORM = Platform.builder(MOD_ID)
            .clientInit(() -> BetterBadlands::onClientInit)
            .clientPostInit(() -> BetterBadlands::onClientPostInit)
            .commonInit(BetterBadlands::onCommonInit)
            .build();

    public static void onClientInit() {
    }

    public static void onClientPostInit(Platform.ModSetupContext ctx) {
        RenderTypeRegistry.register(BetterBadlandsBlocks.SAGUARO_FLOWER.get(), RenderType.cutout());
        RenderTypeRegistry.register(BetterBadlandsBlocks.SAGUARO_SPROUT.get(), RenderType.cutout());
        RenderTypeRegistry.register(BetterBadlandsBlocks.POTTED_SAGUARO_SPROUT.get(), RenderType.cutout());
    }

    public static void onCommonInit() {
        BetterBadlandsBlocks.load(PLATFORM);
        BetterBadlandsItems.load(PLATFORM);
        BetterBadlandsEffects.load(PLATFORM);
        BetterBadlandsFeatures.load(PLATFORM);
        BetterBadlandsFeatures.Configured.load(PLATFORM);
    }

    public static ResourceLocation location(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
