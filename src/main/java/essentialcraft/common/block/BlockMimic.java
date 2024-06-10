package essentialcraft.common.block;

import DummyCore.Client.IModelRegisterer;
import DummyCore.Utils.UnlistedPropertyObject;
import essentialcraft.common.tile.TileMimic;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockMimic extends BlockContainer implements IModelRegisterer {

	public static final UnlistedPropertyObject<IBlockState> STATE = new UnlistedPropertyObject<>("state", IBlockState.class);
	public static final UnlistedPropertyObject<IBlockAccess> WORLD = new UnlistedPropertyObject<>("world", IBlockAccess.class);
	public static final UnlistedPropertyObject<BlockPos> POS = new UnlistedPropertyObject<>("pos", BlockPos.class);

	public BlockMimic() {
		super(Material.ROCK);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileMimic();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[] {STATE, WORLD, POS});
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		state = ((IExtendedBlockState)state).withProperty(WORLD, world).withProperty(POS, pos);
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null && tile instanceof TileMimic) {
			return ((IExtendedBlockState)state).withProperty(STATE, ((TileMimic)tile).getState());
		}
		return state;
	}

	@Override
	public boolean isOpaqueCube(IBlockState s) {
		return false;
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return true;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity tile = worldIn.getTileEntity(pos);
		ItemStack heldItem = playerIn.getHeldItem(hand);

		if(tile instanceof TileMimic) {
			if(!playerIn.isSneaking()) {
				if(!heldItem.isEmpty() && heldItem.getItem() instanceof ItemBlock) {
					TileMimic mimic = (TileMimic)tile;
					IBlockState changeState = ((ItemBlock)heldItem.getItem()).getBlock().getStateForPlacement(worldIn, pos, side, hitX, hitY, hitZ, heldItem.getItemDamage(), playerIn);

					if(isValidBlock(changeState) && changeState.getBlock() != this) {
						mimic.setState(changeState);
						worldIn.markBlockRangeForRenderUpdate(pos, pos.add(1, 1, 1));
						return true;
					}
				}
			}
			else {
				TileMimic mimic = (TileMimic)tile;
				if(mimic.getState() != null) {
					mimic.setState(null);
					worldIn.markBlockRangeForRenderUpdate(pos, pos.add(1, 1, 1));
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		try {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof TileMimic && ((TileMimic)te).getState() != null) {
				return ((TileMimic)te).getState().getBoundingBox(new FakeBlockAccess(worldIn), pos);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return super.getBoundingBox(state, worldIn, pos);
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		try {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof TileMimic && ((TileMimic)te).getState() != null) {
				return ((TileMimic)te).getState().getMapColor(new FakeBlockAccess(worldIn), pos);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return super.getMapColor(state, worldIn, pos);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public static boolean isValidBlock(IBlockState state) {
		return state.getRenderType() == EnumBlockRenderType.MODEL && state.getMaterial() != Material.AIR;
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation("essentialcraft:mimic", "inventory"));
	}

	private static class FakeBlockAccess implements IBlockAccess {

		private final IBlockAccess compose;

		private FakeBlockAccess(IBlockAccess compose) {
			this.compose = compose;
		}

		@Override
		public TileEntity getTileEntity(BlockPos pos) {
			return compose.getTileEntity(pos);
		}

		@Override
		public int getCombinedLight(BlockPos pos, int lightValue) {
			return 15 << 20 | 15 << 4;
		}

		@Override
		public IBlockState getBlockState(BlockPos pos) {
			IBlockState state = compose.getBlockState(pos);
			if(state.getBlock() instanceof BlockMimic) {
				state = ((TileMimic)compose.getTileEntity(pos)).getState();
			}
			return state == null ? Blocks.AIR.getDefaultState() : state;
		}

		@Override
		public boolean isAirBlock(BlockPos pos) {
			return compose.isAirBlock(pos);
		}

		@Override
		public Biome getBiome(BlockPos pos) {
			return compose.getBiome(pos);
		}

		@Override
		public int getStrongPower(BlockPos pos, EnumFacing direction) {
			return compose.getStrongPower(pos, direction);
		}

		@Override
		public WorldType getWorldType() {
			return compose.getWorldType();
		}

		@Override
		public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
			return compose.isSideSolid(pos, side, _default);
		}
	}
}
