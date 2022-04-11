package com.teamaurora.better_badlands.core.registry;

import com.teamaurora.better_badlands.common.potion.SuccumbingEffect;
import com.teamaurora.better_badlands.core.BetterBadlands;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;

import java.util.function.Supplier;

public class BetterBadlandsEffects {
    public static final PollinatedRegistry<MobEffect> MOB_EFFECTS = PollinatedRegistry.create(Registry.MOB_EFFECT, BetterBadlands.MOD_ID);
    public static final PollinatedRegistry<Potion> POTIONS = PollinatedRegistry.create(Registry.POTION, BetterBadlands.MOD_ID);

    public static final Supplier<MobEffect> SUCCUMBING = MOB_EFFECTS.register("succumbing", SuccumbingEffect::new);

    public static final Supplier<Potion> SUCCUMBING_NORMAL = POTIONS.register("succumbing", () -> new Potion(new MobEffectInstance(SUCCUMBING.get(), 900)));
    public static final Supplier<Potion> SUCCUMBING_LONG = POTIONS.register("succumbing_long", () -> new Potion(new MobEffectInstance(SUCCUMBING.get(), 1800)));
    public static final Supplier<Potion> SUCCUMBING_STRONG = POTIONS.register("succumbing_strong", () -> new Potion(new MobEffectInstance(SUCCUMBING.get(), 432, 1)));

    public static void registerBrewingRecipes() {
        PotionBrewing.addMix(Potions.AWKWARD, BetterBadlandsItems.SAGUARO_FLOWER.get(), SUCCUMBING_NORMAL.get());
        PotionBrewing.addMix(SUCCUMBING_NORMAL.get(), Items.GLOWSTONE_DUST, SUCCUMBING_STRONG.get());
        PotionBrewing.addMix(SUCCUMBING_NORMAL.get(), Items.REDSTONE, SUCCUMBING_LONG.get());
    }
}
