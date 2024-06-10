package essentialcraft.common.world.dim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import essentialcraft.common.world.gen.layer.GenLayerHoanna;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Biomes;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeProviderHoanna extends BiomeProvider {

	private GenLayer biomeToUse;
	private GenLayer biomeIndexLayer;
	private BiomeCache biomeCache;
	private List<Biome> biomesToSpawnIn;

	public BiomeProviderHoanna() {
		biomeCache = new BiomeCache(this);
		biomesToSpawnIn = new ArrayList<>();
	}

	public BiomeProviderHoanna(WorldInfo info) {
		this();
		WorldType type = info.getTerrainType();
		String options = info.getGeneratorOptions();
		ChunkGeneratorSettings settings = null;
		if(type == WorldType.CUSTOMIZED && !options.isEmpty()) {
			settings = ChunkGeneratorSettings.Factory.jsonToFactory(options).build();
		}
		GenLayer[] agenlayer = GenLayerHoanna.initializeAllBiomeGenerators(info.getSeed(), type, settings);
		biomeToUse = agenlayer[0];
		biomeIndexLayer = agenlayer[1];
	}

	@Override
	public List<Biome> getBiomesToSpawnIn() {
		return biomesToSpawnIn;
	}

	@Override
	public Biome getBiome(BlockPos pos) {
		return this.getBiome(pos, null);
	}

	@Override
	public Biome getBiome(BlockPos pos, Biome defaultBiome) {
		return biomeCache.getBiome(pos.getX(), pos.getZ(), defaultBiome);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getTemperatureAtHeight(float temp, int y) {
		return temp;
	}

	@Override
	public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height) {
		IntCache.resetIntCache();

		if(biomes == null || biomes.length < width * height) {
			biomes = new Biome[width * height];
		}

		int[] aint = biomeToUse.getInts(x, z, width, height);

		try {
			for(int i = 0; i < width * height; ++i) {
				biomes[i] = Biome.getBiome(aint[i], Biomes.DEFAULT);
			}

			return biomes;
		}
		catch(Throwable throwable) {
			CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
			CrashReportCategory crashreportcategory = crashreport.makeCategory("RawBiomeBlock");
			crashreportcategory.addCrashSection("biomes[] size", Integer.valueOf(biomes.length));
			crashreportcategory.addCrashSection("x", Integer.valueOf(x));
			crashreportcategory.addCrashSection("z", Integer.valueOf(z));
			crashreportcategory.addCrashSection("w", Integer.valueOf(width));
			crashreportcategory.addCrashSection("h", Integer.valueOf(height));
			throw new ReportedException(crashreport);
		}
	}

	@Override
	public Biome[] getBiomes(@Nullable Biome[] oldBiomeList, int x, int z, int width, int depth) {
		return this.getBiomes(oldBiomeList, x, z, width, depth, true);
	}

	@Override
	public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag) {
		IntCache.resetIntCache();

		if(listToReuse == null || listToReuse.length < width * length) {
			listToReuse = new Biome[width * length];
		}

		if(cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0) {
			Biome[] abiome = biomeCache.getCachedBiomes(x, z);
			System.arraycopy(abiome, 0, listToReuse, 0, width * length);
			return listToReuse;
		}
		int[] aint = biomeIndexLayer.getInts(x, z, width, length);

		for(int i = 0; i < width * length; ++i) {
			listToReuse[i] = Biome.getBiome(aint[i], Biomes.DEFAULT);
		}

		return listToReuse;
	}

	@Override
	public boolean areBiomesViable(int x, int z, int radius, List<Biome> allowed) {
		IntCache.resetIntCache();
		int i = x - radius >> 2;
			int j = z - radius >> 2;
			int k = x + radius >> 2;
			int l = z + radius >> 2;
			int i1 = k - i + 1;
			int j1 = l - j + 1;
			int[] aint = biomeToUse.getInts(i, j, i1, j1);

			try {
				for(int k1 = 0; k1 < i1 * j1; ++k1) {
					Biome biome = Biome.getBiome(aint[k1]);

					if(!allowed.contains(biome)) {
						return false;
					}
				}

				return true;
			}
			catch (Throwable throwable) {
				CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
				CrashReportCategory crashreportcategory = crashreport.makeCategory("Layer");
				crashreportcategory.addCrashSection("Layer", biomeToUse.toString());
				crashreportcategory.addCrashSection("x", Integer.valueOf(x));
				crashreportcategory.addCrashSection("z", Integer.valueOf(z));
				crashreportcategory.addCrashSection("radius", Integer.valueOf(radius));
				crashreportcategory.addCrashSection("allowed", allowed);
				throw new ReportedException(crashreport);
			}
	}

	@Override
	public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random) {
		IntCache.resetIntCache();
		int i = x - range >> 2;
		int j = z - range >> 2;
		int k = x + range >> 2;
		int l = z + range >> 2;
		int i1 = k - i + 1;
		int j1 = l - j + 1;
		int[] aint = biomeToUse.getInts(i, j, i1, j1);
		BlockPos blockpos = null;
		int k1 = 0;

		for(int l1 = 0; l1 < i1 * j1; ++l1) {
			int i2 = i + l1 % i1 << 2;
			int j2 = j + l1 / i1 << 2;
			Biome biome = Biome.getBiome(aint[l1]);

			if(biomes.contains(biome) && (blockpos == null || random.nextInt(k1 + 1) == 0)) {
				blockpos = new BlockPos(i2, 0, j2);
				++k1;
			}
		}

		return blockpos;
	}

	@Override
	public void cleanupCache() {
		biomeCache.cleanupCache();
	}
}
