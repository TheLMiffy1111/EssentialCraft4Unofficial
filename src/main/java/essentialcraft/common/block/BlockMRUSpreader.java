package essentialcraft.common.block;

import java.util.Random;

import DummyCore.Client.IModelRegisterer;
import essentialcraft.common.mod.EssentialCraftCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockMRUSpreader extends Block implements IModelRegisterer {

	public static final AxisAlignedBB BLOCK_AABB = new AxisAlignedBB(0.3D, 0D, 0.3D, 0.7D, 0.8D, 0.7D);

	public BlockMRUSpreader() {
		super(Material.ROCK, MapColor.PURPLE);
		setTickRandomly(true);
		setLightLevel(1.0F);
	}

	@Override
	public void randomDisplayTick(IBlockState s, World p_149734_1_, BlockPos p_149734_2_, Random p_149734_5_) {
		for(int i = 0; i < 5; ++i) {
			Vec3d rotateVec = new Vec3d(1, 1, 1);
			rotateVec = rotateVec.rotatePitch(p_149734_5_.nextFloat()*360F);
			rotateVec = rotateVec.rotateYaw(p_149734_5_.nextFloat()*360F);
			for(int i1 = 0; i1 < 10; ++i1) {
				EssentialCraftCore.proxy.spawnParticle("mruFX", p_149734_2_.getX()+0.5F, p_149734_2_.getY()+1F, p_149734_2_.getZ()+0.5F, rotateVec.x*10, rotateVec.y*10, rotateVec.z*10);
			}
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState s)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState s)
	{
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState s)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BLOCK_AABB;
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation("essentialcraft:spreader", "inventory"));
	}
}
