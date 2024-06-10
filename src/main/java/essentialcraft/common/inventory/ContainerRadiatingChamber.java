package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerRadiatingChamber extends ContainerInventory {

	public ContainerRadiatingChamber(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotBoundEssence(inv, 0, 155, 5));
		addSlotToContainer(new SlotGeneric(inv, 1, 108, 5));
		addSlotToContainer(new SlotGeneric(inv, 2, 108, 41));
		addSlotToContainer(new SlotGeneric(inv, 3, 126, 23));
		setupPlayerInventory();
	}
}
