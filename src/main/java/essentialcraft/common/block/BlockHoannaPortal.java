package essentialcraft.common.block;

import java.util.Random;

import com.google.common.cache.LoadingCache;

import DummyCore.Client.IModelRegisterer;
import DummyCore.Client.ModelUtils;
import DummyCore.Utils.DummyPortalHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//Might make this extend a class in DummyCore
public class BlockHoannaPortal extends BlockPortal implements IModelRegisterer {

	public BlockHoannaPortal() {
		super();
		setSoundType(SoundType.GLASS);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {}

	@Override
	public void onEntityCollision(World world, BlockPos pos, IBlockState s, Entity entity) {
		if(!world.isRemote) {
			DummyPortalHandler.transferEntityToDimension(entity);
		}
	}

	@Override
	public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand) {
		if(rand.nextInt(100) == 0) {
			worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		EnumFacing.Axis enumfacing$axis = state.getValue(AXIS);

		if(enumfacing$axis == EnumFacing.Axis.X) {
			BlockHoannaPortal.Size blockportal$size = new BlockHoannaPortal.Size(worldIn, pos, EnumFacing.Axis.X);

			if(!blockportal$size.isValid() || blockportal$size.portalBlockCount < blockportal$size.width * blockportal$size.height) {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
			}
		}
		else if(enumfacing$axis == EnumFacing.Axis.Z) {
			BlockHoannaPortal.Size blockportal$size1 = new BlockHoannaPortal.Size(worldIn, pos, EnumFacing.Axis.Z);

			if(!blockportal$size1.isValid() || blockportal$size1.portalBlockCount < blockportal$size1.width * blockportal$size1.height) {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
			}
		}
	}

	@Override
	public BlockPattern.PatternHelper createPatternHelper(World world, BlockPos pos) {
		EnumFacing.Axis axis = EnumFacing.Axis.Z;
		BlockHoannaPortal.Size size = new BlockHoannaPortal.Size(world, pos, EnumFacing.Axis.X);
		LoadingCache<BlockPos, BlockWorldState> loadingcache = BlockPattern.createLoadingCache(world, true);

		if(!size.isValid()) {
			axis = EnumFacing.Axis.X;
			size = new BlockHoannaPortal.Size(world, pos, EnumFacing.Axis.Z);
		}

		if(!size.isValid()) {
			return new BlockPattern.PatternHelper(pos, EnumFacing.NORTH, EnumFacing.UP, loadingcache, 1, 1, 1);
		}
		int[] aint = new int[EnumFacing.AxisDirection.values().length];
		EnumFacing enumfacing = size.rightDir.rotateYCCW();
		BlockPos blockpos = size.bottomLeft.up(size.getHeight() - 1);

		for(EnumFacing.AxisDirection dir : EnumFacing.AxisDirection.values()) {
			BlockPattern.PatternHelper patternHelper = new BlockPattern.PatternHelper(enumfacing.getAxisDirection() == dir ? blockpos : blockpos.offset(size.rightDir, size.getWidth() - 1), EnumFacing.getFacingFromAxis(dir, axis), EnumFacing.UP, loadingcache, size.getWidth(), size.getHeight(), 1);

			for(int i = 0; i < size.getWidth(); ++i) {
				for(int j = 0; j < size.getHeight(); ++j) {
					BlockWorldState blockworldstate = patternHelper.translateOffset(i, j, 1);

					if(blockworldstate.getBlockState() != null && blockworldstate.getBlockState().getMaterial() != Material.AIR) {
						++aint[dir.ordinal()];
					}
				}
			}
		}

		EnumFacing.AxisDirection axisDir = EnumFacing.AxisDirection.POSITIVE;

		for(EnumFacing.AxisDirection dir : EnumFacing.AxisDirection.values()) {
			if(aint[dir.ordinal()] < aint[axisDir.ordinal()]) {
				axisDir = dir;
			}
		}

		return new BlockPattern.PatternHelper(enumfacing.getAxisDirection() == axisDir ? blockpos : blockpos.offset(size.rightDir, size.getWidth() - 1), EnumFacing.getFacingFromAxis(axisDir, axis), EnumFacing.UP, loadingcache, size.getWidth(), size.getHeight(), 1);
	}

	@Override
	public boolean trySpawnPortal(World world, BlockPos pos) {
		BlockHoannaPortal.Size size = new BlockHoannaPortal.Size(world, pos, EnumFacing.Axis.X);
		if(size.isValid() && size.portalBlockCount == 0) {
			size.placePortalBlocks();
			return true;
		}
		size = new BlockHoannaPortal.Size(world, pos, EnumFacing.Axis.Z);
		if(size.isValid() && size.portalBlockCount == 0) {
			size.placePortalBlocks();
			return true;
		}
		return false;
	}

	@Override
	public void registerModels() {
		ModelUtils.setItemModelSingleIcon(Item.getItemFromBlock(this), "essentialcraft:portal");
	}

	public static class Size {
		private final World world;
		private final EnumFacing.Axis axis;
		private final EnumFacing rightDir;
		private final EnumFacing leftDir;
		private int portalBlockCount;
		private BlockPos bottomLeft;
		private int height;
		private int width;

		public Size(World world, BlockPos pos, EnumFacing.Axis axis) {
			this.world = world;
			this.axis = axis;

			if(axis == EnumFacing.Axis.X) {
				leftDir = EnumFacing.EAST;
				rightDir = EnumFacing.WEST;
			}
			else {
				leftDir = EnumFacing.NORTH;
				rightDir = EnumFacing.SOUTH;
			}

			for(BlockPos blockpos = pos; pos.getY() > blockpos.getY() - 21 && pos.getY() > 0 && isEmptyBlock(world.getBlockState(pos.down()).getBlock()); pos = pos.down()) {}

			int i = getDistanceUntilEdge(pos, leftDir) - 1;

			if(i >= 0) {
				bottomLeft = pos.offset(leftDir, i);
				width = getDistanceUntilEdge(bottomLeft, rightDir);

				if(width < 2 || width > 21) {
					bottomLeft = null;
					width = 0;
				}
			}

			if(bottomLeft != null) {
				height = calculatePortalHeight();
			}
		}

		protected int getDistanceUntilEdge(BlockPos pos, EnumFacing side) {
			int i;
			for(i = 0; i < 22; ++i) {
				BlockPos blockpos = pos.offset(side, i);
				if(!isEmptyBlock(world.getBlockState(blockpos).getBlock()) || world.getBlockState(blockpos.down()).getBlock() != Blocks.OBSIDIAN) {
					break;
				}
			}
			Block block = world.getBlockState(pos.offset(side, i)).getBlock();
			return block == Blocks.OBSIDIAN ? i : 0;
		}

		public int getHeight() {
			return height;
		}

		public int getWidth() {
			return width;
		}

		protected int calculatePortalHeight() {
			label24: for(height = 0; height < 21; ++height) {
				for(int i = 0; i < width; ++i) {
					BlockPos blockpos = bottomLeft.offset(rightDir, i).up(height);
					Block block = world.getBlockState(blockpos).getBlock();

					if(!isEmptyBlock(block)) {
						break label24;
					}

					if(block == BlocksCore.portal) {
						++portalBlockCount;
					}

					if(i == 0) {
						block = world.getBlockState(blockpos.offset(leftDir)).getBlock();

						if(block != Blocks.OBSIDIAN) {
							break label24;
						}
					}
					else if(i == width - 1) {
						block = world.getBlockState(blockpos.offset(rightDir)).getBlock();

						if(block != Blocks.OBSIDIAN) {
							break label24;
						}
					}
				}
			}

			for(int j = 0; j < width; ++j) {
				if(world.getBlockState(bottomLeft.offset(rightDir, j).up(height)).getBlock() != Blocks.OBSIDIAN) {
					height = 0;
					break;
				}
			}

			if(height <= 21 && height >= 3) {
				return height;
			}
			bottomLeft = null;
			width = 0;
			height = 0;
			return 0;
		}

		protected boolean isEmptyBlock(Block blockIn) {
			return blockIn.getMaterial(blockIn.getDefaultState()) == Material.AIR || /*blockIn == BlocksCore.portalActivator ||*/ blockIn == BlocksCore.portal;
		}

		public boolean isValid() {
			return bottomLeft != null && width >= 2 && width <= 21 && height >= 3 && height <= 21;
		}

		public void placePortalBlocks() {
			for(int i = 0; i < width; ++i) {
				BlockPos blockpos = bottomLeft.offset(rightDir, i);

				for(int j = 0; j < height; ++j) {
					world.setBlockState(blockpos.up(j), BlocksCore.portal.getDefaultState().withProperty(BlockPortal.AXIS, axis), 2);
				}
			}
		}
	}
}
