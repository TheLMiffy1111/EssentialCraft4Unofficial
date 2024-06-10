package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerCrystalFormer extends ContainerInventory {

	public ContainerCrystalFormer(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotBoundEssence(inv, 0, 26, 5));
		addSlotToContainer(new SlotGeneric(inv, 2, 108, 41));
		addSlotToContainer(new SlotGeneric(inv, 3, 126, 41));
		addSlotToContainer(new SlotGeneric(inv, 4, 144, 41));
		addSlotToContainer(new SlotGeneric(inv, 5, 108, 23));
		addSlotToContainer(new SlotGeneric(inv, 6, 144, 23));
		addSlotToContainer(new SlotGeneric(inv, 7, 126, 5));
		addSlotToContainer(new SlotGeneric(inv, 1, 82, 5));
		setupPlayerInventory();
	}
}
