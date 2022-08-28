package com.teamaurora.better_badlands.core.forge;

import com.teamaurora.better_badlands.api.KindlingTickerManager;
import com.teamaurora.better_badlands.core.BetterBadlands;
import net.minecraft.server.level.ServerLevel;
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
}
