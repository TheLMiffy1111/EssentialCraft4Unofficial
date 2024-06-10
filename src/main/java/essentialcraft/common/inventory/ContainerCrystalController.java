package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerCrystalController extends ContainerInventory {

	public ContainerCrystalController(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotBoundEssence(inv, 0, 108, 5));
		addSlotToContainer(new SlotGeneric(inv, 1, 140, 41));
		setupPlayerInventory();
	}
}
