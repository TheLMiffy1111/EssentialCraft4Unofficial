package essentialcraft.common.capabilities.espe;

import essentialcraft.api.IESPEHandler;
import net.minecraft.nbt.NBTTagCompound;

public class ESPEStorage implements IESPEHandler {

	protected double maxESPE = 10000D;
	protected double espe = 0D;
	protected int tier = 0;

	protected final boolean maxESPESettable;
	protected final boolean tierSettable;

	public ESPEStorage() {
		maxESPESettable = true;
		tierSettable = true;
	}

	public ESPEStorage(double maxESPE) {
		this.maxESPE = maxESPE;
		maxESPESettable = false;
		tierSettable = true;
	}
	public ESPEStorage(int tier) {
		this.tier = tier;
		maxESPESettable = true;
		tierSettable = false;
	}

	public ESPEStorage(double maxESPE, int tier) {
		this.maxESPE = maxESPE;
		this.tier = tier;
		maxESPESettable = false;
		tierSettable = false;
	}

	@Override
	public double getMaxESPE() {
		return maxESPE;
	}

	@Override
	public void setMaxESPE(double amount) {
		if(maxESPESettable) {
			maxESPE = amount;
		}
	}

	@Override
	public double getESPE() {
		return espe;
	}

	@Override
	public void setESPE(double amount) {
		espe = amount;
	}

	@Override
	public double addESPE(double amount, boolean doAdd) {
		if(amount <= 0) {
			return amount;
		}
		if(espe + amount >= maxESPE) {
			double ret = espe + amount - maxESPE;
			if(doAdd) {
				espe = maxESPE;
			}
			return ret;
		}
		if(doAdd) {
			espe += amount;
		}
		return 0;
	}

	@Override
	public double extractESPE(double amount, boolean doExtract) {
		if(amount <= 0) {
			return 0;
		}
		if(espe - amount <= 0) {
			double ret = espe;
			if(doExtract) {
				espe = 0;
			}
			return ret;
		}
		if(doExtract) {
			espe -= amount;
		}
		return amount;
	}

	@Override
	public int getTier() {
		return tier;
	}

	@Override
	public void setTier(int tier) {
		if(tierSettable) {
			this.tier = tier;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if(maxESPESettable) {
			nbt.setDouble("maxESPE", maxESPE);
		}
		nbt.setDouble("espe", espe);
		if(tierSettable) {
			nbt.setInteger("tier", tier);
		}
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if(maxESPESettable) {
			maxESPE = nbt.getDouble("maxESPE");
		}
		espe = nbt.getDouble("espe");
		if(tierSettable) {
			tier = nbt.getInteger("tier");
		}

		//backwards compatibility
		if(nbt.hasKey("energy")) {
			espe = nbt.getFloat("energy");
		}
	}
}
