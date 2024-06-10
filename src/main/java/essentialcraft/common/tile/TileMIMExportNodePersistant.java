package essentialcraft.common.tile;

import java.util.ArrayList;

import essentialcraft.common.inventory.InventoryMagicFilter;
import essentialcraft.common.item.ItemFilter;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class TileMIMExportNodePersistant extends TileMIMExportNode {

	public TileMIMExportNodePersistant() {
		super();
	}

	@Override
	public void exportAllPossibleItems(TileMIM parent) {
		if(getWorld().getRedstonePowerFromNeighbors(pos) > 0) {
			return;
		}

		IItemHandler inv = getConnectedInventory();
		if(inv == null) {
			inv = getConnectedInventoryNonSided();
		}
		ArrayList<ItemStack> itemsToExport = parent.getAllItems();
		int slots = inv.getSlots();

		if(slots <= 0) {
			return;
		}

		for(ItemStack element : itemsToExport) {
			for(int j = 0; j < slots; ++j) {
				if(inv.insertItem(j, element, true).getCount() < element.getCount()) {
					if(inv.getStackInSlot(j).isEmpty() || inv.getStackInSlot(j).isItemEqual(element) && ItemStack.areItemStackTagsEqual(inv.getStackInSlot(j), element)) {
						if(getStackInSlot(0).isEmpty() || !(getStackInSlot(0).getItem() instanceof ItemFilter)) {
							if(inv.getStackInSlot(j).isEmpty()) {
								ItemStack copied = element.copy();
								if(copied.getCount() >= 2) {
									copied.setCount(1);
								}

								if(parent.retrieveItemStackFromSystem(copied, false, true) == 0) {
									inv.insertItem(j, copied, true);
								}
							}
						}
						else {
							ItemStack copied = element.copy();
							if(ECUtils.canFilterAcceptItem(new InventoryMagicFilter(getStackInSlot(0)), copied, getStackInSlot(0))) {
								if(copied.getCount() >= 2) {
									copied.setCount(1);
								}

								if(inv.getStackInSlot(j).isEmpty()) {
									if(parent.retrieveItemStackFromSystem(copied, false, true) == 0) {
										inv.insertItem(j, copied, true);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
