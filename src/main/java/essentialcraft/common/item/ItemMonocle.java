package essentialcraft.common.item;

import DummyCore.Client.IModelRegisterer;
import essentialcraft.api.IMRUVisibilityHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

public class ItemMonocle extends Item implements IMRUVisibilityHandler, IModelRegisterer {

	public ItemMonocle() {
		super();
		maxStackSize = 1;
		setMaxDamage(16);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public int getItemEnchantability() {
		return 10;
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/magicmonocle", "inventory"));
	}

	@Override
	public boolean canSeeMRU(ItemStack stk) {
		return true;
	}
}
