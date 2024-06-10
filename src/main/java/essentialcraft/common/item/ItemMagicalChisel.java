package essentialcraft.common.item;

import DummyCore.Client.IModelRegisterer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

public class ItemMagicalChisel extends Item implements IModelRegisterer {

	@Override
	public ItemStack getContainerItem(ItemStack itemStack)
	{
		if(!hasContainerItem(itemStack))
		{
			return ItemStack.EMPTY;
		}
		return new ItemStack(itemStack.getItem(), itemStack.getCount(), itemStack.getItemDamage()+1);
	}

	@Override
	public boolean hasContainerItem(ItemStack stack)
	{
		return stack.getItemDamage() < stack.getMaxDamage();
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/magicalchisel", "inventory"));
	}
}
