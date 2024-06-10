package essentialcraft.common.block;

import java.util.ArrayList;
import java.util.Random;

import DummyCore.Client.IBlockColor;
import DummyCore.Client.IItemColor;
import DummyCore.Client.IModelRegisterer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDoublePlant.EnumPlantType;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;

public class BlockHoannaTallGrass extends BlockBush implements IGrowable, IShearable, IBlockColor, IItemColor, IModelRegisterer {

	public BlockHoannaTallGrass() {
		super(Material.VINE);
		setSoundType(SoundType.PLANT);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		float f = 0.4F;
		return new AxisAlignedBB(0.5F - f, 0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f).offset(pos);
	}

	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
		Block b = state.getBlock();
		if(b != null && b instanceof BlockHoannaTallGrass) {
			return true;
		}
		return false;
	}

	@Override
	public int colorMultiplier(IBlockState s, IBlockAccess world, BlockPos pos, int tint) {
		return BiomeColorHelper.getGrassColorAtPos(world, pos);
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		return ColorizerGrass.getGrassColor(0.5D, 1D);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(this, 1, 0);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random rand) {
		return 1 + rand.nextInt(fortune * 2 + 1);
	}

	@Override
	public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState meta, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<>();
		if(world instanceof World ? ((World)world).rand.nextInt(8) != 0 : RANDOM.nextInt(8) != 0) {
			return ret;
		}
		ItemStack seed = ForgeHooks.getGrassSeed(world instanceof World ? ((World)world).rand : RANDOM, fortune);
		if(!seed.isEmpty()) {
			ret.add(seed);
		}
		return ret;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<>();
		ret.add(new ItemStack(this, 1, getMetaFromState(world.getBlockState(pos))));
		return ret;
	}

	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
		int l = getMetaFromState(state);
		byte b0 = 2;

		if(l == 2) {
			b0 = 3;
		}

		if(Blocks.DOUBLE_PLANT.canPlaceBlockAt(world, pos)) {
			Blocks.DOUBLE_PLANT.placeAt(world, pos, EnumPlantType.byMetadata(b0), 2);
		}
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.FARMLAND || state.getBlock() instanceof BlockHoannaTallGrass;
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation("essentialcraft:tallgrass", "inventory"));
	}
}
