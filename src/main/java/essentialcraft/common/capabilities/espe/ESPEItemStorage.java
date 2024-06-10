package essentialcraft.common.capabilities.espe;

import DummyCore.Utils.MiscUtils;
import essentialcraft.api.IESPEHandlerItem;
import essentialcraft.common.capabilities.mru.CapabilityMRUHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ESPEItemStorage extends ESPEStorage implements IESPEHandlerItem, ICapabilityProvider {

	protected boolean storage = false;
	protected ItemStack storageStack;
	protected final boolean storageSettable;

	protected ESPEItemStorage() {
		super();
		storageSettable = true;
	}

	public ESPEItemStorage(ItemStack storageStack) {
		super();
		this.storageStack = storageStack;
		storageSettable = true;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public ESPEItemStorage(ItemStack storageStack, double maxESPE) {
		super(maxESPE);
		this.storageStack = storageStack;
		storageSettable = true;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public ESPEItemStorage(ItemStack storageStack, int tier) {
		super(tier);
		this.storageStack = storageStack;
		storageSettable = true;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public ESPEItemStorage(ItemStack storageStack, double maxESPE, int tier) {
		super(maxESPE, tier);
		this.storageStack = storageStack;
		storageSettable = true;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public ESPEItemStorage(ItemStack storageStack, boolean storage) {
		super();
		this.storageStack = storageStack;
		this.storage = storage;
		storageSettable = false;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public ESPEItemStorage(ItemStack storageStack, double maxESPE, boolean storage) {
		super(maxESPE);
		this.storageStack = storageStack;
		this.storage = storage;
		storageSettable = false;
		MiscUtils.createNBTTag(this.storageStack);
	}
	public ESPEItemStorage(ItemStack storageStack, int tier, boolean storage) {
		super(tier);
		this.storageStack = storageStack;
		this.storage = storage;
		storageSettable = false;
		MiscUtils.createNBTTag(this.storageStack);
	}

	public ESPEItemStorage(ItemStack storageStack, double maxESPE, int tier, boolean storage) {
		super(maxESPE, tier);
		this.storageStack = storageStack;
		this.storage = storage;
		storageSettable = false;
		MiscUtils.createNBTTag(this.storageStack);
	}

	@Override
	public double getMaxESPE() {
		readIfChanged();
		return super.getMaxESPE();
	}

	@Override
	public void setMaxESPE(double amount) {
		readIfChanged();
		super.setMaxESPE(amount);
		writeIfChanged();
	}

	@Override
	public double getESPE() {
		readIfChanged();
		return super.getESPE();
	}

	@Override
	public void setESPE(double amount) {
		readIfChanged();
		super.setESPE(amount);
		writeIfChanged();
	}

	@Override
	public double addESPE(double amount, boolean doAdd) {
		readIfChanged();
		double ret = super.addESPE(amount, doAdd);
		writeIfChanged();
		return ret;
	}

	@Override
	public double extractESPE(double amount, boolean doExtract) {
		readIfChanged();
		double ret = super.extractESPE(amount, doExtract);
		writeIfChanged();
		return ret;
	}

	@Override
	public int getTier() {
		readIfChanged();
		return super.getTier();
	}

	@Override
	public void setTier(int tier) {
		readIfChanged();
		super.setTier(tier);
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
