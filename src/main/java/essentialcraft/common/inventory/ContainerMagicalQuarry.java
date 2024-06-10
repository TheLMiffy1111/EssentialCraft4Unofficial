package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerMagicalQuarry extends ContainerInventory {

	public ContainerMagicalQuarry(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotBoundEssence(inv, 0, 108, 23));
		addSlotToContainer(new SlotGeneric(inv, 1, 108, 41));
		addSlotToContainer(new SlotGeneric(inv, 2, 126, 41));
		addSlotToContainer(new SlotGeneric(inv, 3, 144, 41));
		setupPlayerInventory();
	}
}
