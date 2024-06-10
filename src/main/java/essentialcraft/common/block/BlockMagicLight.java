package essentialcraft.common.block;

import java.util.Random;

import DummyCore.Client.IModelRegisterer;
import DummyCore.Client.ModelUtils;
import essentialcraft.common.mod.EssentialCraftCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMagicLight extends Block implements IModelRegisterer {

	public static final PropertyEnum<LightType> TYPE = PropertyEnum.<LightType>create("type", LightType.class);
	public static final AxisAlignedBB BLOCK_AABB = new AxisAlignedBB(0.2F, 0.2F, 0.2F, 0.8F, 0.8F, 0.8F);

	public BlockMagicLight() {
		super(Material.CIRCUITS);
		setTickRandomly(true);
		setLightLevel(1.0F);
	}

	@Override
	public int quantityDropped(Random p_149745_1_)
	{
		return 0;
	}

	@Override
	public Item getItemDropped(IBlockState p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return null;
	}

	@Override
	public void updateTick(World p_149674_1_, BlockPos p_149674_2_, IBlockState p_149674_3_, Random p_149674_4_)
	{
		int meta = p_149674_3_.getValue(TYPE).getIndex();
		if(meta == 1) {
			p_149674_1_.setBlockToAir(p_149674_2_);
		}
	}

	@Override
	public void randomDisplayTick(IBlockState p_149734_1_, World p_149734_2_, BlockPos p_149734_3_, Random p_149734_4_)
	{
		int meta = p_149734_1_.getValue(TYPE).getIndex();
		if(meta == 0) {
			for(int i = 0; i < 5; ++i)
			{
				Vec3d rotateVec = new Vec3d(1, 1, 1);
				rotateVec = rotateVec.rotatePitch(p_149734_4_.nextFloat()*360F);
				rotateVec = rotateVec.rotateYaw(p_149734_4_.nextFloat()*360F);
				EssentialCraftCore.proxy.spawnParticle("mruFX",p_149734_3_.getX()+0.5F, p_149734_3_.getY()+0.5F, p_149734_3_.getZ()+0.5F, rotateVec.x/5, rotateVec.y/5, rotateVec.z/5);
				rotateVec = null;
			}
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState s)
	{
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return Block.NULL_AABB;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
	{
		return BLOCK_AABB.offset(pos);
	}

	@Override
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isFullCube(IBlockState s)
	{
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, LightType.fromIndex(meta%2));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).getIndex();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(TYPE).build());
		ModelUtils.setItemModelSingleIcon(Item.getItemFromBlock(this), "essentialcraft:torch");
	}

	public static enum LightType implements IStringSerializable {
		PLACED(0, "placed"),
		SPAWNED(1, "spawned");

		private int index;
		private String name;

		private LightType(int i, String s) {
			index = i;
			name = s;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}

		public int getIndex() {
			return index;
		}

		public static LightType fromIndex(int i) {
			return values()[i%2];
		}
	}
}
