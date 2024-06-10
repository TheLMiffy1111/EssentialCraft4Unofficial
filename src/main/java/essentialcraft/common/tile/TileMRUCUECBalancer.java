package essentialcraft.common.tile;

import essentialcraft.api.EnumStructureType;
import essentialcraft.api.IStructurePiece;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileMRUCUECBalancer extends TileEntity implements IStructurePiece, ITickable {
	public TileMRUCUECController controller;

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
		if(controller != null && controller.getMRUCU() != null) {
			controller.getMRUCU().setFlag(true);
		}
	}
}
