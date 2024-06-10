package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerMIMInventoryStorage extends ContainerInventory {

	private int chestInventoryRows;
	private int chestInventoryColumns;

	public ContainerMIMInventoryStorage(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		pInvOffsetZ = 56;
		chestInventoryRows = 6;
		chestInventoryColumns = 9;

		for(int chestRowIndex = 0; chestRowIndex < chestInventoryRows; ++chestRowIndex) {
			for(int chestColumnIndex = 0; chestColumnIndex < chestInventoryColumns; ++chestColumnIndex) {
				addSlotToContainer(new Slot(inv, chestColumnIndex + chestRowIndex*chestInventoryColumns, 8 + chestColumnIndex*18, 18 + chestRowIndex*18));
			}
		}
		setupPlayerInventory();
	}
}
