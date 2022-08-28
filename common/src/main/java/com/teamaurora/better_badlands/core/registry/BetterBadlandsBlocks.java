package com.teamaurora.better_badlands.core.registry;

import com.teamaurora.better_badlands.api.KindlingBlock;
import com.teamaurora.better_badlands.api.KindlingSlabBlock;
import com.teamaurora.better_badlands.api.KindlingStairBlock;
import com.teamaurora.better_badlands.common.block.*;
import com.teamaurora.better_badlands.common.block.trees.SaguaroTreeGrower;
import com.teamaurora.better_badlands.common.item.SaguaroFlowerItem;
import com.teamaurora.better_badlands.common.item.TabInsertBlockItem;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.PollinatedBlockRegistry;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author ebo2022
 */
public class BetterBadlandsBlocks {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final PollinatedBlockRegistry BLOCKS = PollinatedRegistry.createBlock(BetterBadlandsItems.ITEMS);

    public static final Supplier<Block> KINDLING = BLOCKS.registerWithItem("kindling", () -> new KindlingBlock(Properties.KINDLING), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final Supplier<Block> KINDLING_SLAB = BLOCKS.registerWithItem("kindling_slab", () -> new KindlingSlabBlock(Properties.KINDLING), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final Supplier<Block> KINDLING_STAIRS = BLOCKS.registerWithItem("kindling_stairs", () -> new KindlingStairBlock(KINDLING.get().defaultBlockState(), Properties.KINDLING), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));


    /* Terracotta Lamps */

    public static final Supplier<Block> TERRACOTTA_LAMP = BLOCKS.registerWithItem("terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> BLACK_TERRACOTTA_LAMP = BLOCKS.registerWithItem("black_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> RED_TERRACOTTA_LAMP = BLOCKS.registerWithItem("red_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> GREEN_TERRACOTTA_LAMP = BLOCKS.registerWithItem("green_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> BROWN_TERRACOTTA_LAMP = BLOCKS.registerWithItem("brown_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> BLUE_TERRACOTTA_LAMP = BLOCKS.registerWithItem("blue_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> PURPLE_TERRACOTTA_LAMP = BLOCKS.registerWithItem("purple_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> CYAN_TERRACOTTA_LAMP = BLOCKS.registerWithItem("cyan_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> LIGHT_GRAY_TERRACOTTA_LAMP = BLOCKS.registerWithItem("light_gray_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> GRAY_TERRACOTTA_LAMP = BLOCKS.registerWithItem("gray_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> PINK_TERRACOTTA_LAMP = BLOCKS.registerWithItem("pink_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> LIME_TERRACOTTA_LAMP = BLOCKS.registerWithItem("lime_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> YELLOW_TERRACOTTA_LAMP = BLOCKS.registerWithItem("yellow_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> LIGHT_BLUE_TERRACOTTA_LAMP = BLOCKS.registerWithItem("light_blue_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> MAGENTA_TERRACOTTA_LAMP = BLOCKS.registerWithItem("magenta_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> ORANGE_TERRACOTTA_LAMP = BLOCKS.registerWithItem("orange_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final Supplier<Block> WHITE_TERRACOTTA_LAMP = BLOCKS.registerWithItem("white_terracotta_lamp", () -> new TerracottaLampBlock(Properties.TERRACOTTA_LAMP), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));

    public static final Supplier<Block> SAGUARO_CACTUS = BLOCKS.registerWithItem("saguaro_cactus", () -> new SaguaroCactusBlock(Properties.SAGUARO_CACTUS), followItem(Items.CACTUS, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final Supplier<Block> SMALL_SAGUARO_CACTUS = BLOCKS.registerWithItem("small_saguaro_cactus", () -> new SmallSaguaroCactusBlock(Properties.SAGUARO_CACTUS), followItem(Items.CACTUS, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final Supplier<Block> SAGUARO_FLOWER = BLOCKS.registerWithItem("saguaro_flower", () -> new SaguaroFlowerBlock(Properties.SAGUARO_FLOWER), block -> new SaguaroFlowerItem(block, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final Supplier<Block> SAGUARO_SPROUT = BLOCKS.register("saguaro_sprout", () -> new SaguaroSproutBlock(new SaguaroTreeGrower(), Properties.SAGUARO_SPROUT));
    public static final Supplier<Block> POTTED_SAGUARO_SPROUT = BLOCKS.register("potted_saguaro_sprout", createFlowerPot(SAGUARO_SPROUT));

    private static Supplier<Block> createFlowerPot(Supplier<Block> block) {
        return () -> new FlowerPotBlock(block.get(), Properties.POTTED_PLANT);
    }

    private static Function<Block, Item> followItem(Item insertAfter, Item.Properties properties) {
        return object -> new TabInsertBlockItem(insertAfter, object, properties);
    }

    public static void load(Platform platform) {
        LOGGER.debug("Registered to platform");
        BLOCKS.register(platform);
    }

    public static final class Properties {
        public static final BlockBehaviour.Properties KINDLING = BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK).noOcclusion();
        public static final BlockBehaviour.Properties TERRACOTTA_LAMP = BlockBehaviour.Properties.copy(Blocks.TERRACOTTA);
        public static final BlockBehaviour.Properties SAGUARO_CACTUS = BlockBehaviour.Properties.copy(Blocks.CACTUS);
        public static final BlockBehaviour.Properties SAGUARO_FLOWER = BlockBehaviour.Properties.copy(Blocks.OXEYE_DAISY).sound(SoundType.SPORE_BLOSSOM);
        public static final BlockBehaviour.Properties SAGUARO_SPROUT = BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).sound(SoundType.WOOL);
        public static final BlockBehaviour.Properties POTTED_PLANT = BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM);
    }
}
