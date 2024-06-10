package essentialcraft.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Not yet a capability.
 */
public interface IWindResistHandler {

	public boolean resistWind(EntityPlayer player, ItemStack stack);
}
