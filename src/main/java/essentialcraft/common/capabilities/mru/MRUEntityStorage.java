package essentialcraft.common.capabilities.mru;

import essentialcraft.api.IMRUHandlerEntity;
import net.minecraft.nbt.NBTTagCompound;

public class MRUEntityStorage extends MRUStorage implements IMRUHandlerEntity {

	protected boolean flag = false;
	protected boolean stay = true;

	public MRUEntityStorage() {
		super();
	}

	public MRUEntityStorage(int maxMRU) {
		super(maxMRU);
	}

	@Override
	public boolean getFlag() {
		return flag;
	}

	@Override
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public boolean canAlwaysStay() {
		return stay;
	}

	@Override
	public void setAlwaysStay(boolean stay) {
		this.stay = stay;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("flag", getFlag());
		nbt.setBoolean("stay", canAlwaysStay());
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		setFlag(nbt.getBoolean("flag"));
		setAlwaysStay(nbt.getBoolean("stay"));

		//backwards compatibility
		if(nbt.hasKey("Balance")) {
			balance = nbt.getFloat("Balance");
		}
	}
}
