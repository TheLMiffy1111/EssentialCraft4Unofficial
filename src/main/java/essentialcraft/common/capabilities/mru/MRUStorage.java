package essentialcraft.common.capabilities.mru;

import essentialcraft.api.IMRUHandler;
import net.minecraft.nbt.NBTTagCompound;

public class MRUStorage implements IMRUHandler {

	protected int maxMRU = 5000;
	protected int mru = 0;
	protected float balance = 1F;
	protected boolean shade = false;

	protected final boolean maxMRUSettable;

	public MRUStorage() {
		maxMRUSettable = true;
	}

	public MRUStorage(int maxMRU) {
		this.maxMRU = maxMRU;
		maxMRUSettable = false;
	}

	@Override
	public int getMaxMRU() {
		return maxMRU;
	}

	@Override
	public void setMaxMRU(int amount) {
		if(maxMRUSettable && amount >= 0) {
			maxMRU = amount;
		}
	}

	@Override
	public int getMRU() {
		return mru;
	}

	@Override
	public void setMRU(int amount) {
		if(amount <= 0) {
			mru = 0;
		}
		else if(amount >= maxMRU) {
			mru = maxMRU;
		}
		else {
			mru = amount;
		}
	}

	@Override
	public int addMRU(int amount, boolean doAdd) {
		if(amount <= 0) {
			return amount;
		}
		if(mru + amount >= maxMRU) {
			int ret = mru + amount - maxMRU;
			if(doAdd) {
				mru = maxMRU;
			}
			return ret;
		}
		if(doAdd) {
			mru += amount;
		}
		return 0;
	}

	@Override
	public int extractMRU(int amount, boolean doExtract) {
		if(amount <= 0) {
			return 0;
		}
		if(mru - amount <= 0) {
			int ret = mru;
			if(doExtract) {
				mru = 0;
			}
			return ret;
		}
		if(doExtract) {
			mru -= amount;
		}
		return amount;
	}

	@Override
	public float getBalance() {
		return balance;
	}

	@Override
	public void setBalance(float balance) {
		if(balance <= 0F) {
			this.balance = 0F;
		}
		else if(balance >= 2F) {
			this.balance = 2F;
		}
		else {
			this.balance = balance;
		}
	}

	@Override
	public boolean getShade() {
		return shade;
	}

	@Override
	public void setShade(boolean shade) {
		this.shade = shade;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if(maxMRUSettable) {
			nbt.setInteger("maxMRU", maxMRU);
		}
		nbt.setInteger("mru", mru);
		nbt.setFloat("balance", balance);
		nbt.setBoolean("shade", shade);
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if(maxMRUSettable) {
			maxMRU = nbt.getInteger("maxMRU");
		}
		mru = nbt.getInteger("mru");
		balance = nbt.getFloat("balance");
		shade = nbt.getBoolean("shade");
	}
}
