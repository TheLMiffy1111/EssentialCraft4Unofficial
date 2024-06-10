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
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(isInCreativeTab(tab)) {
			for(OreSmeltingRecipe recipe : OreSmeltingRecipe.RECIPES) {
				ItemStack toAdd = new ItemStack(this, 1, 0);
				NBTTagCompound tag = MiscUtils.getStackTag(toAdd);
				tag.setString("ore", recipe.oreName);
				items.add(toAdd);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag isAdvanced) {
		tooltip.add(OreSmeltingRecipe.getLocalizedOreName(stack));
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int layer) {
		return layer == 0 ? 0xFFFFFF : OreSmeltingRecipe.getColorFromItemStack(stack);
	}

	@Override
	public void registerModels() {
		ModelUtils.setItemModelSingleIcon(this, "essentialcraft:item/magicalalloy");
	}
}
