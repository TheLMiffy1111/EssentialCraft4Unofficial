package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerFurnaceMagic extends ContainerInventory {

	public ContainerFurnaceMagic(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotBoundEssence(inv, 0, 108, 41));
		addSlotToContainer(new SlotGeneric(inv, 1, 108, 5));
		addSlotToContainer(new SlotGeneric(inv, 2, 153, 5));
		setupPlayerInventory();
	}
}
