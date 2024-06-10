package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerMagicalAssembler extends ContainerInventory {

	public ContainerMagicalAssembler(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotBoundEssence(inv, 0, 153, 5));
		addSlotToContainer(new SlotGeneric(inv, 1, 153, 41));
		for(int h = 2; h < 18; ++h) {
			addSlotToContainer(new SlotGeneric(inv, h, 1000000, 1000000));
		}
		setupPlayerInventory();
	}
}
