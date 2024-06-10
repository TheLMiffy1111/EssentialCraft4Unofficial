package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import essentialcraft.common.tile.TileMIMScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerMIMScreen extends ContainerInventory {

	public ContainerMIMScreen(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void onContainerClosed(EntityPlayer entityPlayer) {
		super.onContainerClosed(entityPlayer);
		if(inv != null) {
			TileMIMScreen screen = (TileMIMScreen)inv;
			if(screen.parent != null) {
				screen.parent.closeAllStorages(entityPlayer);
			}
		}
	}

	@Override
	public void setupSlots() {
		pInvOffsetX = 40;
		pInvOffsetZ = 90;
		if(inv != null) {
			TileMIMScreen screen = (TileMIMScreen)inv;
			if(screen.parent != null) {
				screen.parent.openAllStorages(player);
			}

			addSlotToContainer(new SlotBoundEssence(inv, 0, 8, 134));
			addSlotToContainer(new SlotGeneric(inv, 1, 8, 26));
		}
		setupPlayerInventory();
	}
}
