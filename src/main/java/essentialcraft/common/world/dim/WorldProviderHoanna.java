package essentialcraft.common.world.dim;

import essentialcraft.common.mod.EssentialCraftCore;
import essentialcraft.common.registry.DimensionRegistry;
import essentialcraft.utils.cfg.Config;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderHoanna extends WorldProvider {

	@Override
	public DimensionType getDimensionType() {
		return DimensionRegistry.hoanna;
	}

	@Override
	protected void init() {
		super.setAllowedSpawnTypes(true, false);
		biomeProvider = new BiomeProviderHoanna(world.getWorldInfo());
		doesWaterVaporize = false;
		nether = false;
		setDimension(Config.dimensionID);
	}

	@Override
	public void generateLightBrightnessTable() {
		float f = 0F;

		for(int i = 0; i <= 15; ++i) {
			float f1;
			if(!ECUtils.isEventActive("essentialcraft.event.darkness")) {
				f1 = 1F - i / 15F;
			}
			else {
				f1 = 1.9F - i / 15F;
			}
			lightBrightnessTable[i] = (1F - f1) / (f1 * 3F + 1F) * (1F - f) + f;
		}
	}

	@Override
	public void setAllowedSpawnTypes(boolean allowHostile, boolean allowPeaceful) {
		super.setAllowedSpawnTypes(allowHostile, false);
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorHoanna(world, world.getSeed(), true, world.getWorldInfo().getGeneratorOptions());
	}

	@Override
	public boolean isSurfaceWorld() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		return (IRenderHandler)EssentialCraftCore.proxy.getRenderer(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getCloudRenderer() {
		return (IRenderHandler)EssentialCraftCore.proxy.getRenderer(1);
	}
}
