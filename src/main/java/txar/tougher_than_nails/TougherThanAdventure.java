package txar.tougher_than_nails;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockPlanksPainted;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFoodStackable;
import net.minecraft.core.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.helper.RecipeHelper;
import turniplabs.halplibe.util.ConfigHandler;
import txar.tougher_than_nails.blocks.BlockStrippedLog;
import txar.tougher_than_nails.items.*;
import txar.tougher_than_nails.mixins.TougherCraftingManagerAccessor;
import useless.dragonfly.debug.block.BlockModel;
import useless.dragonfly.helper.ModelHelper;
import useless.dragonfly.model.block.BlockModelDragonFly;

import java.util.Properties;


public class TougherThanAdventure implements ModInitializer {
    public static final String MOD_ID = "tougher_than_nails";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ConfigHandler config;
	static {
		Properties prop = new Properties();
		prop.setProperty("starting_block_id", "4700");
		prop.setProperty("starting_item_id", "4500");
		config = new ConfigHandler(MOD_ID, prop);
		config.updateConfig();
	}

	public static Item itemFirePlough;
	public static Item itemFlintTool;
	public static Item itemGrassFiber;
	public static Item itemSmallWaterskinEmpty;
	public static Item itemSmallWaterskinFull;
	public static Item itemSmallWaterskinFullDirty;
	public static Item itemEnergyConsumer;
	public static Item itemBlueberry;
	public static Item itemBark;
	public static Item itemResin;
	public static Item itemStickFrame;
	public static Item itemFilter;
	public static Item itemDrinkingBowl;

	//public static Block blockWaterFilter;
	public static Block blockStrippedLog;
	public static Block blockCampfire;

    @Override
    public void onInitialize() {
		int itemNum = config.getInt("starting_item_id");
		int blockNum = config.getInt("starting_block_id");
//		int itemNum = 4500;
//		int blockNum = 4700;

		System.out.println(itemNum);

		itemFlintTool = ItemHelper.createItem(MOD_ID, new ToolFlint("Flint Tool", itemNum++, 16, 2), "flint_tool", "flint_tool.png");
		itemFlintTool.setContainerItem(itemFlintTool);

		itemFirePlough = ItemHelper.createItem(MOD_ID, new ToolFireStarter("Fire Plough", itemNum++, 32, 0.01f), "fire_plough", "fire_plough.png");
		itemGrassFiber = ItemHelper.createItem(MOD_ID, new Item("Grass Fiber", itemNum++), "grass_fiber", "grass_fiber.png");
		itemSmallWaterskinEmpty = ItemHelper.createItem(MOD_ID, new ItemWaterskinEmpty("Empty Small Waterskin", itemNum++), "item_empty_small_waterskin", "small_waterskin_empty.png");
		itemSmallWaterskinFull = ItemHelper.createItem(MOD_ID, new ItemWaterskinFull("Full Small Waterskin", itemNum++, 2, 1), "item_full_small_waterskin", "small_waterskin.png");
		itemEnergyConsumer = ItemHelper.createItem(MOD_ID, new ItemEnergyConsumer("Testing Energy Consumer", itemNum++, 2, false), "item_energy_consumer", "fire_plough.png");
		itemBlueberry = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("Blueberry", itemNum++, 2, false, 3), "item_blueberry", "blueberry.png");
		itemSmallWaterskinFullDirty = ItemHelper.createItem(MOD_ID, new ItemWaterskinFullDirty("Full Small Waterskin", itemNum++, 2, 1, 1), "item_full_small_waterskin_dirty", "small_waterskin.png");
		itemBark = ItemHelper.createItem(MOD_ID, new Item("Bark", itemNum++), "item_bark", "bark.png");
		itemResin = ItemHelper.createItem(MOD_ID, new Item("Resin", itemNum++), "item_resin", "resin.png");
		itemStickFrame = ItemHelper.createItem(MOD_ID, new Item("Stick Frame", itemNum++), 	"item_stick_frame", "stick_frame.png");
		itemFilter = ItemHelper.createItem(MOD_ID, new Item("Filter", itemNum++), "item_filter", "filter.png");
		itemDrinkingBowl = ItemHelper.createItem(MOD_ID, new ItemDrinkingBowl("Drinking Bowl", itemNum++, 10, 64, 1), "item_drinking_bowl", "drinking_bowl.png");

		/*
		blockWaterFilter = new BlockBuilder(MOD_ID)
			.setTopTexture("water_filter_top.png")
			.setBottomTexture("water_filter_bottom.png")
			.setSideTextures("water_filter_side.png")
			.build(new Block("water_filter", blockNum++, Material.cloth));\
		*/

		blockStrippedLog = new BlockBuilder(MOD_ID)
			.setTopTexture("stripped_log_top.png")
			.setBottomTexture("stripped_log_top.png")
			.setSideTextures("stripped_log_side.png")
			.build(new BlockStrippedLog("stripped_wooden_log", blockNum++)
					.withHardness(2.0F)
					.withDisabledNeighborNotifyOnMetadataChange()
					.withTags(BlockTags.FENCES_CONNECT, BlockTags.MINEABLE_BY_AXE)
				);

		blockCampfire = new BlockBuilder(MOD_ID)
			.setBlockModel(new BlockModelDragonFly(ModelHelper.getOrCreateBlockModel(MOD_ID, "campfire.json")))
			.build(new BlockModel("campfire", blockNum++, Material.wood, ModelHelper.getOrCreateBlockModel(MOD_ID, "campfire.json")));



		RecipeHelper.Crafting.removeRecipe(Block.workbench);
		RecipeHelper.Crafting.removeRecipe(Block.planksOak);

		for (int i = 0; i < 16; i++) {
			int metaData = BlockPlanksPainted.getMetadataForColour(i);
			RecipeHelper.removeRecipe(Block.planksOakPainted, metaData);
			RecipeHelper.removeRecipe(Block.planksOakPainted, metaData);
		}

		RecipeHelper.Crafting.createRecipe(itemFlintTool, 1, new Object[]{"AB", "C ", 'A', Item.flint, 'B', itemGrassFiber, 'C', Item.stick});
		RecipeHelper.Crafting.createRecipe(itemFlintTool, 1, new Object[]{"AB", " C", 'A', Item.flint, 'B', itemGrassFiber, 'C', Item.stick});
		RecipeHelper.Crafting.createRecipe(itemFirePlough, 1, new Object[]{"##", '#', Item.stick});

		RecipeHelper.Crafting.createShapelessRecipe(new ItemStack(Item.stick, 1), new Object[]{Block.saplingBirch});
		RecipeHelper.Crafting.createShapelessRecipe(new ItemStack(Item.stick, 1), new Object[]{Block.saplingCherry});
		RecipeHelper.Crafting.createShapelessRecipe(new ItemStack(Item.stick, 1), new Object[]{Block.saplingOak});
		RecipeHelper.Crafting.createShapelessRecipe(new ItemStack(Item.stick, 1), new Object[]{Block.saplingEucalyptus});
		RecipeHelper.Crafting.createShapelessRecipe(new ItemStack(Item.stick, 1), new Object[]{Block.saplingPine});
		RecipeHelper.Crafting.createShapelessRecipe(new ItemStack(Item.stick, 1), new Object[]{Block.saplingOakRetro});
		RecipeHelper.Crafting.createShapelessRecipe(new ItemStack(Item.stick, 1), new Object[]{Block.saplingShrub});

		RecipeHelper.Crafting.createShapelessRecipe(new ItemStack(itemStickFrame, 1), new Object[]{Item.stick, Item.stick, itemResin});
		RecipeHelper.Crafting.createShapelessRecipe(new ItemStack(itemFilter, 1), new Object[]{itemStickFrame, Item.cloth, Item.cloth, itemResin});
		RecipeHelper.Crafting.createShapelessRecipe(new ItemStack(itemSmallWaterskinEmpty, 1), new Object[]{Item.leather, Item.leather, Item.leather, itemGrassFiber});

		((TougherCraftingManagerAccessor) RecipeHelper.craftingManager).callAddRecipeWithArgs(new ItemStack(itemDrinkingBowl), true, true, false, new Object[]{"A", "B", 'A', itemFlintTool, 'B', itemBark});

		LOGGER.info("TougherThanAdventure initialized! <3");
    }
}
