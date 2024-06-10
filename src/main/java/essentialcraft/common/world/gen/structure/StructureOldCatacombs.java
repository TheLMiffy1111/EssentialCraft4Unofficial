package essentialcraft.common.world.gen.structure;

import java.util.List;
import java.util.Random;

import DummyCore.Utils.MathUtils;
import essentialcraft.common.block.BlocksCore;
import essentialcraft.common.registry.LootTableRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class StructureOldCatacombs {

	public static class Start extends StructureStart {

		public Start() {}

		public Start(World world, Random rand, int chunkX, int chunkZ) {
			super(chunkX, chunkZ);
			int x = (chunkX << 4) + 2;
			int y = MathHelper.getInt(rand, 6, 32);
			int z = (chunkZ << 4) + 2;
			StructureBoundingBox structureBB = new StructureBoundingBox(x, y, z, x+4, y+4, z+4);
			StructureOldCatacombs.Room room = new StructureOldCatacombs.Room(0, rand, structureBB, EnumFacing.DOWN);
			components.add(room);
			room.buildComponent(room, components, rand);
			updateBoundingBox();
		}
	}

	public static void registerCatacombComponents() {
		MapGenStructureIO.registerStructureComponent(StructureOldCatacombs.Room.class, "OCRoom");
		MapGenStructureIO.registerStructureComponent(StructureOldCatacombs.Corridor.class, "OCCorridor");
	}

	public static class Room extends StructureComponent {

		public EnumFacing fromDirection;
		public boolean broken;
		public boolean grown;
		public boolean generateExit;

		public boolean south = false;
		public boolean west = false;
		public boolean north = false;
		public boolean east = false;

		public Room() {}

		public Room(int index, Random rand, StructureBoundingBox structureBB, EnumFacing facing) {
			super(index);
			setCoordBaseMode(EnumFacing.SOUTH);

			boundingBox = structureBB;
			fromDirection = facing;
			broken = rand.nextDouble() < 0.125D;
			grown = rand.nextDouble() < 0.125D;
			generateExit = rand.nextDouble() < 0.0625D;

			switch(facing) {
			case SOUTH:
				south = true;
				break;
			case WEST:
				west = true;
				break;
			case NORTH:
				north = true;
				break;
			case EAST:
				east = true;
				break;
			default:
				break;
			}
		}

		@Override
		protected void writeStructureToNBT(NBTTagCompound tagCompound) {
			tagCompound.setInteger("Direction", fromDirection.getIndex());

			tagCompound.setBoolean("Broken", broken);
			tagCompound.setBoolean("Grown", grown);
			tagCompound.setBoolean("Exit", generateExit);

			tagCompound.setBoolean("South", south);
			tagCompound.setBoolean("West", west);
			tagCompound.setBoolean("North", north);
			tagCompound.setBoolean("East", east);
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager tm) {
			fromDirection = EnumFacing.byIndex(tagCompound.getInteger("Direction"));

			broken = tagCompound.getBoolean("Broken");
			grown = tagCompound.getBoolean("Grown");
			generateExit = tagCompound.getBoolean("Exit");

			south = tagCompound.getBoolean("South");
			west = tagCompound.getBoolean("West");
			north = tagCompound.getBoolean("North");
			east = tagCompound.getBoolean("East");
		}

		public static StructureBoundingBox getValidPlacement(List<StructureComponent> components, Random rand, int x, int y, int z, EnumFacing facing) {
			StructureBoundingBox structureBB = new StructureBoundingBox(x, y, z, x, y+4, z);

			switch(facing) {
			case SOUTH:
				structureBB.maxX = x+4;
				structureBB.maxZ = z+4;
				break;
			case WEST:
				structureBB.minX = x-4;
				structureBB.maxZ = z+4;
				break;
			case NORTH:
			default:
				structureBB.maxX = x+4;
				structureBB.minZ = z-4;
				break;
			case EAST:
				structureBB.maxX = x+4;
				structureBB.maxZ = z+4;
				break;
			}

			return StructureComponent.findIntersecting(components, structureBB) != null ? null : structureBB;
		}

		@Override
		public void buildComponent(StructureComponent parent, List<StructureComponent> compenents, Random rand) {
			int index = getComponentType();
			if(index > 7) {
				return;
			}
			if(fromDirection != EnumFacing.SOUTH) {
				StructureBoundingBox structureBB = StructureOldCatacombs.Corridor.getValidPlacement(compenents, rand, boundingBox.minX, boundingBox.minY, boundingBox.maxZ+1, EnumFacing.SOUTH);
				if(structureBB != null) {
					StructureComponent component = new StructureOldCatacombs.Corridor(index+1, structureBB, EnumFacing.SOUTH);
					compenents.add(component);
					component.buildComponent(this, compenents, rand);
					south = true;
				}
			}
			if(fromDirection != EnumFacing.WEST) {
				StructureBoundingBox structureBB = StructureOldCatacombs.Corridor.getValidPlacement(compenents, rand, boundingBox.minX-1, boundingBox.minY, boundingBox.minZ, EnumFacing.WEST);
				if(structureBB != null) {
					StructureComponent component = new StructureOldCatacombs.Corridor(index+1, structureBB, EnumFacing.WEST);
					compenents.add(component);
					component.buildComponent(this, compenents, rand);
					west = true;
				}
			}
			if(fromDirection != EnumFacing.NORTH) {
				StructureBoundingBox structureBB = StructureOldCatacombs.Corridor.getValidPlacement(compenents, rand, boundingBox.minX, boundingBox.minY, boundingBox.minZ-1, EnumFacing.NORTH);
				if(structureBB != null) {
					StructureComponent component = new StructureOldCatacombs.Corridor(index+1, structureBB, EnumFacing.NORTH);
					compenents.add(component);
					component.buildComponent(this, compenents, rand);
					north = true;
				}
			}
			if(fromDirection != EnumFacing.EAST) {
				StructureBoundingBox structureBB = StructureOldCatacombs.Corridor.getValidPlacement(compenents, rand, boundingBox.maxX+1, boundingBox.minY, boundingBox.minZ, EnumFacing.EAST);
				if(structureBB != null) {
					StructureComponent component = new StructureOldCatacombs.Corridor(index+1, structureBB, EnumFacing.EAST);
					compenents.add(component);
					component.buildComponent(this, compenents, rand);
					east = true;
				}
			}
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB) {
			fillWithBlocks(world, structureBB, 0, 0, 0, 4, 4, 4, BlocksCore.fortifiedStone.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			if(south) {
				fillWithAir(world, structureBB, 1, 1, 4, 3, 3, 4);
			}
			if(west) {
				fillWithAir(world, structureBB, 0, 1, 1, 0, 3, 3);
			}
			if(north) {
				fillWithAir(world, structureBB, 1, 1, 0, 3, 3, 0);
			}
			if(east) {
				fillWithAir(world, structureBB, 4, 1, 1, 4, 3, 3);
			}

			if(generateExit) {
				int i;
				for(i = 10; i < 256; ++i) {
					if(getBlockStateFromPos(world, 2, i, 2, structureBB).getMaterial() == Material.AIR) {
						break;
					}
				}
				fillWithBlocks(world, structureBB, 0, 4, 0, 4, i, 4, BlocksCore.fortifiedStone.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				fillWithAir(world, structureBB, 1, 4, 1, 3, 4, 3);
				fillWithAir(world, structureBB, 1, i, 1, 3, i, 3);
				fillWithBlocks(world, structureBB, 1, 4, 2, 1, i, 2, Blocks.LADDER.getStateFromMeta(5), Blocks.AIR.getDefaultState(), false);

				this.generateChest(world, structureBB, rand, 2, 2, 2, LootTableRegistry.CHEST_CATACOMBS);
				setBlockState(world, BlocksCore.voidStone.getDefaultState(), 2, 1, 2, structureBB);
			}

			if(broken) {
				for(int i = 1; i < 4; ++i) {
					for(int j = 1; j < 4; ++j) {
						for(int k = 1; k < 4; ++k) {
							if(rand.nextInt(j+3) == 0) {
								setBlockState(world, BlocksCore.concrete.getDefaultState(), i, j, k, structureBB);
							}
						}
					}
				}
			}

			if(grown) {
				Vec3d rootVec = new Vec3d(MathUtils.randomDouble(rand)*3, -6, MathUtils.randomDouble(rand)*3);
				for(int vi = 0; vi <= 6; ++vi) {
					setBlockState(world, BlocksCore.root.getDefaultState(), 3+(int)(rootVec.x/vi), 1+(int)(rootVec.x/vi), 3+(int)(rootVec.x/vi), structureBB);
				}
				for(int i = 0; i < 5; ++i) {
					for(int j = 0; j < 5; ++j) {
						for(int k = 0; k < 5; ++k) {
							if(rand.nextInt(3) == 0) {
								Block b = getBlockStateFromPos(world, i, j, k, structureBB).getBlock();
								if(b != Blocks.AIR && b != BlocksCore.concrete && b != BlocksCore.root) {
									setBlockState(world, Blocks.LEAVES.getStateFromMeta(4), i, j, k, structureBB);
								}
							}
						}
					}
				}
			}

			return true;
		}
	}

	public static class Corridor extends StructureComponent {

		public static int corridorMinLength = 32;
		public static int corridorMaxLength = 64;

		public EnumFacing direction;

		public Corridor() {}

		public Corridor(int index, StructureBoundingBox structureBB, EnumFacing facing) {
			super(index);
			setCoordBaseMode(EnumFacing.SOUTH);

			boundingBox = structureBB;
			direction = facing;
		}

		@Override
		protected void writeStructureToNBT(NBTTagCompound tagCompound) {
			tagCompound.setInteger("Direction", direction.getIndex());
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager tm) {
			direction = EnumFacing.byIndex(tagCompound.getInteger("Direction"));
		}

		public static StructureBoundingBox getValidPlacement(List<StructureComponent> components, Random rand, int x, int y, int z, EnumFacing facing) {
			StructureBoundingBox structureBB = new StructureBoundingBox(x, y, z, x, y+4, z);
			int i = rand.nextInt(corridorMaxLength-corridorMinLength) + corridorMinLength;

			switch(facing) {
			case SOUTH:
				structureBB.maxX = x+4;
				structureBB.maxZ = z+i-1;
				break;
			case WEST:
				structureBB.minX = x-i+1;
				structureBB.maxZ = z+4;
				break;
			case NORTH:
			default:
				structureBB.maxX = x+4;
				structureBB.minZ = z-i+1;
				break;
			case EAST:
				structureBB.maxX = x+i-1;
				structureBB.maxZ = z+4;
				break;
			}

			return StructureComponent.findIntersecting(components, structureBB) != null ? null : structureBB;
		}

		@Override
		public void buildComponent(StructureComponent parent, List<StructureComponent> compenents, Random rand) {
			int index = getComponentType();
			if(index > 6) {
				return;
			}
			switch(direction) {
			case SOUTH: {
				StructureBoundingBox structureBB = StructureOldCatacombs.Room.getValidPlacement(compenents, rand, boundingBox.maxX+1, boundingBox.minY, boundingBox.minZ, EnumFacing.SOUTH);
				if(structureBB != null) {
					StructureComponent component = new StructureOldCatacombs.Room(index+1, rand, structureBB, EnumFacing.NORTH);
					compenents.add(component);
					component.buildComponent(this, compenents, rand);
				}
				break;
			}
			case WEST: {
				StructureBoundingBox structureBB = StructureOldCatacombs.Room.getValidPlacement(compenents, rand, boundingBox.minX-1, boundingBox.minY, boundingBox.minZ, EnumFacing.WEST);
				if(structureBB != null) {
					StructureComponent component = new StructureOldCatacombs.Room(index+1, rand, structureBB, EnumFacing.EAST);
					compenents.add(component);
					component.buildComponent(this, compenents, rand);
				}
				break;
			}
			case NORTH:
			default: {
				StructureBoundingBox structureBB = StructureOldCatacombs.Room.getValidPlacement(compenents, rand, boundingBox.minX, boundingBox.minY, boundingBox.minZ-1, EnumFacing.NORTH);
				if(structureBB != null) {
					StructureComponent component = new StructureOldCatacombs.Room(index+1, rand, structureBB, EnumFacing.SOUTH);
					compenents.add(component);
					component.buildComponent(this, compenents, rand);
				}
				break;
			}
			case EAST: {
				StructureBoundingBox structureBB = StructureOldCatacombs.Room.getValidPlacement(compenents, rand, boundingBox.maxX+1, boundingBox.minY, boundingBox.minZ, EnumFacing.EAST);
				if(structureBB != null) {
					StructureComponent component = new StructureOldCatacombs.Room(index+1, rand, structureBB, EnumFacing.WEST);
					compenents.add(component);
					component.buildComponent(this, compenents, rand);
				}
				break;
			}
			}
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB) {
			fillWithBlocks(world, structureBB, 0, 0, 0, boundingBox.getXSize()-1, 4, boundingBox.getZSize()-1, BlocksCore.fortifiedStone.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			switch(direction.getAxis()) {
			case X:
				fillWithAir(world, structureBB, 0, 1, 1, 0, 3, 3);
				fillWithAir(world, structureBB, boundingBox.getXSize()-1, 1, 1, boundingBox.getXSize()-1, 3, 3);
				break;
			case Z:
				fillWithAir(world, structureBB, 1, 1, 0, 3, 3, 0);
				fillWithAir(world, structureBB, 1, 1, boundingBox.getZSize()-1, 3, 3, boundingBox.getZSize()-1);
				break;
			default:
				break;
			}
			return true;
		}
	}
}
