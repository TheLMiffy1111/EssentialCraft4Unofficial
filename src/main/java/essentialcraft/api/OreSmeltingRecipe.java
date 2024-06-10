package essentialcraft.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import DummyCore.Utils.MiscUtils;
import essentialcraft.common.item.ItemsCore;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.oredict.OreDictionary;

public class OreSmeltingRecipe {

	public static final ArrayList<OreSmeltingRecipe> RECIPES = Lists.<OreSmeltingRecipe>newArrayList();
	public static final HashMap<String, OreSmeltingRecipe> RECIPE_MAP = Maps.<String, OreSmeltingRecipe>newHashMap();

	public String oreName;
	public String outputName;
	public int color;
	public int dropAmount;

	public OreSmeltingRecipe(String oreName, int color) {
		this(oreName, color, 1);
	}

	public OreSmeltingRecipe(String oreName, int color, int dropAmount) {
		this(oreName, "", color, dropAmount);
	}

	public OreSmeltingRecipe(String oreName, String outputName, int color, int dropAmount) {
		this.oreName = oreName;
		this.outputName = outputName;
		this.color = color;
		this.dropAmount = dropAmount;
	}

	public OreSmeltingRecipe(String oreName, String outputName, int color) {
		this(oreName, outputName, color, 1);
	}

	public OreSmeltingRecipe register() {
		boolean flag = true;
		for(OreSmeltingRecipe rec : RECIPES) {
			if(rec.oreName == oreName) {
				flag = false;
			}
		}
		if(flag) {
			RECIPES.add(this);
			RECIPE_MAP.put(oreName, this);
		}
		return this;
	}

	public static OreSmeltingRecipe addRecipe(String oreName, int color) {
		return new OreSmeltingRecipe(oreName, color).register();
	}

	public static OreSmeltingRecipe addRecipe(String oreName, int color, int dropAmount) {
		return new OreSmeltingRecipe(oreName, color, dropAmount).register();
	}

	public static OreSmeltingRecipe addRecipe(String oreName, String outputName, int color, int dropAmount) {
		return new OreSmeltingRecipe(oreName, outputName, color, dropAmount).register();
	}

	public static OreSmeltingRecipe addRecipe(String oreName, String outputName, int color) {
		return new OreSmeltingRecipe(oreName, outputName, color).register();
	}

	public static boolean removeRecipe(OreSmeltingRecipe rec) {
		return RECIPES.remove(rec) && RECIPE_MAP.remove(rec.oreName, rec);
	}

	public static int getColorFromItemStack(ItemStack stk) {
		if(stk.getItem() == ItemsCore.magicalAlloy) {
			if(!stk.hasTagCompound()) {
				if(stk.getItemDamage() < RECIPES.size()) {
					return RECIPES.get(stk.getItemDamage()).color;
				}
				return 0xFFFFFF;
			}
			NBTTagCompound tag = stk.getTagCompound();
			if(tag.hasKey("ore")) {
				return RECIPE_MAP.get(tag.getString("ore")).color;
			}
		}
		return 0xFFFFFF;
	}

	public static String getLocalizedOreName(ItemStack stk) {
		if(stk.getItem() == ItemsCore.magicalAlloy) {
			if(stk.getItemDamage() >= OreSmeltingRecipe.RECIPES.size()) {
				return "";
			}
			OreSmeltingRecipe ore;
			if(!stk.hasTagCompound()) {
				if(stk.getItemDamage() >= RECIPES.size()) {
					return "";
				}
				ore = OreSmeltingRecipe.RECIPES.get(stk.getItemDamage());
			}
			else {
				NBTTagCompound tag = stk.getTagCompound();
				if(!tag.hasKey("ore")) {
					return "";
				}
				ore = RECIPE_MAP.get(tag.getString("ore"));
			}
			List<ItemStack> oreLst = OreDictionary.getOres(ore.oreName, false);
			if(oreLst != null && !oreLst.isEmpty()) {
				return oreLst.get(0).getDisplayName();
			}
			return I18n.translateToLocal("tile."+ore.oreName+".name");
		}
		return "";
	}

	public static ItemStack getAlloyStack(OreSmeltingRecipe rec, int stackSize) {
		ItemStack ret = new ItemStack(ItemsCore.magicalAlloy, stackSize, 0);
		NBTTagCompound tag = MiscUtils.getStackTag(ret);
		tag.setString("ore", rec.oreName);
		return ret;
	}

	public static int getIndex(ItemStack stk) {
		if(stk.getItem() == ItemsCore.magicalAlloy && stk.hasTagCompound()) {
			NBTTagCompound tag = stk.getTagCompound();
			if(tag.hasKey("ore")) {
				return RECIPES.indexOf(RECIPE_MAP.get(tag.getString("ore")));
			}
		}
		return stk.getItemDamage();
	}

	static {
		addRecipe("oreCoal", "gemCoal", 0x343434);
		addRecipe("oreIron", 0xE2C0AA);
		addRecipe("oreGold", 0xF8AF2B);
		addRecipe("oreDiamond", "gemDiamond", 0x5DECF5);
		addRecipe("oreEmerald", "gemEmerald", 0x17DD62);
		addRecipe("oreQuartz", "gemQuartz", 0xD1BEB1);
		addRecipe("oreRedstone", "dustRedstone", 0x8F0303, 8);
		addRecipe("oreLapis", "gemLapis", 0x1C40A9, 16);
		addRecipe("oreCopper", 0xBC4800);
		addRecipe("oreTin", 0xC3E9FF);
		addRecipe("oreLead", 0x7C8CC7);
		addRecipe("oreSilver", 0xF0FDFE);
		addRecipe("oreCobalt", 0x002568);
		addRecipe("oreArdite", 0xC9A537);
		addRecipe("oreNickel", 0xE5E4BD);
		addRecipe("oreAluminum", 0xC5C5C5);
		addRecipe("oreUranium", 0x41B200);
		addRecipe("oreIridium", 0xEBFFFF);
		addRecipe("oreAlchemite", "gemAlchemite", 0xFF0E27, 5);
		addRecipe("oreFireElemental", "gemFireElemental", 0xFF0000, 3);
		addRecipe("oreWaterElemental", "gemWaterElemental", 0x0000FF, 3);
		addRecipe("oreEarthElemental", "gemEarthElemental", 0x7D5A3A, 3);
		addRecipe("oreAirElemental", "gemAirElemental", 0xFFFFFF, 3);
		addRecipe("oreElemental", "gemElemental", 0xFF00FF, 3);
		addRecipe("oreMithriline", "dustMithriline", 0x00FF00, 8);
		addRecipe("oreSaltpeter", "dustSaltpeter", 0x999595, 5);
		addRecipe("oreSulfur", "dustSulfur", 0xFFFF99, 5);
		addRecipe("orePlatinum", 0x6F7889);
		addRecipe("oreMithril", 0x3B525F);
	}
}
