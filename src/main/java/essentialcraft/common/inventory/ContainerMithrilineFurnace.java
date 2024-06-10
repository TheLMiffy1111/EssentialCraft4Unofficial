package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerMithrilineFurnace extends ContainerInventory {

	public ContainerMithrilineFurnace(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotGeneric(inv, 0, 80, 64));
		addSlotToContainer(new SlotGeneric(inv, 1, 80, 21));
		setupPlayerInventory();
	}
}
