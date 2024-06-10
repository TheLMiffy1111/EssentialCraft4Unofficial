package essentialcraft.common.item;

import DummyCore.Client.IModelRegisterer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;

public class ItemRecordEC extends ItemRecord implements IModelRegisterer {

	protected ItemRecordEC(String p_i45350_1_, SoundEvent sound) {
		super(p_i45350_1_, sound);
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/" + getRegistryName().getPath(), "inventory"));
	}
}
