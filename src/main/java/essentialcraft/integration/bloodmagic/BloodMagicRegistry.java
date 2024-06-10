package essentialcraft.integration.bloodmagic;

import org.apache.logging.log4j.LogManager;

import WayofTime.bloodmagic.altar.AltarTier;
import WayofTime.bloodmagic.core.registry.AltarRecipeRegistry;
import essentialcraft.common.item.ItemsCore;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;


public class BloodMagicRegistry {

	public static void register() {
		if(Loader.isModLoaded("bloodmagic")) {
			try {
				ItemStack emptySoulStone = new ItemStack(ItemsCore.soulStone, 1, 0);
				ItemStack filledSoulStone = new ItemStack(ItemsCore.soulStone, 1, 1);
				AltarRecipeRegistry.registerRecipe(new AltarRecipeRegistry.AltarRecipe(emptySoulStone, filledSoulStone, AltarTier.ONE, 250, 2, 1));
				LogManager.getLogger().trace("Successfully registered Blood Magic integration!");
			}
			catch(Throwable e) {
				LogManager.getLogger().error("Unable to add Blood Magic Integration.", e);
			}
		}
	}
}
