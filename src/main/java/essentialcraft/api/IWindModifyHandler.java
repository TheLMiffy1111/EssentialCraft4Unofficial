package essentialcraft.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Not yet a capability.
 */
public interface IWindModifyHandler {

	public float getModifier(ItemStack stack, EntityPlayer player);
}
