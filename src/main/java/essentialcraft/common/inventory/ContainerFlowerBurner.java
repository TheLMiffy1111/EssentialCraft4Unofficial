package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerFlowerBurner extends ContainerInventory {

	public ContainerFlowerBurner(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setupSlots() {
		setupPlayerInventory();
	}
}
