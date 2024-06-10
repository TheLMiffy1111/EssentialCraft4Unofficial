package essentialcraft.common.inventory;

import java.util.Arrays;
import java.util.UUID;

import DummyCore.Utils.MiscUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;

public class InventoryMagicFilter implements IInventory {

	public ItemStack[] inventory = new ItemStack[9];
	public UUID randomUUID;
	public ItemStack filterStack = ItemStack.EMPTY;

	public InventoryMagicFilter(ItemStack filter) {
		Arrays.fill(inventory, ItemStack.EMPTY);
		if(!filter.hasTagCompound()) {
			NBTTagCompound theTag = MiscUtils.getStackTag(filter);
			randomUUID = UUID.randomUUID();
			theTag.setString("uniqueID", randomUUID.toString());
		}
		readFromNBTTagCompound(MiscUtils.getStackTag(filter));
		filterStack = filter;
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if(inventory[slot].isEmpty()) {
			return ItemStack.EMPTY;
		}
		ItemStack returnStack;
		if(inventory[slot].getCount() > amount) {
			returnStack = inventory[slot].splitStack(amount);
		}
		else {
			returnStack = inventory[slot];
			inventory[slot] = ItemStack.EMPTY;
		}
		markDirty();
		return returnStack;
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {
		ItemStack returnStack = getStackInSlot(slot);
		setInventorySlotContents(slot, ItemStack.EMPTY);
		return returnStack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
	}

	@Override
	public String getName() {
		return "essentialcraft.inventory.filter";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		for(int i = 0; i < inventory.length; i++) {
			ItemStack tempStack = getStackInSlot(i);
			if (!tempStack.isEmpty() && tempStack.getCount() == 0) {
				setInventorySlotContents(i, ItemStack.EMPTY);
			}
		}
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer e) {}

	@Override
	public void closeInventory(EntityPlayer e) {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	public void readFromNBTTagCompound(NBTTagCompound tag) {
		NBTTagCompound inventoryTag = (NBTTagCompound)tag.getTag("inventory");
		if(inventoryTag == null) {
			return;
		}

		if(randomUUID == null) {
			randomUUID = UUID.fromString(tag.getString("uniqueID"));
			//Not actually sure if this can happen, but it is Java, so the more null checks, the better!
			if(randomUUID == null) {
				randomUUID = UUID.randomUUID();
			}
		}

		NBTTagList actualInventory = inventoryTag.getTagList("items", 10);
		for(int i = 0; i < actualInventory.tagCount() && i < inventory.length; i++) {
			NBTTagCompound indexTag = actualInventory.getCompoundTagAt(i);
			int index = indexTag.getInteger("index");
			try {
				inventory[index] = new ItemStack(indexTag);
			}
			catch(Exception e) {
				inventory[index] = ItemStack.EMPTY;
			}
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		NBTTagList items = new NBTTagList();

		for(int i = 0; i < inventory.length; i++) {
			if(!inventory[i].isEmpty() && inventory[i].getCount() > 0) {
				NBTTagCompound indexTag = new NBTTagCompound();
				items.appendTag(indexTag);
				indexTag.setInteger("index", i);
				inventory[i].writeToNBT(indexTag);
			}
		}
		NBTTagCompound inventoryTag = new NBTTagCompound();
		inventoryTag.setTag("items", items);
		tag.setTag("inventory", inventoryTag);
		//Can this ever happen? I guess it only happens on server, so should be impossible, but just in case - here is a NPE check.
		if(randomUUID == null) {
			randomUUID = UUID.randomUUID();
		}
		tag.setString("uniqueID", randomUUID.toString());
		return tag;
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for(int i = 0; i < getSizeInventory(); i++) {
			setInventorySlotContents(i, ItemStack.EMPTY);
		}
	}

	@Override
	public boolean isEmpty() {
		boolean ret = true;
		for(ItemStack stk : inventory) {
			ret &= stk.isEmpty();
		}
		return ret;
	}
}
