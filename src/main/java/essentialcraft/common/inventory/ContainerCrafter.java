package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerCrafter extends ContainerInventory {

	public ContainerCrafter(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		for(int o = 0; o < 9; ++o) {
			addSlotToContainer(new SlotGeneric(inv, o, 30+o%3*18, 17+o/3*18));
		}

		addSlotToContainer(new SlotGeneric(inv, 9, 124, 35));
		addSlotToContainer(new SlotGeneric(inv, 10, 94, 17));
		setupPlayerInventory();
	}
}
