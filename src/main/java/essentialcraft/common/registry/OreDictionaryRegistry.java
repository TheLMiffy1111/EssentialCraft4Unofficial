package essentialcraft.common.registry;

import static essentialcraft.utils.cfg.Config.allowPaleItemsInOtherRecipes;

import essentialcraft.common.block.BlocksCore;
import essentialcraft.common.item.ItemsCore;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryRegistry {

	public static void register() {
		OreDictionary.registerOre("blockDropsFire", new ItemStack(BlocksCore.drops, 1, 0));
		OreDictionary.registerOre("blockDropsWater", new ItemStack(BlocksCore.drops, 1, 1));
		OreDictionary.registerOre("blockDropsEarth", new ItemStack(BlocksCore.drops, 1, 2));
		OreDictionary.registerOre("blockDropsAir", new ItemStack(BlocksCore.drops, 1, 3));
		OreDictionary.registerOre("platingMagic", new ItemStack(BlocksCore.magicPlating, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("glass", new ItemStack(BlocksCore.fortifiedGlass, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("glassFortified", new ItemStack(BlocksCore.fortifiedGlass, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("stone", new ItemStack(BlocksCore.fortifiedStone, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("stoneFortified", new ItemStack(BlocksCore.fortifiedStone, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("iceCompressed", new ItemStack(BlocksCore.coldStone, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockVoid", new ItemStack(BlocksCore.voidStone, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockVoidStone", new ItemStack(BlocksCore.voidStone, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("stoneVoid", new ItemStack(BlocksCore.voidStone, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("glassVoid", new ItemStack(BlocksCore.voidGlass, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockGlassVoid", new ItemStack(BlocksCore.voidGlass, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("voidGlass", new ItemStack(BlocksCore.voidGlass, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockVoidGlass", new ItemStack(BlocksCore.voidGlass, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockConcrete", new ItemStack(BlocksCore.concrete, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("concrete", new ItemStack(BlocksCore.concrete, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("cacti", new ItemStack(BlocksCore.cacti, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("cactus", new ItemStack(BlocksCore.cacti, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockCacti", new ItemStack(BlocksCore.cacti, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockCactus", new ItemStack(BlocksCore.cacti, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("dirt", new ItemStack(BlocksCore.dreadDirt, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockDirt", new ItemStack(BlocksCore.dreadDirt, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("log", new ItemStack(BlocksCore.root, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockLog", new ItemStack(BlocksCore.root, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("fence", new ItemStack(BlocksCore.fence[0], 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("fence", new ItemStack(BlocksCore.fence[1], 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("fence", new ItemStack(BlocksCore.fence[2], 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("platingPale", new ItemStack(BlocksCore.platingPale, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("platingMithriline", new ItemStack(BlocksCore.invertedBlock, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("platingDemonic", new ItemStack(BlocksCore.demonicPlating, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("chest", new ItemStack(BlocksCore.chest, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockChest", new ItemStack(BlocksCore.chest, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("chestMagical", new ItemStack(BlocksCore.chest, 1, 0));
		OreDictionary.registerOre("chestVoid", new ItemStack(BlocksCore.chest, 1, 1));
		OreDictionary.registerOre("rightClicker", new ItemStack(BlocksCore.rightClicker, 1, OreDictionary.WILDCARD_VALUE));

		OreDictionary.registerOre("blockElemental", new ItemStack(BlocksCore.compressed, 1, 4));
		OreDictionary.registerOre("blockFireElemental", new ItemStack(BlocksCore.compressed, 1, 0));
		OreDictionary.registerOre("blockWaterElemental", new ItemStack(BlocksCore.compressed, 1, 1));
		OreDictionary.registerOre("blockEarthElemental", new ItemStack(BlocksCore.compressed, 1, 2));
		OreDictionary.registerOre("blockAirElemental", new ItemStack(BlocksCore.compressed, 1, 3));

		OreDictionary.registerOre("oreFireElemental", new ItemStack(BlocksCore.oreDrops, 1, 1));
		OreDictionary.registerOre("oreWaterElemental", new ItemStack(BlocksCore.oreDrops, 1, 2));
		OreDictionary.registerOre("oreEarthElemental", new ItemStack(BlocksCore.oreDrops, 1, 3));
		OreDictionary.registerOre("oreAirElemental", new ItemStack(BlocksCore.oreDrops, 1, 4));
		OreDictionary.registerOre("oreElemental", new ItemStack(BlocksCore.oreDrops, 1, 0));
		OreDictionary.registerOre("oreFireElemental", new ItemStack(BlocksCore.oreDrops, 1, 6));
		OreDictionary.registerOre("oreWaterElemental", new ItemStack(BlocksCore.oreDrops, 1, 7));
		OreDictionary.registerOre("oreEarthElemental", new ItemStack(BlocksCore.oreDrops, 1, 8));
		OreDictionary.registerOre("oreAirElemental", new ItemStack(BlocksCore.oreDrops, 1, 9));
		OreDictionary.registerOre("oreElemental", new ItemStack(BlocksCore.oreDrops, 1, 5));
		OreDictionary.registerOre("oreFireElemental", new ItemStack(BlocksCore.oreDrops, 1, 11));
		OreDictionary.registerOre("oreWaterElemental", new ItemStack(BlocksCore.oreDrops, 1, 12));
		OreDictionary.registerOre("oreEarthElemental", new ItemStack(BlocksCore.oreDrops, 1, 13));
		OreDictionary.registerOre("oreAirElemental", new ItemStack(BlocksCore.oreDrops, 1, 14));
		OreDictionary.registerOre("oreElemental", new ItemStack(BlocksCore.oreDrops, 1, 10));

		OreDictionary.registerOre("gemFireElemental", new ItemStack(ItemsCore.drops, 1, 0));
		OreDictionary.registerOre("gemWaterElemental", new ItemStack(ItemsCore.drops, 1, 1));
		OreDictionary.registerOre("gemEarthElemental", new ItemStack(ItemsCore.drops, 1, 2));
		OreDictionary.registerOre("gemAirElemental", new ItemStack(ItemsCore.drops, 1, 3));
		OreDictionary.registerOre("gemElemental", new ItemStack(ItemsCore.drops, 1, 4));
		OreDictionary.registerOre("gemCoal", new ItemStack(Items.COAL, 1, 0));
		OreDictionary.registerOre("gemNetherStar", new ItemStack(Items.NETHER_STAR, 1, 0));
		OreDictionary.registerOre("gemEnderPearl", new ItemStack(Items.ENDER_PEARL, 1, 0));
		OreDictionary.registerOre("book", new ItemStack(Items.BOOK, 1, 0));

		OreDictionary.registerOre("pearlPale", new ItemStack(ItemsCore.genericItem, 1, 38));

		if(allowPaleItemsInOtherRecipes) {
			OreDictionary.registerOre("gemEnderPearl", new ItemStack(ItemsCore.genericItem, 1, 38));
			OreDictionary.registerOre("enderPearl", new ItemStack(ItemsCore.genericItem, 1, 38));
			OreDictionary.registerOre("itemEnderPearl", new ItemStack(ItemsCore.genericItem, 1, 38));
			OreDictionary.registerOre("pearlEnder", new ItemStack(ItemsCore.genericItem, 1, 38));
		}

		OreDictionary.registerOre("essentialcraft:gemEnderPearl", new ItemStack(Items.ENDER_PEARL, 1, 0));
		OreDictionary.registerOre("essentialcraft:gemEnderPearl", new ItemStack(ItemsCore.genericItem, 1, 38));

		OreDictionary.registerOre("ingotPale", new ItemStack(ItemsCore.genericItem, 1, 39));

		if(allowPaleItemsInOtherRecipes) {
			OreDictionary.registerOre("ingotGold", new ItemStack(ItemsCore.genericItem, 1, 39));
		}

		OreDictionary.registerOre("essentialcraft:ingotGold", new ItemStack(ItemsCore.genericItem, 1, 39));
		OreDictionary.registerOre("essentialcraft:ingotGold", new ItemStack(Items.GOLD_INGOT));

		OreDictionary.registerOre("enderEye", new ItemStack(Items.ENDER_EYE, 1, 0));
		OreDictionary.registerOre("coreMagic", new ItemStack(ItemsCore.genericItem, 1, 1));
		OreDictionary.registerOre("coreElemental", new ItemStack(ItemsCore.genericItem, 1, 1));
		OreDictionary.registerOre("coreMagic", new ItemStack(ItemsCore.genericItem, 1, 42));
		OreDictionary.registerOre("corePale", new ItemStack(ItemsCore.genericItem, 1, 42));
		OreDictionary.registerOre("coreMagic", new ItemStack(ItemsCore.genericItem, 1, 53));
		OreDictionary.registerOre("coreDemonic", new ItemStack(ItemsCore.genericItem, 1, 53));

		OreDictionary.registerOre("plateDiamond", new ItemStack(ItemsCore.genericItem, 1, 21));
		OreDictionary.registerOre("plateEmerald", new ItemStack(ItemsCore.genericItem, 1, 22));
		OreDictionary.registerOre("frameMagic", new ItemStack(ItemsCore.genericItem, 1, 24));
		OreDictionary.registerOre("frameIron", new ItemStack(ItemsCore.genericItem, 1, 26));
		OreDictionary.registerOre("waterMagic", new ItemStack(ItemsCore.genericItem, 1, 6));
		OreDictionary.registerOre("ingotThaumium", new ItemStack(ItemsCore.genericItem, 1, 5));
		OreDictionary.registerOre("plateThaumium", new ItemStack(ItemsCore.genericItem, 1, 34));
		OreDictionary.registerOre("platePale", new ItemStack(ItemsCore.genericItem, 1, 41));
		OreDictionary.registerOre("plateDemonic", new ItemStack(ItemsCore.genericItem, 1, 54));
		OreDictionary.registerOre("ingotDemonic", new ItemStack(ItemsCore.genericItem, 1, 52));

		OreDictionary.registerOre("plateMagic", new ItemStack(ItemsCore.genericItem, 1, 34));
		OreDictionary.registerOre("plateMagic", new ItemStack(ItemsCore.genericItem, 1, 41));
		OreDictionary.registerOre("plateMagic", new ItemStack(ItemsCore.genericItem, 1, 49));
		OreDictionary.registerOre("plateMagic", new ItemStack(ItemsCore.genericItem, 1, 54));

		OreDictionary.registerOre("alloysMagical", new ItemStack(ItemsCore.genericItem, 1, 0));
		OreDictionary.registerOre("orbGold", new ItemStack(ItemsCore.genericItem, 1, 4));
		OreDictionary.registerOre("plateFortified", new ItemStack(ItemsCore.genericItem, 1, 7));
		OreDictionary.registerOre("plateEnder", new ItemStack(ItemsCore.genericItem, 1, 8));
		OreDictionary.registerOre("plateGlass", new ItemStack(ItemsCore.genericItem, 1, 9));
		OreDictionary.registerOre("ingotGoldMagical", new ItemStack(ItemsCore.genericItem, 1, 10));
		OreDictionary.registerOre("plateRedstone", new ItemStack(ItemsCore.genericItem, 1, 11));
		OreDictionary.registerOre("dustCrystal", new ItemStack(ItemsCore.genericItem, 1, 20));
		OreDictionary.registerOre("dustMagic", new ItemStack(ItemsCore.genericItem, 1, 3));
		OreDictionary.registerOre("rodHeat", new ItemStack(ItemsCore.genericItem, 1, 25));
		OreDictionary.registerOre("screenMagic", new ItemStack(ItemsCore.genericItem, 1, 27));
		OreDictionary.registerOre("mruLink", new ItemStack(ItemsCore.genericItem, 1, 28));
		OreDictionary.registerOre("mruCatcher", new ItemStack(ItemsCore.genericItem, 1, 29));
		OreDictionary.registerOre("conversionMatrix", new ItemStack(ItemsCore.genericItem, 1, 30));
		OreDictionary.registerOre("plateObsidian", new ItemStack(ItemsCore.genericItem, 1, 31));
		OreDictionary.registerOre("worldInteractor", new ItemStack(ItemsCore.genericItem, 1, 33));
		OreDictionary.registerOre("gemTitanite", new ItemStack(ItemsCore.titanite, 1, 0));
		OreDictionary.registerOre("gemTitaniteTwinkling", new ItemStack(ItemsCore.twinkling_titanite, 1, 0));

		OreDictionary.registerOre("gemPale", new ItemStack(ItemsCore.genericItem, 1, 40));

		if(allowPaleItemsInOtherRecipes) {
			OreDictionary.registerOre("gemDiamond", new ItemStack(ItemsCore.genericItem, 1, 40));
			OreDictionary.registerOre("gemEmerald", new ItemStack(ItemsCore.genericItem, 1, 40));
			OreDictionary.registerOre("gemRuby", new ItemStack(ItemsCore.genericItem, 1, 40));
			OreDictionary.registerOre("gemSapphire", new ItemStack(ItemsCore.genericItem, 1, 40));
			OreDictionary.registerOre("gemPeridot", new ItemStack(ItemsCore.genericItem, 1, 40));
		}

		OreDictionary.registerOre("plateVoid", new ItemStack(ItemsCore.genericItem, 1, 35));
		OreDictionary.registerOre("coreVoid", new ItemStack(ItemsCore.genericItem, 1, 36));
		OreDictionary.registerOre("reactorVoid", new ItemStack(ItemsCore.genericItem, 1, 37));

		OreDictionary.registerOre("focusFire", new ItemStack(ItemsCore.fFocus, 1, 0));
		OreDictionary.registerOre("focusWater", new ItemStack(ItemsCore.wFocus, 1, 0));
		OreDictionary.registerOre("focusEarth", new ItemStack(ItemsCore.eFocus, 1, 0));
		OreDictionary.registerOre("focusAir", new ItemStack(ItemsCore.aFocus, 1, 0));

		OreDictionary.registerOre("soulShard", new ItemStack(ItemsCore.storage, 1, 0));
		OreDictionary.registerOre("soulStone", new ItemStack(ItemsCore.storage, 1, 1));
		OreDictionary.registerOre("darkSoulMatter", new ItemStack(ItemsCore.storage, 1, 2));
		OreDictionary.registerOre("redSoulMatter", new ItemStack(ItemsCore.storage, 1, 3));
		OreDictionary.registerOre("matterOfEternity", new ItemStack(ItemsCore.storage, 1, 4));

		OreDictionary.registerOre("ingotMagnet", new ItemStack(ItemsCore.genericItem, 1, 43));
		OreDictionary.registerOre("gemResonant", new ItemStack(ItemsCore.genericItem, 1, 44));

		OreDictionary.registerOre("coreLapis", new ItemStack(ItemsCore.genericItem, 1, 45));
		OreDictionary.registerOre("dustFading", new ItemStack(ItemsCore.genericItem, 1, 46));
		OreDictionary.registerOre("gemFading", new ItemStack(ItemsCore.genericItem, 1, 47));
		OreDictionary.registerOre("gemMithrilineCrystal", new ItemStack(ItemsCore.genericItem, 1, 48));
		OreDictionary.registerOre("plateMithrilineMetal", new ItemStack(ItemsCore.genericItem, 1, 49));
		OreDictionary.registerOre("ingotMithrilineMetal", new ItemStack(ItemsCore.genericItem, 1, 50));
		OreDictionary.registerOre("dustMithriline", new ItemStack(ItemsCore.genericItem, 1, 51));
		OreDictionary.registerOre("gemWind", new ItemStack(ItemsCore.genericItem, 1, 55));

		OreDictionary.registerOre("record", new ItemStack(ItemsCore.record_everlastingSummer, 1, 0));
		OreDictionary.registerOre("record", new ItemStack(ItemsCore.record_papersPlease, 1, 0));
		OreDictionary.registerOre("record", new ItemStack(ItemsCore.record_robocalypse, 1, 0));
		OreDictionary.registerOre("record", new ItemStack(ItemsCore.record_secret, 1, 0));

		OreDictionary.registerOre("oreMithriline", new ItemStack(BlocksCore.oreMithriline, 1, 0));
		OreDictionary.registerOre("oreMithriline", new ItemStack(BlocksCore.oreMithriline, 1, 1));
		OreDictionary.registerOre("oreMithriline", new ItemStack(BlocksCore.oreMithriline, 1, 2));
		OreDictionary.registerOre("blockMithriline", new ItemStack(BlocksCore.compressed, 1, 5));
	}

	public static void register1() {
		for(ItemStack stk : OreDictionary.getOres("ingotGold", false)) {
			OreDictionary.registerOre("essentialcraft:ingotGold", stk);
		}
	}
}
