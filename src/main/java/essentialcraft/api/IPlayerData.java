package essentialcraft.api;

import java.util.List;
import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;

public interface IPlayerData {

	public int getOverhaulDamage();

	public void modifyOverhaulDamage(int newDamage);

	public int getPlayerRadiation();

	public void modifyRadiation(int newRad);

	public int getPlayerWindPoints();

	public void modifyWindpoints(int newWind);

	public boolean isWindbound();

	public void modifyWindbound(boolean newValue);

	public int getPlayerUBMRU();

	public void modifyUBMRU(int newUBMRU);

	public int getMatrixTypeID();

	public void modifyMatrixType(int newType);

	public List<ICorruptionEffect> getEffects();

	public void readFromNBTTagCompound(NBTTagCompound tag);

	public NBTTagCompound writeToNBTTagCompound(NBTTagCompound tag);

	public UUID carrierUUID();

}
