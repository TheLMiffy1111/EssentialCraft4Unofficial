package essentialcraft.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 *
 * @author Modbder
 * @Description use this interface to create new blocks for the cold distillator to operate with
 *
 */
public interface IColdBlock {

	/**
	 * This is used to check how many mru per tick will this block add
	 * @param meta - the metadata of the block
	 * @return amount of mru per tick will this block add
	 */
	public float getColdModifier(IBlockAccess world, BlockPos pos);
}
