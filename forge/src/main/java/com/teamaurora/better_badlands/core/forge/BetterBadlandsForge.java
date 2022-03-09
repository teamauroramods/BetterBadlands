package com.teamaurora.better_badlands.core.forge;

import com.teamaurora.better_badlands.core.BetterBadlands;
import net.minecraftforge.fml.common.Mod;

@Mod(BetterBadlands.MOD_ID)
public class BetterBadlandsForge {
    public BetterBadlandsForge() {
        BetterBadlands.PLATFORM.setup();
    }
}
