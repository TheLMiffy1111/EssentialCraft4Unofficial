package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import essentialcraft.common.tile.TileWeaponMaker;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerWeaponBench extends ContainerInventory {

	public ContainerWeaponBench(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		TileWeaponMaker maker = (TileWeaponMaker)tile;
		addSlotToContainer(new SlotGeneric(inv, 0, 152, 37));

		if(maker.index == 0) {
			//Elemental Core
			addSlotToContainer(new SlotGeneric(inv, 1, 40, 37));
			//Soul Sphere
			addSlotToContainer(new SlotGeneric(inv, 2, 60, 57));
			//Lens
			addSlotToContainer(new SlotGeneric(inv, 3, 20, 17));
			//Scope
			addSlotToContainer(new SlotGeneric(inv, 4, 60, 17));
			//Base
			addSlotToContainer(new SlotGeneric(inv, 5, 40, 17));
			addSlotToContainer(new SlotGeneric(inv, 6, 60, 37));
			addSlotToContainer(new SlotGeneric(inv, 7, 80, 37));
			//Mechanism
			addSlotToContainer(new SlotGeneric(inv, 8, 80, 17));
			//Handle
			addSlotToContainer(new SlotGeneric(inv, 9, 80, 57));
			sizeInventory = 10;
		}

		if(maker.index == 1) {
			//Elemental Core
			addSlotToContainer(new SlotGeneric(inv, 1, 40, 37));
			//Soul Sphere
			addSlotToContainer(new SlotGeneric(inv, 2, 60, 37));
			//Lens
			addSlotToContainer(new SlotGeneric(inv, 3, 20, 37));
			//Scope
			addSlotToContainer(new SlotGeneric(inv, 4, 40, 17));
			//Base
			addSlotToContainer(new SlotGeneric(inv, 5, 20, 17));
			addSlotToContainer(new SlotGeneric(inv, 6, 60, 17));
			addSlotToContainer(new SlotGeneric(inv, 7, 80, 37));
			addSlotToContainer(new SlotGeneric(inv, 8, 60, 57));
			//Mechanism
			addSlotToContainer(new SlotGeneric(inv, 9, 80, 17));
			//Handle
			addSlotToContainer(new SlotGeneric(inv, 10, 20, 57));
			addSlotToContainer(new SlotGeneric(inv, 11, 40, 57));
			addSlotToContainer(new SlotGeneric(inv, 12, 80, 57));
			sizeInventory = 13;
		}

		if(maker.index == 2) {
			//Elemental Core
			addSlotToContainer(new SlotGeneric(inv, 1, 60, 37));
			//Soul Sphere
			addSlotToContainer(new SlotGeneric(inv, 2, 40, 57));
			//Lens
			addSlotToContainer(new SlotGeneric(inv, 3, 20, 37));
			//Scope
			addSlotToContainer(new SlotGeneric(inv, 4, 60, 17));
			addSlotToContainer(new SlotGeneric(inv, 5, 40, 17));
			addSlotToContainer(new SlotGeneric(inv, 6, 80, 17));
			//Base
			addSlotToContainer(new SlotGeneric(inv, 7, 40, 37));
			addSlotToContainer(new SlotGeneric(inv, 8, 80, 37));
			addSlotToContainer(new SlotGeneric(inv, 9, 100, 37));
			//Mechanism
			addSlotToContainer(new SlotGeneric(inv, 10, 100, 17));
			addSlotToContainer(new SlotGeneric(inv, 11, 60, 57));
			//Handle
			addSlotToContainer(new SlotGeneric(inv, 12, 80, 57));
			addSlotToContainer(new SlotGeneric(inv, 13, 100, 57));
			sizeInventory = 14;
		}

		if(maker.index == 3) {
			//Elemental Core
			addSlotToContainer(new SlotGeneric(inv, 1, 80, 37));
			//Soul Sphere
			addSlotToContainer(new SlotGeneric(inv, 2, 40, 57));
			//Lens
			addSlotToContainer(new SlotGeneric(inv, 3, 20, 37));
			addSlotToContainer(new SlotGeneric(inv, 4, 20, 17));
			addSlotToContainer(new SlotGeneric(inv, 5, 20, 57));
			//Scope
			//No scope for the Gatling
			//Base
			addSlotToContainer(new SlotGeneric(inv, 6, 40, 17));
			addSlotToContainer(new SlotGeneric(inv, 7, 60, 17));
			addSlotToContainer(new SlotGeneric(inv, 8, 80, 17));
			addSlotToContainer(new SlotGeneric(inv, 9, 100, 17));
			addSlotToContainer(new SlotGeneric(inv, 10, 120, 17));
			addSlotToContainer(new SlotGeneric(inv, 11, 40, 37));
			addSlotToContainer(new SlotGeneric(inv, 12, 120, 37));
			//Mechanism
			addSlotToContainer(new SlotGeneric(inv, 13, 60, 37));
			addSlotToContainer(new SlotGeneric(inv, 14, 100, 37));
			//Handle
			addSlotToContainer(new SlotGeneric(inv, 15, 60, 57));
			addSlotToContainer(new SlotGeneric(inv, 16, 80, 57));
			addSlotToContainer(new SlotGeneric(inv, 17, 100, 57));
			addSlotToContainer(new SlotGeneric(inv, 18, 120, 57));
			sizeInventory = 19;
		}
		setupPlayerInventory();
	}
}
