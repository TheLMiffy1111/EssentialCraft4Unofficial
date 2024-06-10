package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerMagicalTeleporter extends ContainerInventory {

	public ContainerMagicalTeleporter(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotBoundEssence(inv, 0, 108, 23));
		addSlotToContainer(new SlotBoundEssence(inv, 1, 140, 23));
		setupPlayerInventory();
	}
}
