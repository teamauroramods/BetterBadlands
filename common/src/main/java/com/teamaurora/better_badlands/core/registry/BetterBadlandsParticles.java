package com.teamaurora.better_badlands.core.registry;

import com.teamaurora.better_badlands.core.BetterBadlands;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

/**
 * @author ebo2022
 */
public class BetterBadlandsParticles {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final PollinatedRegistry<ParticleType<?>> PARTICLES = PollinatedRegistry.create(Registry.PARTICLE_TYPE, BetterBadlands.MOD_ID);

    public static final Supplier<SimpleParticleType> TWIG = PARTICLES.register("twig", () -> new SimpleParticleType(false));

    public static void load(Platform platform) {
        LOGGER.debug("Registered to platform");
        PARTICLES.register(platform);
    }
}
