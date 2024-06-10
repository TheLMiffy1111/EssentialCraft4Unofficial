package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerFilter extends ContainerInventory {

	public InventoryMagicFilter inventory;

	public ContainerFilter(EntityPlayer p, InventoryMagicFilter inv) {
		super(p, inv);
		inventory = inv;
	}

	@Override
	public boolean canInteractWith(EntityPlayer p) {
		return true;
	}

	public void saveToNBT(ItemStack itemStack) {
		if(!itemStack.hasTagCompound()) {
			itemStack.setTagCompound(new NBTTagCompound());
		}
		inventory.writeToNBT(itemStack.getTagCompound());
	}

	@Override
	public void setupSlots() {
		for(int o = 0; o < 9; ++o) {
			addSlotToContainer(new Slot(inv, o, 62+o%3*18, 17+o/3*18));
		}
		setupPlayerInventory();
	}
}
