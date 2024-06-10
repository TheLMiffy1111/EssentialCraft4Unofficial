package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerMagmaticSmeltery extends ContainerInventory {

	public ContainerMagmaticSmeltery(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotBoundEssence(inv, 0, 108, 23));
		addSlotToContainer(new SlotGeneric(inv, 1, 44, 23));
		addSlotToContainer(new SlotGeneric(inv, 2, 62, 23));
		addSlotToContainer(new SlotGeneric(inv, 3, 126, 5));
		addSlotToContainer(new SlotGeneric(inv, 4, 126, 23));
		addSlotToContainer(new SlotGeneric(inv, 5, 155, 5));
		addSlotToContainer(new SlotGeneric(inv, 6, 155, 23));
		addSlotToContainer(new SlotGeneric(inv, 7, 140, 41));
		setupPlayerInventory();
	}
}
