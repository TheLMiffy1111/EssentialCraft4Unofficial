package essentialcraft.common.world.gen.structure;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenOldCatacombs extends MapGenStructure {

	private double chance = 0.01F;

	public MapGenOldCatacombs() {}

	public MapGenOldCatacombs(Map<String, String> settings) {
		for(Entry<String, String> entry : settings.entrySet()) {
			if(entry.getKey().equals("chance")) {
				chance = MathHelper.getDouble(entry.getValue(), chance);
			}
		}
	}

	@Override
	public String getStructureName() {
		return "OldCatacombs";
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		return rand.nextDouble() < chance;
	}

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ) {
		return new StructureOldCatacombs.Start(world, rand, chunkX, chunkZ);
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored) {
		int j = pos.getX() >> 4;
		int k = pos.getZ() >> 4;

		for(int l = 0; l <= 1000; ++l) {
			for(int i1 = -l; i1 <= l; ++i1) {
				boolean flag = i1 == -l || i1 == l;

				for(int j1 = -l; j1 <= l; ++j1) {
					boolean flag1 = j1 == -l || j1 == l;

					if(flag || flag1) {
						int k1 = j + i1;
						int l1 = k + j1;
						rand.setSeed(k1 ^ l1 ^ worldIn.getSeed());
						rand.nextInt();

						if(canSpawnStructureAtCoords(k1, l1) && (!findUnexplored || !worldIn.isChunkGeneratedAt(k1, l1))) {
							return new BlockPos((k1 << 4) + 8, 64, (l1 << 4) + 8);
						}
					}
				}
			}
		}

		return null;
	}
}
