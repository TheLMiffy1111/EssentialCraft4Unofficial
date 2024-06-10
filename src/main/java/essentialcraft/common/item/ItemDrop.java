package essentialcraft.common.item;

import DummyCore.Client.IModelRegisterer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;

public class ItemDrop extends Item implements IModelRegisterer {
	public static String[] dropNames = {"fire", "water", "earth", "air", "elemental", "unknown"};

	public ItemDrop() {
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		return getTranslationKey()+dropNames[Math.min(stack.getItemDamage(), dropNames.length-1)];
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(isInCreativeTab(tab)) {
			for(int i = 0; i < 5; ++i) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	public void registerModels() {
		for(int i = 0; i < 5; i++) {
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation("essentialcraft:item/gem_elemental", "type=" + dropNames[i]));
		}
	}
}
