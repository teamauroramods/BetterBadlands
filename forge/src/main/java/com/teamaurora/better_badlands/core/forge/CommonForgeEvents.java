package com.teamaurora.better_badlands.core.forge;

import com.teamaurora.better_badlands.api.KindlingTickerManager;
import com.teamaurora.better_badlands.core.BetterBadlands;
import com.teamaurora.better_badlands.core.registry.BetterBadlandsFeatures;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterBadlands.MOD_ID)
public class CommonForgeEvents {

    @SubscribeEvent
    public static void onEvent(WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerLevel serverLevel) {
            KindlingTickerManager.INSTANCE.createForLevel(serverLevel);
        }
    }

    @SubscribeEvent
    public static void onEvent(BiomeLoadingEvent event) {
        if (matchesKeys(event.getName(), Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.WOODED_BADLANDS))
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(BetterBadlandsFeatures.Configured.SAGUARO_PLACED.get()));
    }

    public static boolean matchesKeys(ResourceLocation location, ResourceKey<?>... keys) {
        for (ResourceKey<?> key : keys)
            if (key.location().equals(location))
                return true;
        return false;
    }
}
