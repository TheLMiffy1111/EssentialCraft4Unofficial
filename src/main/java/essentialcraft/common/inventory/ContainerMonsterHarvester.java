package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerMonsterHarvester extends ContainerInventory {

	public ContainerMonsterHarvester(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotBoundEssence(inv, 0, 126, 5));
		addSlotToContainer(new SlotGeneric(inv, 1, 108, 23));
		addSlotToContainer(new SlotGeneric(inv, 2, 126, 23));
		addSlotToContainer(new SlotGeneric(inv, 3, 144, 23));
		addSlotToContainer(new SlotGeneric(inv, 4, 117, 41));
		addSlotToContainer(new SlotGeneric(inv, 5, 135, 41));
		setupPlayerInventory();
	}
}
