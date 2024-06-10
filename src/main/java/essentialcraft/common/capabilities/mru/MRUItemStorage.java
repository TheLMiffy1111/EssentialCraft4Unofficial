package essentialcraft.common.capabilities.mru;

import DummyCore.Utils.MiscUtils;
import essentialcraft.api.IMRUHandlerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class MRUItemStorage extends MRUStorage implements IMRUHandlerItem, ICapabilityProvider {

	protected boolean storage = false;
	protected ItemStack storageStack;
	protected final boolean storageSettable;

	protected MRUItemStorage() {
		super();
		storageSettable = true;
	}

	public MRUItemStorage(ItemStack storageStack) {
		super();
		this.storageStack = storageStack;
		storageSettable = true;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public MRUItemStorage(ItemStack storageStack, int maxMRU) {
		super(maxMRU);
		this.storageStack = storageStack;
		storageSettable = true;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public MRUItemStorage(ItemStack storageStack, boolean storage) {
		super();
		this.storageStack = storageStack;
		this.storage = storage;
		storageSettable = false;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public MRUItemStorage(ItemStack storageStack, int maxMRU, boolean storage) {
		super(maxMRU);
		this.storageStack = storageStack;
		this.storage = storage;
		storageSettable = false;
		MiscUtils.createNBTTag(this.storageStack);
	}

	@Override
	public int getMaxMRU() {
		readIfChanged();
		return super.getMaxMRU();
	}

	@Override
	public void setMaxMRU(int amount) {
		readIfChanged();
		super.setMaxMRU(amount);
		writeIfChanged();
	}

	@Override
	public int getMRU() {
		readIfChanged();
		return super.getMRU();
	}

	@Override
	public void setMRU(int amount) {
		readIfChanged();
		super.setMRU(amount);
		writeIfChanged();
	}

	@Override
	public int addMRU(int amount, boolean doAdd) {
		readIfChanged();
		int ret = super.addMRU(amount, doAdd);
		writeIfChanged();
		return ret;
	}

	@Override
	public int extractMRU(int amount, boolean doExtract) {
		readIfChanged();
		int ret = super.extractMRU(amount, doExtract);
		writeIfChanged();
		return ret;
	}

	@Override
	public float getBalance() {
		readIfChanged();
		return super.getBalance();
	}

	@Override
	public void setBalance(float balance) {
		readIfChanged();
		super.setBalance(balance);
		writeIfChanged();
	}

	@Override
	public boolean getShade() {
		readIfChanged();
		return super.getShade();
	}

	@Override
	public void setShade(boolean shade) {
		readIfChanged();
		super.setShade(shade);
		writeIfChanged();
	}

	@Override
	public boolean getStorage() {
		readIfChanged();
		return storage;
	}

	@Override
	public void setStorage(boolean storage) {
		readIfChanged();
		if(storageSettable) {
			this.storage = storage;
		}
		writeIfChanged();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if(storageSettable) {
			nbt.setBoolean("storage", storage);
		}
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if(storageSettable) {
			storage = nbt.getBoolean("storage");
		}
	}

	protected NBTTagCompound prevNBT = null;

	protected void writeIfChanged() {
		NBTTagCompound nbt = writeToNBT(storageStack.getTagCompound().copy());
		if(!nbt.equals(prevNBT)) {
			storageStack.setTagCompound(nbt);
			prevNBT = nbt.copy();
		}
	}

	protected void readIfChanged() {
		NBTTagCompound nbt = storageStack.getTagCompound();
		if(!nbt.equals(prevNBT)) {
			readFromNBT(nbt);
			prevNBT = nbt.copy();
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityMRUHandler.MRU_HANDLER_ITEM_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityMRUHandler.MRU_HANDLER_ITEM_CAPABILITY ? (T)this : null;
	}
}
