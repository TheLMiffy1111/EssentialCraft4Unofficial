package essentialcraft.common.world.gen.structure;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import essentialcraft.common.block.BlocksCore;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class StructureModernShaftPieces {

	public static void registerShaftComponents() {
		MapGenStructureIO.registerStructureComponent(StructureModernShaftPieces.Corridor.class, "MMSCorridor");
		MapGenStructureIO.registerStructureComponent(StructureModernShaftPieces.Cross.class, "MMSCrossing");
		MapGenStructureIO.registerStructureComponent(StructureModernShaftPieces.Room.class, "MMSRoom");
		MapGenStructureIO.registerStructureComponent(StructureModernShaftPieces.Stairs.class, "MMSStairs");
	}

	private static StructureComponent getRandomComponent(List<StructureComponent> components, Random rand, int x, int y, int z, EnumFacing facing, int index) {
		int j1 = rand.nextInt(100);
		StructureBoundingBox structureboundingbox;

		if(j1 >= 80) {
			structureboundingbox = StructureModernShaftPieces.Cross.findValidPlacement(components, rand, x, y, z, facing);

			if(structureboundingbox != null) {
				return new StructureModernShaftPieces.Cross(index, structureboundingbox, facing);
			}
		}
		else if(j1 >= 70) {
			structureboundingbox = StructureModernShaftPieces.Stairs.findValidPlacement(components, rand, x, y, z, facing);

			if(structureboundingbox != null) {
				return new StructureModernShaftPieces.Stairs(index, rand, structureboundingbox, facing);
			}
		}
		else {
			structureboundingbox = StructureModernShaftPieces.Corridor.findValidPlacement(components, rand, x, y, z, facing);

			if(structureboundingbox != null) {
				return new StructureModernShaftPieces.Corridor(index, rand, structureboundingbox, facing);
			}
		}

		return null;
	}

	private static StructureComponent getNextMineShaftComponent(StructureComponent parent, List<StructureComponent> components, Random rand, int x, int y, int z, EnumFacing facing, int index) {
		if((index > 8) || (Math.abs(x - parent.getBoundingBox().minX) > 80) || (Math.abs(z - parent.getBoundingBox().minZ) > 80)) {
			return null;
		}
		StructureComponent structurecomponent1 = getRandomComponent(components, rand, x, y, z, facing, index + 1);

		if(structurecomponent1 != null) {
			components.add(structurecomponent1);
			structurecomponent1.buildComponent(parent, components, rand);
		}

		return structurecomponent1;
	}

	public static class Corridor extends StructureComponent {

		private boolean hasRails;
		private boolean hasSpiders;
		private boolean spawnerPlaced;
		private int sectionCount;

		public Corridor() {}

		@Override
		protected void writeStructureToNBT(NBTTagCompound nbt) {
			nbt.setBoolean("hr", hasRails);
			nbt.setBoolean("sc", hasSpiders);
			nbt.setBoolean("hps", spawnerPlaced);
			nbt.setInteger("Num", sectionCount);
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager tm) {
			hasRails = nbt.getBoolean("hr");
			hasSpiders = nbt.getBoolean("sc");
			spawnerPlaced = nbt.getBoolean("hps");
			sectionCount = nbt.getInteger("Num");
		}

		public Corridor(int index, Random rand, StructureBoundingBox structureBB, EnumFacing facing) {
			super(index);
			setCoordBaseMode(facing);
			boundingBox = structureBB;
			hasRails = rand.nextInt(3) == 0;
			hasSpiders = !hasRails && rand.nextInt(23) == 0;

			if(getCoordBaseMode().getAxis() != EnumFacing.Axis.Z) {
				sectionCount = structureBB.getXSize() / 5;
			}
			else {
				sectionCount = structureBB.getZSize() / 5;
			}
		}

		public static StructureBoundingBox findValidPlacement(List<StructureComponent> components, Random rand, int x, int y, int z, EnumFacing facing) {
			StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y, z, x, y + 2, z);
			int i1;

			for(i1 = rand.nextInt(3) + 2; i1 > 0; --i1) {
				int j1 = i1 * 5;

				switch(facing) {
				case SOUTH:
					structureboundingbox.maxX = x + 2;
					structureboundingbox.maxZ = z + j1 - 1;
					break;
				case WEST:
					structureboundingbox.minX = x - (j1 - 1);
					structureboundingbox.maxZ = z + 2;
					break;
				case NORTH:
					structureboundingbox.maxX = x + 2;
					structureboundingbox.minZ = z - (j1 - 1);
					break;
				case EAST:
					structureboundingbox.maxX = x + j1 - 1;
					structureboundingbox.maxZ = z + 2;
					break;
				default:
					break;
				}

				if(StructureComponent.findIntersecting(components, structureboundingbox) == null) {
					break;
				}
			}

			return i1 > 0 ? structureboundingbox : null;
		}

		@Override
		public void buildComponent(StructureComponent parent, List<StructureComponent> components, Random rand) {
			int i = getComponentType();
			int j = rand.nextInt(4);

			EnumFacing enumfacing = getCoordBaseMode();

			if(enumfacing != null) {
				switch(enumfacing) {
				case NORTH:
				default:
					if(j <= 1) {
						StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX, boundingBox.minY - 1 + rand.nextInt(3), boundingBox.minZ - 1, enumfacing, i);
					}
					else if(j == 2) {
						StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX - 1, boundingBox.minY - 1 + rand.nextInt(3), boundingBox.minZ, EnumFacing.WEST, i);
					}
					else {
						StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.maxX + 1, boundingBox.minY - 1 + rand.nextInt(3), boundingBox.minZ, EnumFacing.EAST, i);
					}
					break;
				case SOUTH:
					if(j <= 1) {
						StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX, boundingBox.minY - 1 + rand.nextInt(3), boundingBox.maxZ + 1, enumfacing, i);
					}
					else if(j == 2) {
						StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX - 1, boundingBox.minY - 1 + rand.nextInt(3), boundingBox.maxZ - 3, EnumFacing.WEST, i);
					}
					else {
						StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.maxX + 1, boundingBox.minY - 1 + rand.nextInt(3), boundingBox.maxZ - 3, EnumFacing.EAST, i);
					}
					break;
				case WEST:
					if(j <= 1) {
						StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX - 1, boundingBox.minY - 1 + rand.nextInt(3), boundingBox.minZ, enumfacing, i);
					}
					else if(j == 2) {
						StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX, boundingBox.minY - 1 + rand.nextInt(3), boundingBox.minZ - 1, EnumFacing.NORTH, i);
					}
					else {
						StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX, boundingBox.minY - 1 + rand.nextInt(3), boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
					}
					break;
				case EAST:
					if(j <= 1) {
						StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.maxX + 1, boundingBox.minY - 1 + rand.nextInt(3), boundingBox.minZ, enumfacing, i);
					}
					else if(j == 2) {
						StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.maxX - 3, boundingBox.minY - 1 + rand.nextInt(3), boundingBox.minZ - 1, EnumFacing.NORTH, i);
					}
					else {
						StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.maxX - 3, boundingBox.minY - 1 + rand.nextInt(3), boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
					}
				}
			}

			if(i < 8) {
				int k;
				int l;

				if(getCoordBaseMode().getAxis() != EnumFacing.Axis.Z) {
					for(k = boundingBox.minX + 3; k + 3 <= boundingBox.maxX; k += 5) {
						l = rand.nextInt(5);

						if(l == 0) {
							StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, k, boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, i + 1);
						}
						else if(l == 1) {
							StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, k, boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, i + 1);
						}
					}
				}
				else {
					for(k = boundingBox.minZ + 3; k + 3 <= boundingBox.maxZ; k += 5) {
						l = rand.nextInt(5);

						if(l == 0) {
							StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX - 1, boundingBox.minY, k, EnumFacing.WEST, i + 1);
						}
						else if(l == 1) {
							StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.maxX + 1, boundingBox.minY, k, EnumFacing.EAST, i + 1);
						}
					}
				}
			}
		}

		@Override
		protected boolean generateChest(World world, StructureBoundingBox structureBB, Random rand, int x, int y, int z, ResourceLocation loot) {
			int i1 = getXWithOffset(x, z);
			int j1 = getYWithOffset(y);
			int k1 = getZWithOffset(x, z);

			if(structureBB.isVecInside(new BlockPos(i1, j1, k1)) && world.getBlockState(new BlockPos(i1, j1, k1)).getMaterial() == Material.AIR) {
				if(rand.nextBoolean()) {}
				world.setBlockState(new BlockPos(i1, j1, k1), Blocks.RAIL.getDefaultState(), 2);
				world.setBlockState(new BlockPos(i1, j1-1, k1), BlocksCore.voidStone.getDefaultState(), 2);
				EntityMinecartChest entityminecartchest = new EntityMinecartChest(world, i1 + 0.5F, j1 + 0.5F, k1 + 0.5F);
				entityminecartchest.setLootTable(loot, rand.nextLong());
				world.spawnEntity(entityminecartchest);
				return true;
			}
			return false;
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB) {
			if(isLiquidInStructureBoundingBox(world, structureBB)) {
				return false;
			}
			int i = sectionCount * 5 - 1;
			fillWithBlocks(world, structureBB, 0, 0, 0, 2, 1, i, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			//random destruction
			generateMaybeBox(world, structureBB, rand, 0.8F, 0, 2, 0, 2, 2, i, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false, 0);

			if(hasSpiders) {
				//webs
				generateMaybeBox(world, structureBB, rand, 0.6F, 0, 0, 0, 2, 1, i, Blocks.WEB.getDefaultState(), Blocks.AIR.getDefaultState(), false, 0);
			}

			int j;
			int k;

			for(j = 0; j < sectionCount; ++j) {
				k = 2 + j * 5;

				fillWithBlocks(world, structureBB, 0, 0, k, 0, 1, k, BlocksCore.fence[0].getDefaultState(), Blocks.AIR.getDefaultState(), false);
				fillWithBlocks(world, structureBB, 2, 0, k, 2, 1, k, BlocksCore.fence[0].getDefaultState(), Blocks.AIR.getDefaultState(), false);

				if(rand.nextInt(4) == 0) {
					//supports
					fillWithBlocks(world, structureBB, 0, 2, k, 0, 2, k, BlocksCore.voidStone.getDefaultState(), BlocksCore.voidStone.getDefaultState(), false);
					fillWithBlocks(world, structureBB, 2, 2, k, 2, 2, k, BlocksCore.voidStone.getDefaultState(), BlocksCore.voidStone.getDefaultState(), false);
				}
				else {
					fillWithBlocks(world, structureBB, 0, 2, k, 2, 2, k, BlocksCore.voidStone.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				}

				//Spawner
				randomlyPlaceBlock(world, structureBB, rand, 0.1F, 0, 2, k - 1, Blocks.WEB.getDefaultState());
				randomlyPlaceBlock(world, structureBB, rand, 0.1F, 2, 2, k - 1, Blocks.WEB.getDefaultState());
				randomlyPlaceBlock(world, structureBB, rand, 0.1F, 0, 2, k + 1, Blocks.WEB.getDefaultState());
				randomlyPlaceBlock(world, structureBB, rand, 0.1F, 2, 2, k + 1, Blocks.WEB.getDefaultState());
				randomlyPlaceBlock(world, structureBB, rand, 0.05F, 0, 2, k - 2, Blocks.WEB.getDefaultState());
				randomlyPlaceBlock(world, structureBB, rand, 0.05F, 2, 2, k - 2, Blocks.WEB.getDefaultState());
				randomlyPlaceBlock(world, structureBB, rand, 0.05F, 0, 2, k + 2, Blocks.WEB.getDefaultState());
				randomlyPlaceBlock(world, structureBB, rand, 0.05F, 2, 2, k + 2, Blocks.WEB.getDefaultState());
				randomlyPlaceBlock(world, structureBB, rand, 0.05F, 1, 2, k - 1, Blocks.TORCH.getDefaultState());
				randomlyPlaceBlock(world, structureBB, rand, 0.05F, 1, 2, k + 1, Blocks.TORCH.getDefaultState());

				if(rand.nextInt(100) == 0) {
					this.generateChest(world, structureBB, rand, 2, 0, k - 1, LootTableList.CHESTS_ABANDONED_MINESHAFT);
				}

				if(rand.nextInt(100) == 0) {
					this.generateChest(world, structureBB, rand, 0, 0, k + 1, LootTableList.CHESTS_ABANDONED_MINESHAFT);
				}

				if(hasSpiders && !spawnerPlaced) {
					int l = getYWithOffset(0);
					int i1 = k - 1 + rand.nextInt(3);
					int j1 = getXWithOffset(1, i1);
					i1 = getZWithOffset(1, i1);

					if(structureBB.isVecInside(new BlockPos(j1, l, i1))) {
						spawnerPlaced = true;
						world.setBlockState(new BlockPos(j1, l, i1), Blocks.MOB_SPAWNER.getDefaultState(), 2);
						TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(new BlockPos(j1, l, i1));

						if(tileentitymobspawner != null) {
							tileentitymobspawner.getSpawnerBaseLogic().setEntityId(EntityList.getKey(EntityCaveSpider.class));
						}
					}
				}
			}

			for(j = 0; j <= 2; ++j) {
				for(k = 0; k <= i; ++k) {
					int b0 = -1;
					IBlockState block1 = getBlockStateFromPos(world, j, b0, k, structureBB);

					if(block1.getMaterial() == Material.AIR) {
						int b1 = -1;
						setBlockState(world, BlocksCore.voidStone.getDefaultState(), j, b1, k, structureBB);
					}
				}
			}

			if(hasRails) {
				for(j = 0; j <= i; ++j) {
					getBlockStateFromPos(world, 1, -1, j, structureBB);

					randomlyPlaceBlock(world, structureBB, rand, 0.7F, 1, 0, j, Blocks.RAIL.getDefaultState());
					randomlyPlaceBlock(world, structureBB, rand, 0.7F, 0, -1, j, BlocksCore.voidStone.getDefaultState());
					randomlyPlaceBlock(world, structureBB, rand, 0.7F, 1, -1, j, BlocksCore.voidStone.getDefaultState());
					randomlyPlaceBlock(world, structureBB, rand, 0.7F, -1, -1, j, BlocksCore.voidStone.getDefaultState());
				}
			}

			return true;
		}
	}

	public static class Cross extends StructureComponent {

		private EnumFacing corridorDirection;
		private boolean isMultipleFloors;

		public Cross() {}

		@Override
		protected void writeStructureToNBT(NBTTagCompound nbt) {
			nbt.setBoolean("tf", isMultipleFloors);
			nbt.setInteger("D", corridorDirection.getIndex());
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager tm) {
			isMultipleFloors = nbt.getBoolean("tf");
			corridorDirection = EnumFacing.byIndex(nbt.getInteger("D"));
		}

		public Cross(int index, StructureBoundingBox structureBB, EnumFacing facing) {
			super(index);
			corridorDirection = facing;
			boundingBox = structureBB;
			isMultipleFloors = structureBB.getYSize() > 3;
		}

		public static StructureBoundingBox findValidPlacement(List<StructureComponent> components, Random rand, int x, int y, int z, EnumFacing facing) {
			StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y, z, x, y + 2, z);

			if(rand.nextInt(4) == 0) {
				structureboundingbox.maxY += 4;
			}

			switch(facing) {
			case SOUTH:
				structureboundingbox.minX = x - 1;
				structureboundingbox.maxX = x + 3;
				structureboundingbox.maxZ = z + 4;
				break;
			case WEST:
				structureboundingbox.minX = x - 4;
				structureboundingbox.minZ = z - 1;
				structureboundingbox.maxZ = z + 3;
				break;
			case NORTH:
			default:
				structureboundingbox.minX = x - 1;
				structureboundingbox.maxX = x + 3;
				structureboundingbox.minZ = z - 4;
				break;
			case EAST:
				structureboundingbox.maxX = x + 4;
				structureboundingbox.minZ = z - 1;
				structureboundingbox.maxZ = z + 3;
				break;
			}

			return StructureComponent.findIntersecting(components, structureboundingbox) != null ? null : structureboundingbox;
		}

		@Override
		public void buildComponent(StructureComponent parent, List<StructureComponent> components, Random rand) {
			int i = getComponentType();

			switch(corridorDirection) {
			case NORTH:
			default:
				StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, i);
				StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, EnumFacing.WEST, i);
				StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, EnumFacing.EAST, i);
				break;
			case SOUTH:
				StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
				StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, EnumFacing.WEST, i);
				StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, EnumFacing.EAST, i);
				break;
			case WEST:
				StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, i);
				StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
				StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, EnumFacing.WEST, i);
				break;
			case EAST:
				StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, i);
				StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
				StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, EnumFacing.EAST, i);
			}

			if(isMultipleFloors) {
				if(rand.nextBoolean()) {
					StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX + 1, boundingBox.minY + 3 + 1, boundingBox.minZ - 1, EnumFacing.NORTH, i);
				}
				if(rand.nextBoolean()) {
					StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX - 1, boundingBox.minY + 3 + 1, boundingBox.minZ + 1, EnumFacing.WEST, i);
				}
				if(rand.nextBoolean()) {
					StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.maxX + 1, boundingBox.minY + 3 + 1, boundingBox.minZ + 1, EnumFacing.EAST, i);
				}
				if(rand.nextBoolean()) {
					StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX + 1, boundingBox.minY + 3 + 1, boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
				}
			}
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB) {
			if(isLiquidInStructureBoundingBox(world, structureBB)) {
				return false;
			}
			if(isMultipleFloors) {
				fillWithBlocks(world, structureBB, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.minY + 3 - 1, boundingBox.maxZ, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				fillWithBlocks(world, structureBB, boundingBox.minX, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.minY + 3 - 1, boundingBox.maxZ - 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				fillWithBlocks(world, structureBB, boundingBox.minX + 1, boundingBox.maxY - 2, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				fillWithBlocks(world, structureBB, boundingBox.minX, boundingBox.maxY - 2, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ - 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				fillWithBlocks(world, structureBB, boundingBox.minX + 1, boundingBox.minY + 3, boundingBox.minZ + 1, boundingBox.maxX - 1, boundingBox.minY + 3, boundingBox.maxZ - 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			}
			else {
				fillWithBlocks(world, structureBB, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				fillWithBlocks(world, structureBB, boundingBox.minX, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ - 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			}

			fillWithBlocks(world, structureBB, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ + 1, boundingBox.minX + 1, boundingBox.maxY, boundingBox.minZ + 1, BlocksCore.voidStone.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			fillWithBlocks(world, structureBB, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ - 1, boundingBox.minX + 1, boundingBox.maxY, boundingBox.maxZ - 1, BlocksCore.voidStone.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			fillWithBlocks(world, structureBB, boundingBox.maxX - 1, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.minZ + 1, BlocksCore.voidStone.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			fillWithBlocks(world, structureBB, boundingBox.maxX - 1, boundingBox.minY, boundingBox.maxZ - 1, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ - 1, BlocksCore.voidStone.getDefaultState(), Blocks.AIR.getDefaultState(), false);

			for(int i = boundingBox.minX; i <= boundingBox.maxX; ++i) {
				for(int j = boundingBox.minZ; j <= boundingBox.maxZ; ++j) {
					if(getBlockStateFromPos(world, i, boundingBox.minY - 1, j, structureBB).getMaterial() == Material.AIR) {
						setBlockState(world, BlocksCore.voidStone.getDefaultState(), i, boundingBox.minY - 1, j, structureBB);
					}
				}
			}

			return true;
		}
	}

	public static class Room extends StructureComponent {

		private List<StructureBoundingBox> roomsLinkedToTheRoom = new LinkedList<>();
		public Room() {}

		public Room(int index, Random rand, int minX, int minZ) {
			super(index);
			boundingBox = new StructureBoundingBox(minX, 50, minZ, minX + 7 + rand.nextInt(6), 54 + rand.nextInt(6), minZ + 7 + rand.nextInt(6));
		}

		@Override
		public void buildComponent(StructureComponent parent, List<StructureComponent> components, Random rand) {
			int i = getComponentType();
			int k = boundingBox.getYSize() - 3 - 1;

			if(k <= 0) {
				k = 1;
			}

			int j;
			StructureComponent structurecomponent1;
			StructureBoundingBox structureboundingbox;

			for(j = 0; j < boundingBox.getXSize(); j += 4) {
				j += rand.nextInt(boundingBox.getXSize());

				if(j + 3 > boundingBox.getXSize()) {
					break;
				}

				structurecomponent1 = StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX + j, boundingBox.minY + rand.nextInt(k) + 1, boundingBox.minZ - 1, EnumFacing.NORTH, i);

				if(structurecomponent1 != null) {
					structureboundingbox = structurecomponent1.getBoundingBox();
					roomsLinkedToTheRoom.add(new StructureBoundingBox(structureboundingbox.minX, structureboundingbox.minY, boundingBox.minZ, structureboundingbox.maxX, structureboundingbox.maxY, boundingBox.minZ + 1));
				}
			}

			for(j = 0; j < boundingBox.getXSize(); j += 4) {
				j += rand.nextInt(boundingBox.getXSize());

				if(j + 3 > boundingBox.getXSize()) {
					break;
				}

				structurecomponent1 = StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX + j, boundingBox.minY + rand.nextInt(k) + 1, boundingBox.maxZ + 1, EnumFacing.SOUTH, i);

				if(structurecomponent1 != null) {
					structureboundingbox = structurecomponent1.getBoundingBox();
					roomsLinkedToTheRoom.add(new StructureBoundingBox(structureboundingbox.minX, structureboundingbox.minY, boundingBox.maxZ - 1, structureboundingbox.maxX, structureboundingbox.maxY, boundingBox.maxZ));
				}
			}

			for(j = 0; j < boundingBox.getZSize(); j += 4) {
				j += rand.nextInt(boundingBox.getZSize());

				if(j + 3 > boundingBox.getZSize()) {
					break;
				}

				structurecomponent1 = StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX - 1, boundingBox.minY + rand.nextInt(k) + 1, boundingBox.minZ + j, EnumFacing.WEST, i);

				if(structurecomponent1 != null) {
					structureboundingbox = structurecomponent1.getBoundingBox();
					roomsLinkedToTheRoom.add(new StructureBoundingBox(boundingBox.minX, structureboundingbox.minY, structureboundingbox.minZ, boundingBox.minX + 1, structureboundingbox.maxY, structureboundingbox.maxZ));
				}
			}

			for(j = 0; j < boundingBox.getZSize(); j += 4) {
				j += rand.nextInt(boundingBox.getZSize());

				if(j + 3 > boundingBox.getZSize()) {
					break;
				}

				structurecomponent1 = StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX - 1, boundingBox.minY + rand.nextInt(k) + 1, boundingBox.minZ + j, EnumFacing.WEST, i);

				if(structurecomponent1 != null) {
					structureboundingbox = structurecomponent1.getBoundingBox();
					roomsLinkedToTheRoom.add(new StructureBoundingBox(boundingBox.maxX - 1, structureboundingbox.minY, structureboundingbox.minZ, boundingBox.maxX, structureboundingbox.maxY, structureboundingbox.maxZ));
				}
			}
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB) {
			if(isLiquidInStructureBoundingBox(world, structureBB)) {
				return false;
			}
			fillWithBlocks(world, structureBB, boundingBox.minX, boundingBox.minY, boundingBox.minZ, boundingBox.maxX, boundingBox.minY, boundingBox.maxZ, Blocks.DIRT.getDefaultState(), Blocks.AIR.getDefaultState(), true);
			fillWithBlocks(world, structureBB, boundingBox.minX, boundingBox.minY + 1, boundingBox.minZ, boundingBox.maxX, Math.min(boundingBox.minY + 3, boundingBox.maxY), boundingBox.maxZ, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			for(StructureBoundingBox structureboundingbox1 : roomsLinkedToTheRoom) {
				fillWithBlocks(world, structureBB, structureboundingbox1.minX, structureboundingbox1.maxY - 2, structureboundingbox1.minZ, structureboundingbox1.maxX, structureboundingbox1.maxY, structureboundingbox1.maxZ, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			}

			randomlyRareFillWithBlocks(world, structureBB, boundingBox.minX, boundingBox.minY + 4, boundingBox.minZ, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ, Blocks.AIR.getDefaultState(), false);
			return true;
		}

		@Override
		public void offset(int x, int y, int z) {
			super.offset(x, y, z);

			for(StructureBoundingBox structureboundingbox : roomsLinkedToTheRoom) {
				structureboundingbox.offset(x, y, z);
			}
		}

		@Override
		protected void writeStructureToNBT(NBTTagCompound nbt) {
			NBTTagList nbttaglist = new NBTTagList();
			for(StructureBoundingBox structureboundingbox : roomsLinkedToTheRoom) {
				nbttaglist.appendTag(structureboundingbox.toNBTTagIntArray());
			}

			nbt.setTag("Entrances", nbttaglist);
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager tm) {
			NBTTagList nbttaglist = nbt.getTagList("Entrances", 11);

			for(int i = 0; i < nbttaglist.tagCount(); ++i) {
				roomsLinkedToTheRoom.add(new StructureBoundingBox(nbttaglist.getIntArrayAt(i)));
			}
		}
	}

	public static class Stairs extends StructureComponent {

		public Stairs() {}

		public Stairs(int index, Random rand, StructureBoundingBox structureBB, EnumFacing facing){
			super(index);
			setCoordBaseMode(facing);
			boundingBox = structureBB;
		}

		@Override
		protected void writeStructureToNBT(NBTTagCompound nbt) {}

		@Override
		protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager tm) {}

		public static StructureBoundingBox findValidPlacement(List<StructureComponent> components, Random rand, int x, int y, int z, EnumFacing facing) {
			StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y - 5, z, x, y + 2, z);

			switch(facing) {
			case SOUTH:
				structureboundingbox.maxX = x + 2;
				structureboundingbox.maxZ = z + 8;
				break;
			case WEST:
				structureboundingbox.minX = x - 8;
				structureboundingbox.maxZ = z + 2;
				break;
			case NORTH:
			default:
				structureboundingbox.maxX = x + 2;
				structureboundingbox.minZ = z - 8;
				break;
			case EAST:
				structureboundingbox.maxX = x + 8;
				structureboundingbox.maxZ = z + 2;
				break;
			}

			return StructureComponent.findIntersecting(components, structureboundingbox) != null ? null : structureboundingbox;
		}

		@Override
		public void buildComponent(StructureComponent parent, List<StructureComponent> components, Random rand) {
			int i = getComponentType();

			switch(getCoordBaseMode()) {
			case SOUTH:
			default:
				StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX, boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
				break;
			case WEST:
				StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ, EnumFacing.WEST, i);
				break;
			case NORTH:
				StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.minX, boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, i);
				break;
			case EAST:
				StructureModernShaftPieces.getNextMineShaftComponent(parent, components, rand, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ, EnumFacing.EAST, i);
				break;
			}
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB) {
			if(isLiquidInStructureBoundingBox(world, structureBB)) {
				return false;
			}
			fillWithBlocks(world, structureBB, 0, 5, 0, 2, 7, 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			fillWithBlocks(world, structureBB, 0, 0, 7, 2, 2, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

			for(int i = -20; i < 5; ++i) {
				fillWithBlocks(world, structureBB, 0, 5 - i - (i < 4 ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			}

			return true;
		}
	}
}