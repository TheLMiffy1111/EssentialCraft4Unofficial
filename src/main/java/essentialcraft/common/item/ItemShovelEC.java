package essentialcraft.common.item;

import DummyCore.Client.IModelRegisterer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemSpade;
import net.minecraftforge.client.model.ModelLoader;

public class ItemShovelEC extends ItemSpade implements IModelRegisterer {

	public ItemShovelEC(ToolMaterial p_i45347_1_) {
		super(p_i45347_1_);
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/" + getRegistryName().getPath(), "inventory"));
	}
}
