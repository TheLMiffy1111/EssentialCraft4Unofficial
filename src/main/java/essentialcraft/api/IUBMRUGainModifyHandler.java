package essentialcraft.api;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Not yet a capability.
 */
public interface IUBMRUGainModifyHandler {

	public float getModifiedValue(float original, ItemStack mod, Random rng, EntityPlayer player);
}
