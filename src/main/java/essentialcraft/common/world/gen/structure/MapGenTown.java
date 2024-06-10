package essentialcraft.common.world.gen.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import essentialcraft.common.registry.BiomeRegistry;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenTown extends MapGenStructure {
	/** A list of all the biomes villages can spawn in. */
	public static List<Biome> townSpawnBiomes = Arrays.<Biome>asList(
			Biomes.PLAINS, Biomes.DESERT, Biomes.SAVANNA, Biomes.TAIGA, BiomeRegistry.dreadlands, BiomeRegistry.desert, BiomeRegistry.chaosCorruption, BiomeRegistry.frozenCorruption, BiomeRegistry.magicCorruption
			);
	private int size;
	private int distance;
	private int minTownSeparation;

	public MapGenTown() {
		distance = 32;
		minTownSeparation = 8;
	}

	public MapGenTown(Map<String, String> map) {
		this();
		for(Entry<String, String> entry : map.entrySet()) {
			if(entry.getKey().equals("size")) {
				size = MathHelper.getInt(entry.getValue(), size, 0);
			}
			else if(entry.getKey().equals("distance")) {
				distance = MathHelper.getInt(entry.getValue(), distance, 9);
			}
		}
	}

	@Override
	public String getStructureName() {
		return "Town";
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		int k = chunkX;
		int l = chunkZ;

		if(chunkX < 0) {
			chunkX -= distance - 1;
		}

		if(chunkZ < 0) {
			chunkZ -= distance - 1;
		}

		int i1 = chunkX / distance;
		int j1 = chunkZ / distance;
		Random random = world.setRandomSeed(i1, j1, 10387312);
		i1 *= distance;
		j1 *= distance;
		i1 += random.nextInt(distance - 8);
		j1 += random.nextInt(distance - 8);
		if(k == i1 && l == j1) {
			return true;
		}

		return false;
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)  {
		world = worldIn;
		return findNearestStructurePosBySpacing(worldIn, this, pos, distance, 8, 10387312, false, 100, findUnexplored);
	}

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ) {
		return new MapGenTown.Start(world, rand, chunkX, chunkZ, size);
	}

	public static class Start extends StructureStart {
		private boolean hasMoreThanTwoComponents;

		public Start() {}

		public Start(World world, Random rand, int chunkX, int chunkZ, int size) {
			super(chunkX, chunkZ);
			List<StructureTownPieces.PieceWeight> list = StructureTownPieces.getStructureTownWeightedPieceList();
			StructureTownPieces.Start start = new StructureTownPieces.Start(world.getBiomeProvider(), 0, rand, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, size);
			components.add(start);
			start.buildComponent(start, components, rand);
			List<StructureComponent> list1 = start.pendingRoads;
			List<StructureComponent> list2 = start.pendingHouses;
			int l;

			while(!list1.isEmpty() || !list2.isEmpty()) {
				StructureComponent structurecomponent;

				if(list1.isEmpty()) {
					l = rand.nextInt(list2.size());
					structurecomponent = list2.remove(l);
					structurecomponent.buildComponent(start, components, rand);
				}
				else {
					l = rand.nextInt(list1.size());
					structurecomponent = list1.remove(l);
					structurecomponent.buildComponent(start, components, rand);
				}
			}

			updateBoundingBox();
			l = 0;
			for(StructureComponent structurecomponent1 : components) {
				if(!(structurecomponent1 instanceof StructureTownPieces.Road)) {
					++l;
				}
			}

			hasMoreThanTwoComponents = l > 2;
		}

		@Override
		public boolean isSizeableStructure() {
			return hasMoreThanTwoComponents;
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setBoolean("Valid", hasMoreThanTwoComponents);
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			hasMoreThanTwoComponents = nbt.getBoolean("Valid");
		}
	}
}