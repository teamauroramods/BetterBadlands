package com.teamaurora.better_badlands.core.fabric;

import com.teamaurora.better_badlands.core.BetterBadlands;
import net.fabricmc.api.ModInitializer;

public class BetterBadlandsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BetterBadlands.PLATFORM.setup();
    }
}
