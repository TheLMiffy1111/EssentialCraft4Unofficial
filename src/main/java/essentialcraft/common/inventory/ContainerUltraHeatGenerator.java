package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.tileentity.TileEntity;

public class ContainerUltraHeatGenerator extends ContainerInventory {

	public ContainerUltraHeatGenerator(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotGeneric(inv, 0, 108, 41));
		addSlotToContainer(new SlotFurnaceOutput(player, inv, 1, 144, 41));
		setupPlayerInventory();
	}
}
