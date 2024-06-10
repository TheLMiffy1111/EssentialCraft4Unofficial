package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerPotionSpreader extends ContainerInventory {

	public ContainerPotionSpreader(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotBoundEssence(inv, 0, 155, 23));
		for(int j = 0; j < 4; ++j) {
			for(int k = 0; k < 2; ++k) {
				addSlotToContainer(new SlotGeneric(inv, j + k*4 + 1, 26 + j*18, 23 + k*18));
			}
		}
		setupPlayerInventory();
	}
}
