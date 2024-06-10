package essentialcraft.common.tile;

import net.minecraft.util.EnumFacing;

public class TileFluidEjector extends TileMRUGeneric {

	public EnumFacing getRotation() {
		int metadata = getBlockMetadata();
		metadata %= 6;
		return EnumFacing.byIndex(metadata);
	}

	public TileFluidEjector() {
		super(0);
		setSlotsNum(0);
	}

	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}
}
