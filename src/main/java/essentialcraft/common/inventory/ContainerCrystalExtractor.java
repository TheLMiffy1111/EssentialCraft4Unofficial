package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerCrystalExtractor extends ContainerInventory {

	public ContainerCrystalExtractor(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotBoundEssence(inv, 0, 108, 5));

		addSlotToContainer(new SlotGeneric(inv, 1, 26, 23));
		addSlotToContainer(new SlotGeneric(inv, 2, 44, 23));
		addSlotToContainer(new SlotGeneric(inv, 3, 62, 23));
		addSlotToContainer(new SlotGeneric(inv, 4, 80, 23));
		addSlotToContainer(new SlotGeneric(inv, 5, 98, 23));
		addSlotToContainer(new SlotGeneric(inv, 6, 116, 23));
		addSlotToContainer(new SlotGeneric(inv, 7, 26, 41));
		addSlotToContainer(new SlotGeneric(inv, 8, 44, 41));
		addSlotToContainer(new SlotGeneric(inv, 9, 62, 41));
		addSlotToContainer(new SlotGeneric(inv, 10, 80, 41));
		addSlotToContainer(new SlotGeneric(inv, 11, 98, 41));
		addSlotToContainer(new SlotGeneric(inv, 12, 116, 41));
		setupPlayerInventory();
	}
}
