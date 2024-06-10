package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import DummyCore.Utils.UnformedItemStack;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerCraftingFrame extends ContainerInventory {

	public InventoryCraftingFrame inventory;

	public static class SlotFake extends Slot {
		ContainerCraftingFrame parent;
		EntityPlayer player;
		public SlotFake(EntityPlayer p, ContainerCraftingFrame c, IInventory inv, int id, int x, int y) {
			super(inv, id, x, y);
			parent = c;
			player = p;
		}

		@Override
		public ItemStack onTake(EntityPlayer pickuper, ItemStack stk) {
			inventory.setInventorySlotContents(slotNumber, ItemStack.EMPTY);
			onSlotChanged();
			return stk;
		}

		@Override
		public boolean canTakeStack(EntityPlayer player) {
			InventoryCrafting crafting = new InventoryCrafting(parent, 3, 3);

			for(int i = 0; i < 9; ++i) {
				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(i%3*3+i/3));
			}

			ItemStack result = CraftingManager.findMatchingResult(crafting, player.getEntityWorld());

			parent.inventory.setInventorySlotContents(9, !result.isEmpty() ? result.copy() : ItemStack.EMPTY);

			crafting = null;

			if(slotNumber == 9 && result.isEmpty()) {
				inventory.setInventorySlotContents(slotNumber, ItemStack.EMPTY);
			}

			if(slotNumber != 9) {
				inventory.setInventorySlotContents(slotNumber, ItemStack.EMPTY);

				crafting = new InventoryCrafting(parent, 3, 3);
				for(int i = 0; i < 9; ++i) {
					crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(i));
				}

				result = CraftingManager.findMatchingResult(crafting, player.getEntityWorld());

				parent.inventory.setInventorySlotContents(9, !result.isEmpty() ? result.copy() : ItemStack.EMPTY);

				crafting = null;
			}

			onSlotChanged();

			return false;
		}

		@Override
		public boolean isItemValid(ItemStack stk) {
			if(!stk.isEmpty()) {
				if(slotNumber != 9) {
					ItemStack setTo = stk.copy();
					setTo.setCount(1);
					inventory.setInventorySlotContents(slotNumber, setTo);

					InventoryCrafting crafting = new InventoryCrafting(parent, 3, 3);

					for(int i = 0; i < 9; ++i) {
						crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(i%3*3+i/3));
					}

					ItemStack result = CraftingManager.findMatchingResult(crafting, player.getEntityWorld());

					parent.inventory.setInventorySlotContents(9, !result.isEmpty() ? result.copy() : ItemStack.EMPTY);

					crafting = null;

					onSlotChanged();

					parent.inventorySlots.get(9).onSlotChanged();
				}
				else {
					IRecipe settedRec = null;

					if(!getHasStack()) {
						IRecipe rec = ECUtils.findRecipeByIS(stk, 0);
						if(rec == null && !parent.player.getHeldItemMainhand().getTagCompound().getBoolean("ignoreOreDict")) {
							rec = ECUtils.findRecipeByIS(stk, 2);
						}

						settedRec = rec;

						if(rec != null) {
							for(int i = 0; i < 9; ++i) {
								parent.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
								IRecipe srec = rec;
								if(srec.getIngredients().get(i).getMatchingStacks().length > 0) {
									UnformedItemStack ust = new UnformedItemStack(srec.getIngredients().get(i).getMatchingStacks());
									parent.inventory.setInventorySlotContents(i, ust.possibleStacks.get(player.getEntityWorld().rand.nextInt(ust.possibleStacks.size())));
								}
								else {
									parent.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
								}

								parent.inventorySlots.get(i).onSlotChanged();
							}
						}
					}

					inventory.setInventorySlotContents(slotNumber, settedRec == null || settedRec.getRecipeOutput().isEmpty() ? ItemStack.EMPTY : settedRec.getRecipeOutput().copy());
				}
			}
			return false;
		}
	}

	public ContainerCraftingFrame(EntityPlayer p, InventoryCraftingFrame inv) {
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
	public ItemStack slotClick(int slotID, int buttonPressed, ClickType flag, EntityPlayer player) {
		Slot tmpSlot;
		if(slotID >= 0 && slotID < inventorySlots.size()) {
			tmpSlot = inventorySlots.get(slotID);
		}
		else {
			tmpSlot = null;
		}

		if(tmpSlot != null && tmpSlot.isHere(player.inventory, player.inventory.currentItem)) {
			return tmpSlot.getStack();
		}

		return super.slotClick(slotID, buttonPressed, flag, player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setupSlots() {
		for(int o = 0; o < 9; ++o) {
			addSlotToContainer(new SlotFake(player, this, inv, o, 30+o/3*18, 17+o%3*18));
		}

		addSlotToContainer(new SlotFake(player, this, inv, 9, 124, 17+18));
		setupPlayerInventory();
	}
}
