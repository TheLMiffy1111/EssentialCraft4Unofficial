package essentialcraft.common.item;

import DummyCore.Client.IModelRegisterer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemHoe;
import net.minecraftforge.client.model.ModelLoader;

public class ItemHoeEC extends ItemHoe implements IModelRegisterer {

	public ItemHoeEC(ToolMaterial material) {
		super(material);
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/" + getRegistryName().getPath(), "inventory"));
	}
}
