package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerMagicianTable extends ContainerInventory {

	public ContainerMagicianTable(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotBoundEssence(inv, 0, 153, 5));
		addSlotToContainer(new SlotGeneric(inv, 1, 44, 23));
		addSlotToContainer(new SlotGeneric(inv, 2, 26, 5));
		addSlotToContainer(new SlotGeneric(inv, 3, 62, 5));
		addSlotToContainer(new SlotGeneric(inv, 4, 26, 41));
		addSlotToContainer(new SlotGeneric(inv, 5, 62, 41));
		addSlotToContainer(new SlotGeneric(inv, 6, 89, 5));
		setupPlayerInventory();
	}
}
