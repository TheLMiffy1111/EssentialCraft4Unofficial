package essentialcraft.common.item;

import java.util.List;

import DummyCore.Client.IItemColor;
import DummyCore.Client.IModelRegisterer;
import DummyCore.Client.ModelUtils;
import DummyCore.Utils.MiscUtils;
import essentialcraft.api.OreSmeltingRecipe;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMagicalAlloy extends Item implements IItemColor, IModelRegisterer {

	public ItemMagicalAlloy() {
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public void getSubItems(CreativeTabs p_150895_2_, NonNullList<ItemStack> p_150895_3_) {
		if(isInCreativeTab(p_150895_2_)) {
			for(OreSmeltingRecipe recipe : OreSmeltingRecipe.RECIPES) {
				ItemStack toAdd = new ItemStack(this, 1, 0);
				NBTTagCompound tag = MiscUtils.getStackTag(toAdd);
				tag.setString("ore", recipe.oreName);
				p_150895_3_.add(toAdd);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack p_77624_1_, World p_77624_2_, List<String> p_77624_3_, ITooltipFlag p_77624_4_) {
		p_77624_3_.add(OreSmeltingRecipe.getLocalizedOreName(p_77624_1_));
	}

	@Override
	public int getColorFromItemstack(ItemStack p_82790_1_, int p_82790_2_) {
		return p_82790_2_ == 0 ? 0xFFFFFF : OreSmeltingRecipe.getColorFromItemStack(p_82790_1_);
	}

	@Override
	public void registerModels() {
		ModelUtils.setItemModelSingleIcon(this, "essentialcraft:item/magicalalloy");
	}
}
