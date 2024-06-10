package essentialcraft.common.tile;

import essentialcraft.common.capabilities.espe.CapabilityESPEHandler;
import essentialcraft.common.capabilities.espe.ESPEStorage;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.config.Configuration;

public class TileCreativeESPESource extends TileEntity implements ITickable {

	public static double cfgMaxESPE = 1000000D;
	protected ESPEStorage espeStorage = new ESPEStorage(cfgMaxESPE, Integer.MAX_VALUE>>1);

	public TileCreativeESPESource() {
		super();
	}

	@Override
	public void update() {
		espeStorage.setESPE(espeStorage.getMaxESPE());
	}

	public static void setupConfig(Configuration cfg) {
		try {
			String category = "tileentities.creativeespesource";
			cfgMaxESPE = cfg.get(category, "MaxESPE", 1000000D).setMinValue(Double.MIN_NORMAL).getDouble();
		}
		catch(Exception e) {
			return;
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityESPEHandler.ESPE_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityESPEHandler.ESPE_HANDLER_CAPABILITY ? (T)espeStorage : super.getCapability(capability, facing);
	}
}
