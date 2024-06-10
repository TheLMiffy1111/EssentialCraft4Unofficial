package essentialcraft.common.world.gen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenManager implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if(world != null) {
			if(world.provider != null) {
				int dim = world.provider.getDimension();
				if(dim == -1) {
					handleNetherGen(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
				}
				else if(dim == 1) {
					handleEndGen(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
				}
				else {
					handlePossibleOverworldGen(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
				}
			}
		}
	}

	public void handleNetherGen(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		WorldGenElementalDrops.handleGeneration(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
	}

	public void handlePossibleOverworldGen(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		WorldGenElementalDrops.handleGeneration(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		WorldGenElderMRUCU.handleGeneration(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
	}

	public void handleEndGen(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		WorldGenElementalDrops.handleGeneration(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
	}
}
