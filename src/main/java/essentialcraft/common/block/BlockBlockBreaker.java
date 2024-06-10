package essentialcraft.common.block;

import DummyCore.Client.IModelRegisterer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockBlockBreaker extends Block implements IModelRegisterer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");

	public BlockBlockBreaker() {
		super(Material.ROCK);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN));
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		if(!world.isRemote && world.getRedstonePowerFromNeighbors(pos) > 0) {
			EnumFacing d = world.getBlockState(pos).getValue(FACING);
			Block broken = world.getBlockState(pos.offset(d)).getBlock();
			if(!broken.isAir(world.getBlockState(pos.offset(d)), world, pos.offset(d))) {
				float hardness = broken.getBlockHardness(world.getBlockState(pos.offset(d)), world, pos.offset(d));
				if(hardness >= 0 && hardness <= 10) {
					for(int i = 1; i < 13; ++i) {
						BlockPos dP = pos.offset(d, i);
						Block b = world.getBlockState(dP).getBlock();
						if(b.getBlockHardness(world.getBlockState(dP), world, dP) != hardness) {
							break;
						}
						b.breakBlock(world, dP, world.getBlockState(dP));
						b.onPlayerDestroy(world, dP, world.getBlockState(dP));
						b.dropBlockAsItem(world, dP, world.getBlockState(dP), 0);
						world.setBlockToAir(dP);
					}
				}
			}
		}
	}

	@Override
	public IBlockState getStateForPlacement(World w, BlockPos p, EnumFacing side, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, side);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta % 6));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation("essentialcraft:blockbreaker", "inventory"));
	}
}
