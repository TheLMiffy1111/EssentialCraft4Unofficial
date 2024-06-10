package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerMRUDimTransciever extends ContainerInventory {

	public ContainerMRUDimTransciever(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotBoundEssence(inv, 0, 29, 32));
		setupPlayerInventory();
	}
}
