package essentialcraft.common.tile;

import essentialcraft.common.inventory.InventoryMagicFilter;
import essentialcraft.common.item.ItemFilter;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class TileMIMImportNodePersistant extends TileMIMImportNode {

	public TileMIMImportNodePersistant() {
		super();
	}

	@Override
	public void importAllPossibleItems(TileMIM parent) {
		if(getWorld().getRedstonePowerFromNeighbors(pos) > 0) {
			return;
		}

		IItemHandler inv = getConnectedInventory();
		if(inv == null) {
			getConnectedInventoryNonSided();
		}
		int slots = inv.getSlots();

		if(slots <= 0) {
			return;
		}

		for(int j = 0; j < slots; ++j) {
			ItemStack stk = inv.getStackInSlot(j);
			if(!stk.isEmpty()) {
				if(getStackInSlot(0).isEmpty() || !(getStackInSlot(0).getItem() instanceof ItemFilter)) {
					ItemStack current = stk.copy();
					current.shrink(1);

					if(current.getCount() > 0) {
						if(parent.addItemStackToSystem(current)) {
							inv.extractItem(j, current.getCount(), false);
						}
					}
				}
				else if(ECUtils.canFilterAcceptItem(new InventoryMagicFilter(getStackInSlot(0)), stk, getStackInSlot(0))) {
					ItemStack current = stk.copy();
					current.shrink(1);

					if(current.getCount() > 0) {
						if(parent.addItemStackToSystem(current)) {
							inv.extractItem(j, current.getCount(), false);
						}
					}
				}
			}
		}
	}
}
