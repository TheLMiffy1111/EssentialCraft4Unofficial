package essentialcraft.common.tile;

import essentialcraft.api.EnumStructureType;
import essentialcraft.api.IMRUHandler;
import essentialcraft.api.IStructurePiece;
import essentialcraft.common.capabilities.mru.CapabilityMRUHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class TileMRUCUECEjector extends TileEntity implements IStructurePiece {
	public TileMRUCUECController controller;
	public ControllerMRUStorageOutputOnlyWrapper mruStorageWrapper = new ControllerMRUStorageOutputOnlyWrapper();

	@Override
	public EnumStructureType getStructure() {
		return EnumStructureType.MRUCUEC;
	}

	@Override
	public TileEntity structureController() {
		return controller;
	}

	@Override
	public void setStructureController(TileEntity tile, EnumStructureType structure) {
		if(tile instanceof TileMRUCUECController && structure == getStructure()) {
			controller = (TileMRUCUECController)tile;
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityMRUHandler.MRU_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityMRUHandler.MRU_HANDLER_CAPABILITY ? (T)mruStorageWrapper : super.getCapability(capability, facing);
	}

	public class ControllerMRUStorageOutputOnlyWrapper implements IMRUHandler {

		@Override
		public int getMaxMRU() {
			if(controller != null) {
				return controller.mruStorage.getMaxMRU();
			}
			return 0;
		}

		@Override
		public void setMaxMRU(int amount) {
			if(controller != null) {
				controller.mruStorage.setMaxMRU(amount);
			}
		}

		@Override
		public int getMRU() {
			if(controller != null) {
				return controller.mruStorage.getMRU();
			}
			return 0;
		}

		@Override
		public void setMRU(int amount) {
			if(controller != null) {
				controller.mruStorage.setMRU(amount);
			}
		}

		@Override
		public int addMRU(int amount, boolean doAdd) {
			return amount;
		}

		@Override
		public int extractMRU(int amount, boolean doExtract) {
			if(controller != null) {
				return controller.mruStorage.extractMRU(amount, doExtract);
			}
			return 0;
		}

		@Override
		public float getBalance() {
			if(controller != null) {
				controller.mruStorage.getBalance();
			}
			return 1F;
		}

		@Override
		public void setBalance(float balance) {
			if(controller != null) {
				controller.mruStorage.setBalance(balance);
			}
		}

		@Override
		public boolean getShade() {
			if(controller != null) {
				return controller.mruStorage.getShade();
			}
			return false;
		}

		@Override
		public void setShade(boolean shade) {
			if(controller != null) {
				controller.mruStorage.setShade(shade);
			}
		}

		@Override
		public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
			if(controller != null) {
				controller.mruStorage.writeToNBT(nbt);
			}
			return nbt;
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			if(controller != null) {
				controller.mruStorage.readFromNBT(nbt);
			}
		}
	}
}
