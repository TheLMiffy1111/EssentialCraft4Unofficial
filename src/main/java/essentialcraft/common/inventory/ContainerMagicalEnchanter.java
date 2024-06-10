package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerMagicalEnchanter extends ContainerInventory {

	public ContainerMagicalEnchanter(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotBoundEssence(inv, 0, 155, 23));
		addSlotToContainer(new SlotGeneric(inv, 1, 26, 23));
		addSlotToContainer(new SlotGeneric(inv, 2, 94, 23));
		setupPlayerInventory();
	}
}
