package com.teamaurora.better_badlands.core.registry;

import com.teamaurora.better_badlands.common.block.*;
import com.teamaurora.better_badlands.core.BetterBadlands;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.grower.SpruceTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Supplier;

/**
 * @author ebo2022
 */
public class BetterBadlandsBlocks {
    public static final PollinatedRegistry<Block> BLOCKS = PollinatedRegistry.create(Registry.BLOCK, BetterBadlands.MOD_ID);

    public static final Supplier<Block> KINDLING = registerBlock("kindling", () -> new KindlingBlock(Properties.KINDLING), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final Supplier<Block> KINDLING_SLAB = registerBlock("kindling_slab", () -> new KindlingSlabBlock(Properties.KINDLING), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final Supplier<Block> KINDLING_STAIRS = registerBlock("kindling_stairs", () -> new KindlingStairBlock(KINDLING.get().defaultBlockState(), Properties.KINDLING), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));


    public static final Supplier<Block> INVISIBLE_LIGHT_SOURCE = registerBlockNoItem("invisible_light_source", () -> new InvisibleLightBlock(Properties.INVISIBLE_LIGHT_SOURCE));

    public static final Supplier<Block> TERRACOTTA_LAMP = registerBlock("terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> BLACK_TERRACOTTA_LAMP = registerBlock("black_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> RED_TERRACOTTA_LAMP = registerBlock("red_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> GREEN_TERRACOTTA_LAMP = registerBlock("green_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> BROWN_TERRACOTTA_LAMP = registerBlock("brown_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> BLUE_TERRACOTTA_LAMP = registerBlock("blue_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> PURPLE_TERRACOTTA_LAMP = registerBlock("purple_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> CYAN_TERRACOTTA_LAMP = registerBlock("cyan_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> LIGHT_GRAY_TERRACOTTA_LAMP = registerBlock("light_gray_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> GRAY_TERRACOTTA_LAMP = registerBlock("gray_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> PINK_TERRACOTTA_LAMP = registerBlock("pink_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> LIME_TERRACOTTA_LAMP = registerBlock("lime_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> YELLOW_TERRACOTTA_LAMP = registerBlock("yellow_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> LIGHT_BLUE_TERRACOTTA_LAMP = registerBlock("light_blue_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> MAGENTA_TERRACOTTA_LAMP = registerBlock("magenta_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> ORANGE_TERRACOTTA_LAMP = registerBlock("orange_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> WHITE_TERRACOTTA_LAMP = registerBlock("white_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));

    public static final Supplier<Block> SAGUARO_CACTUS = registerBlock("saguaro_cactus", () -> new SaguaroCactusBlock(Properties.SAGUARO_CACTUS), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> SMALL_SAGUARO_CACTUS = registerBlock("small_saguaro_cactus", () -> new SmallSaguaroCactusBlock(Properties.SAGUARO_CACTUS), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> SAGUARO_FLOWER = registerBlockNoItem("saguaro_flower", () -> new SaguaroFlowerBlock(Properties.SAGUARO_FLOWER));
    public static final Supplier<Block> SAGUARO_SPROUT = registerBlockNoItem("saguaro_sprout", () -> new SaguaroSproutBlock(new OakTreeGrower(), Properties.SAGUARO_SPROUT));

    private static Supplier<Block> registerBlock(String id, Supplier<Block> block, Item.Properties properties) {
        Supplier<Block> register = BLOCKS.register(id, block);
        BetterBadlandsItems.ITEMS.register(id, () -> new BlockItem(register.get(), properties));
        return register;
    }

    private static Supplier<Block> registerBlockNoItem(String id, Supplier<Block> block) {
        Supplier<Block> register = BLOCKS.register(id, block);
        return register;
    }

    public static final class Properties {
        public static final BlockBehaviour.Properties INVISIBLE_LIGHT_SOURCE = BlockBehaviour.Properties.of(Material.AIR).lightLevel(l -> 13);
        public static final BlockBehaviour.Properties KINDLING = BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK).noOcclusion();
        public static final BlockBehaviour.Properties TERRACOTTA_LAMP = BlockBehaviour.Properties.copy(Blocks.TERRACOTTA);
        public static final BlockBehaviour.Properties SAGUARO_CACTUS = BlockBehaviour.Properties.copy(Blocks.CACTUS);
        public static final BlockBehaviour.Properties SAGUARO_FLOWER = BlockBehaviour.Properties.copy(Blocks.OXEYE_DAISY).sound(SoundType.SPORE_BLOSSOM);
        public static final BlockBehaviour.Properties SAGUARO_SPROUT = BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).sound(SoundType.WOOL);
    }
}
