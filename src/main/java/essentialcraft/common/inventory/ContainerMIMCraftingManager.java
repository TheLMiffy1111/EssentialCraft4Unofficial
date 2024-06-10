package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import essentialcraft.common.item.ItemCraftingFrame;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerMIMCraftingManager extends ContainerInventory {

	private int chestInventoryRows;
	private int chestInventoryColumns;

	public ContainerMIMCraftingManager(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		pInvOffsetZ = 56;
		chestInventoryRows = 6;
		chestInventoryColumns = 9;

		for(int chestRowIndex = 0; chestRowIndex < chestInventoryRows; ++chestRowIndex) {
			for(int chestColumnIndex = 0; chestColumnIndex < chestInventoryColumns; ++chestColumnIndex) {
				addSlotToContainer(new Slot(inv, chestColumnIndex + chestRowIndex*chestInventoryColumns, 8 + chestColumnIndex*18, 18 + chestRowIndex*18) {
					@Override
					public boolean isItemValid(ItemStack stk) {
						return stk.getItem() instanceof ItemCraftingFrame && stk.hasTagCompound();
					}
				});
			}
		}
		setupPlayerInventory();
	}
}
