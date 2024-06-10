package essentialcraft.common.capabilities.mru;

import essentialcraft.api.IMRUHandler;
import essentialcraft.common.item.ItemBoundGem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MRUTileRangelessStorage extends MRUTileStorage {

	public MRUTileRangelessStorage() {
		super();
		setRange(Integer.MAX_VALUE);
	}

	public MRUTileRangelessStorage(int maxMRU) {
		super(maxMRU);
		setRange(Integer.MAX_VALUE);
	}

	@Override
	public void mruIn(BlockPos pos, World world, ItemStack boundGem) {
		if(boundGem.getItem() instanceof ItemBoundGem && boundGem.getTagCompound() != null) {
			int[] o = ItemBoundGem.getCoords(boundGem);
			BlockPos o1 = new BlockPos(o[0], o[1], o[2]);
			if(!pos.equals(o1) && world.getTileEntity(o1) != null && world.getTileEntity(o1).hasCapability(CapabilityMRUHandler.MRU_HANDLER_CAPABILITY, null)) {
				IMRUHandler other = world.getTileEntity(o1).getCapability(CapabilityMRUHandler.MRU_HANDLER_CAPABILITY, null);
				if(getMRU() < getMaxMRU()) {
					int req = getMaxMRU() - getMRU();
					int extracted = other.extractMRU(req, true);
					if(extracted+getMRU() > 0) {
						setBalance((other.getBalance()*extracted+getBalance()*getMRU())/(extracted+getMRU()));
					}
					addMRU(extracted, true);
				}
			}
		}
	}

	@Override
	public void spawnMRUParticles(BlockPos pos, World world, ItemStack boundGem) {
		if(world.isRemote) {
			if(boundGem.getItem() instanceof ItemBoundGem && boundGem.getTagCompound() != null) {
				int[] o = ItemBoundGem.getCoords(boundGem);
				new BlockPos(o[0],o[1],o[2]);
				BlockPos pos1 = new BlockPos(o[0], o[1], o[2]);
				doSpawnMRUParticles(pos, pos1, world);
			}
		}
	}
}
