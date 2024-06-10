package essentialcraft.common.item;

import java.util.Locale;

import DummyCore.Client.IModelRegisterer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;

public class ItemEssentialFuel extends Item implements IModelRegisterer {

	public String[] name = {"Fiery", "Watery", "Earthen", "Windy", "Unknown"};

	public ItemEssentialFuel() {
		super();
		setMaxDamage(0);
		maxStackSize = 16;
		bFull3D = false;
		setHasSubtypes(true);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(isInCreativeTab(tab)) {
			for(int i = 0; i < 4; ++i) {
				ItemStack min = new ItemStack(this, 1, i);
				items.add(min);
			}
		}
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		return getTranslationKey()+name[Math.min(stack.getItemDamage(), name.length-1)];
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}

	@Override
	public void registerModels() {
		for(int i = 0; i < name.length-1; i++) {
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation("essentialcraft:item/elementalfuel", "type=" + name[i].toLowerCase(Locale.ENGLISH)));
		}
	}
}
