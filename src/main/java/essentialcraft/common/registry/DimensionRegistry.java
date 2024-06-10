package essentialcraft.common.registry;

import DummyCore.Utils.DummyPortalHandler;
import essentialcraft.common.block.BlocksCore;
import essentialcraft.common.world.dim.PortalGeneratorHoanna;
import essentialcraft.common.world.dim.WorldProviderHoanna;
import essentialcraft.utils.cfg.Config;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionRegistry {
	public static DimensionType hoanna;
	public static void register() {
		hoanna = DimensionType.register("hoanna", "_hoanna", Config.dimensionID, WorldProviderHoanna.class, false);
		DimensionManager.registerDimension(Config.dimensionID, hoanna);
		DummyPortalHandler.registerPortal(BlocksCore.portal, Config.dimensionID, 0, 200, false, PortalGeneratorHoanna.INSTANCE, new ResourceLocation("essentialcraft:blocks/portal"), 0xFFFFFF);
	}
}
