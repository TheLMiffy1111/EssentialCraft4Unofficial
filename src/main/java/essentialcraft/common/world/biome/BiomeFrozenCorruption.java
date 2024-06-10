package essentialcraft.common.world.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class BiomeFrozenCorruption extends Biome
{
	public BiomeFrozenCorruption(BiomeProperties par1) {
		super(par1);
	}

	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		return 0x0077ff;
	}

	@Override
	public int getFoliageColorAtPos(BlockPos pos) {
		return 0x0077ff;
	}

	@Override
	public int getWaterColorMultiplier() {
		return 0x0077ff;
	}

	@Override
	public int getModdedBiomeGrassColor(int original) {
		return 0x0077ff;
	}

	@Override
	public int getModdedBiomeFoliageColor(int original) {
		return 0x0077ff;
	}
}
