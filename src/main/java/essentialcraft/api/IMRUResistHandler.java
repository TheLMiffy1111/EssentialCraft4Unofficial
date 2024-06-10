package essentialcraft.api;

import java.util.List;

import net.minecraft.item.ItemStack;

/**
 * @author TheLMiffy1111
 * Not yet a capability.
 */
public interface IMRUResistHandler {

	List<Float> getMRUResistances(ItemStack stack);
}
