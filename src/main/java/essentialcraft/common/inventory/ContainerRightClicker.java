package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerRightClicker extends ContainerInventory {

	public ContainerRightClicker(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotBoundEssence(inv, 0, 26, 41));

		if(tile.getBlockMetadata() == 0 || tile.getBlockMetadata() == 1) {
			addSlotToContainer(new SlotGeneric(inv, 1, 80, 24, "minecraft:items/stick"));
			sizeInventory = 3;
		}
		else {
			for(int i = 0; i < 9; ++i) {
				addSlotToContainer(new SlotGeneric(inv, 1 + i, 62 + i%3*18, 6 + i/3*18, "minecraft:items/stick"));
			}
			sizeInventory = 11;
		}
		addSlotToContainer(new SlotGeneric(inv, 10, 156, 4, "minecraft:items/dye_powder_gray"));
		setupPlayerInventory();
	}
}
