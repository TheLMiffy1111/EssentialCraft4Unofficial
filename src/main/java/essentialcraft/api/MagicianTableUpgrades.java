package essentialcraft.api;

import java.util.ArrayList;
import java.util.List;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author Modbder
 * @Description Use this to add new Upgrades to Magician Table. You should do this after initializing your Items.
 */
public class MagicianTableUpgrades {

	public static final List<ItemStack> UPGRADE_STACKS = new ArrayList<>();
	public static final DoubleList UPGRADE_EFFICIENCIES = new DoubleArrayList();
	public static final List<ResourceLocation> UPGRADE_TEXTURES = new ArrayList<>();

	public static void addUpgrade(ItemStack stack, double efficiency, ResourceLocation texture) {
		UPGRADE_STACKS.add(stack.copy());
		UPGRADE_EFFICIENCIES.add(efficiency);
		UPGRADE_TEXTURES.add(texture);
	}

	public static boolean isItemUpgrade(ItemStack stack) {
		for(ItemStack s : UPGRADE_STACKS) {
			if(s.isItemEqual(stack)) {
				return true;
			}
		}
		return false;
	}

	public static ItemStack createStackByUpgradeID(int uid) {
		if(UPGRADE_STACKS.size() > uid) {
			return UPGRADE_STACKS.get(uid);
		}
		return ItemStack.EMPTY;
	}

	public static int getUpgradeIDByItemStack(ItemStack stack) {
		for(int i = 0; i < UPGRADE_STACKS.size(); ++i) {
			if(stack.isItemEqual(UPGRADE_STACKS.get(i))) {
				return i;
			}
		}
		return -1;
	}
}
