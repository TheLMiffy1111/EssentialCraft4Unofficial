package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import essentialcraft.common.item.ItemBoundGem;
import essentialcraft.common.tile.TileMIMCraftingManager;
import essentialcraft.common.tile.TileMIMExportNode;
import essentialcraft.common.tile.TileMIMImportNode;
import essentialcraft.common.tile.TileMIMInventoryStorage;
import essentialcraft.common.tile.TileMIMScreen;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class ContainerMIM extends ContainerInventory {

	public static class SlotBGTEClassDepenant extends Slot {
		public SlotBGTEClassDepenant(IInventory inv, int id, int x, int y, Class<? extends TileEntity> c) {
			super(inv, id, x, y);
			dependant = c;
		}

		Class<? extends TileEntity> dependant;

		@Override
		public boolean isItemValid(ItemStack stk) {
			if(!(stk.getItem() instanceof ItemBoundGem) || stk.getTagCompound() == null || !stk.getTagCompound().hasKey("pos")) {
				return false;
			}

			TileEntity tile = (TileEntity)inventory;

			if(stk.getTagCompound().getInteger("dim") != tile.getWorld().provider.getDimension()) {
				return false;
			}

			int[] coords = ItemBoundGem.getCoords(stk);

			TileEntity t = tile.getWorld().getTileEntity(new BlockPos(coords[0], coords[1], coords[2]));

			if((t == null) || !dependant.isAssignableFrom(t.getClass())) {
				return false;
			}

			return true;
		}
	}

	public ContainerMIM(InventoryPlayer invPlayer, TileEntity tile) {
		super(invPlayer, tile);
	}

	@Override
	public void setupSlots() {
		pInvOffsetX = 10;
		pInvOffsetZ = 90;
		addSlotToContainer(new SlotBoundEssence(inv, 0, 5, 147));

		Class<TileMIMInventoryStorage> isc = TileMIMInventoryStorage.class;
		Class<TileMIMScreen> msc = TileMIMScreen.class;
		Class<TileMIMCraftingManager> cmc = TileMIMCraftingManager.class;
		Class<TileMIMExportNode> enc = TileMIMExportNode.class;
		Class<TileMIMImportNode> inc = TileMIMImportNode.class;

		addSlotToContainer(new SlotBGTEClassDepenant(inv, 1, 28, 20, isc));
		addSlotToContainer(new SlotBGTEClassDepenant(inv, 2, 46, 20, isc));
		addSlotToContainer(new SlotBGTEClassDepenant(inv, 3, 64, 20, isc));
		addSlotToContainer(new SlotBGTEClassDepenant(inv, 4, 28, 38, isc));
		addSlotToContainer(new SlotBGTEClassDepenant(inv, 5, 46, 38, isc));
		addSlotToContainer(new SlotBGTEClassDepenant(inv, 6, 64, 38, isc));

		addSlotToContainer(new SlotBGTEClassDepenant(inv, 7, 91, 20, msc));
		addSlotToContainer(new SlotBGTEClassDepenant(inv, 8, 109, 20, msc));
		addSlotToContainer(new SlotBGTEClassDepenant(inv, 9, 91, 38, msc));
		addSlotToContainer(new SlotBGTEClassDepenant(inv, 10, 109, 38, msc));

		addSlotToContainer(new SlotBGTEClassDepenant(inv, 11, 136, 20, cmc));
		addSlotToContainer(new SlotBGTEClassDepenant(inv, 12, 154, 20, cmc));
		addSlotToContainer(new SlotBGTEClassDepenant(inv, 13, 172, 20, cmc));
		addSlotToContainer(new SlotBGTEClassDepenant(inv, 14, 136, 38, cmc));
		addSlotToContainer(new SlotBGTEClassDepenant(inv, 15, 154, 38, cmc));
		addSlotToContainer(new SlotBGTEClassDepenant(inv, 16, 172, 38, cmc));

		for(int i = 0; i < 18; ++i) {
			addSlotToContainer(new SlotBGTEClassDepenant(inv, 17+i, 28+i%9*18, 74+i/9*18, inc));
		}

		for(int i = 0; i < 18; ++i) {
			addSlotToContainer(new SlotBGTEClassDepenant(inv, 35+i, 28+i%9*18, 128+i/9*18, enc));
		}
		setupPlayerInventory();
	}
}
