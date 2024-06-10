package essentialcraft.common.block;

import DummyCore.Client.IModelRegisterer;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.client.model.ModelLoader;

public class BlockEC extends Block implements IModelRegisterer {

	public BlockRenderLayer layer = BlockRenderLayer.SOLID;

	public BlockEC(Material material) {
		super(material);
	}

	public BlockEC(Material material, MapColor color) {
		super(material, color);
	}

	public BlockEC(Material material, BlockRenderLayer layer) {
		super(material);
		this.layer = layer;
	}

	public BlockEC(Material material, MapColor color, BlockRenderLayer layer) {
		super(material, color);
		this.layer = layer;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return layer;
	}

	@Override
	public Block setSoundType(SoundType s) {
		return super.setSoundType(s);
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation("essentialcraft:" + getRegistryName().getPath(), "inventory"));
	}
}
