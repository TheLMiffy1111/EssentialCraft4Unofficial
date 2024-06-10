package essentialcraft.common.world.biome;

import essentialcraft.common.block.BlocksCore;
import essentialcraft.common.world.gen.WorldGenDreadCacti;
import net.minecraft.world.biome.Biome;

public class BiomeHoannaDreadlands extends Biome
{
	public int grassColor = 16777215;
	public int waterColor = 16777215;
	public int leavesColor = 16777215;

	public BiomeHoannaDreadlands setGrassColor(int i)
	{
		grassColor = i;
		return this;
	}

	public BiomeHoannaDreadlands setWaterColor(int i)
	{
		waterColor = i;
		return this;
	}

	public BiomeHoannaDreadlands setLeavesColor(int i)
	{
		leavesColor = i;
		return this;
	}

	public BiomeHoannaDreadlands(BiomeProperties par1)
	{
		super(par1);
		topBlock = BlocksCore.dreadDirt.getDefaultState();
		fillerBlock = BlocksCore.dreadDirt.getDefaultState();
		decorator.treesPerChunk = -999;
		decorator.deadBushPerChunk = 2;
		decorator.reedsPerChunk = -999;
		decorator.cactiPerChunk = -999;
		decorator.cactusGen = new WorldGenDreadCacti();

		spawnableCreatureList.clear();
	}

	public int getBiomeGrassColor()
	{
		return grassColor;
	}

	public int getBiomeFoliageColor()
	{
		return leavesColor;
	}

	@Override
	public int getWaterColorMultiplier()
	{
		return waterColor;
	}

	@Override
	public int getModdedBiomeGrassColor(int original)
	{
		return grassColor;
	}

	@Override
	public int getModdedBiomeFoliageColor(int original)
	{
		return leavesColor;
	}
}
