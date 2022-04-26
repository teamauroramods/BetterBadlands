package com.teamaurora.better_badlands.core;
import gg.moonflower.pollen.api.config.PollinatedConfigBuilder;
import gg.moonflower.pollen.api.config.PollinatedConfigBuilder.ConfigValue;


public class BetterBadlandsCommonConfig {
    public final ConfigValue<Integer> maxSpreadCap;

    protected BetterBadlandsCommonConfig(PollinatedConfigBuilder builder) {
        builder.push("Kindling");
        {
            this.maxSpreadCap = builder.comment("Defines how far burning Kindling can spread.").define("Max Spread Distance", 25);
        }
        builder.pop();
    }
}
