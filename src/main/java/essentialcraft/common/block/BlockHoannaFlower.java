package essentialcraft.common.block;

import DummyCore.Client.IModelRegisterer;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class BlockHoannaFlower extends BlockBush implements IModelRegisterer {

	public BlockHoannaFlower() {
		super();
		setSoundType(SoundType.PLANT);
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation("essentialcraft:flowergreen", "inventory"));
	}
}
