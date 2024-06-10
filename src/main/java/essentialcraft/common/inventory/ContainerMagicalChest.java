package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerMagicalChest extends ContainerInventory {

	private int chestInventoryRows;
	private int chestInventoryColumns;

	public ContainerMagicalChest(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		if(tile.getBlockMetadata() == 0) {
			pInvOffsetX = 0;
			pInvOffsetZ = 56;
			chestInventoryRows = 6;
			chestInventoryColumns = 9;
		}
		else if(tile.getBlockMetadata() == 1) {
			pInvOffsetX = 40;
			pInvOffsetZ = 90;
			chestInventoryRows = 9;
			chestInventoryColumns = 13;
		}

		for(int chestRowIndex = 0; chestRowIndex < chestInventoryRows; ++chestRowIndex) {
			for(int chestColumnIndex = 0; chestColumnIndex < chestInventoryColumns; ++chestColumnIndex) {
				if(tile.getBlockMetadata() == 0) {
					addSlotToContainer(new Slot(inv, chestColumnIndex + chestRowIndex*chestInventoryColumns, 8 + chestColumnIndex*18, 18 + chestRowIndex*18));
				}
				else if(tile.getBlockMetadata() == 1) {
					addSlotToContainer(new Slot(inv, chestColumnIndex + chestRowIndex*chestInventoryColumns, 12 + chestColumnIndex*18, 8 + chestRowIndex*18));
				}
			}
		}
		setupPlayerInventory();
	}
}
