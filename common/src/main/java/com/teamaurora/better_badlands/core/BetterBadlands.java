package com.teamaurora.better_badlands.core;

import com.teamaurora.better_badlands.api.KindlingTickerManager;
import com.teamaurora.better_badlands.core.registry.*;
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
            .commonPostInit(BetterBadlands::commonPostInit)
            .build();

    public static void onClientInit() {
        BetterBadlandsParticles.setupClient();
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
        BetterBadlandsParticles.load(PLATFORM);
        BetterBadlandsFeatures.load(PLATFORM);
        BetterBadlandsFeatures.Configured.load(PLATFORM);
    }

    public static void commonPostInit(Platform.ModSetupContext ctx) {
        ctx.enqueueWork(KindlingTickerManager.INSTANCE::init);
    }

    public static ResourceLocation location(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
