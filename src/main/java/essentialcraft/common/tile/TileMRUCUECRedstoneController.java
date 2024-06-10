package essentialcraft.common.tile;

import essentialcraft.api.EnumStructureType;
import essentialcraft.api.IStructurePiece;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileMRUCUECRedstoneController extends TileEntity implements IStructurePiece, ITickable {
	public TileMRUCUECController controller;
	public int setting;
	public int tickTimer;

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		setting = tag.getInteger("setting");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("setting", setting);
		return tag;
	}

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
	public void update() {
		++tickTimer;
		if(tickTimer >= 20) {
			tickTimer = 0;
			getWorld().notifyNeighborsOfStateChange(pos, getWorld().getBlockState(pos).getBlock(), false);
		}
	}

	public boolean outputRedstone() {
		if(controller != null && controller.getMRUCU() != null && (float)controller.mruStorage.getMRU()/(float)controller.mruStorage.getMaxMRU()*10 >= setting) {
			return true;
		}

		return false;
	}
}
