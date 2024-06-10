package essentialcraft.common.block;

import java.util.Random;

import DummyCore.Client.IModelRegisterer;
import essentialcraft.common.item.ItemsCore;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class BlockMagicalFruit extends Block implements IModelRegisterer {

	public BlockMagicalFruit() {
		super(Material.CACTUS);
		setSoundType(SoundType.PLANT);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ItemsCore.fruit;
	}

	@Override
	public int quantityDropped(Random rand) {
		return 3 + rand.nextInt(5);
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random rand) {
		int j = this.quantityDropped(rand) + rand.nextInt(1 + fortune);

		if(j > 9) {
			j = 9;
		}

		return j;
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation("essentialcraft:fruit", "inventory"));
	}
}
