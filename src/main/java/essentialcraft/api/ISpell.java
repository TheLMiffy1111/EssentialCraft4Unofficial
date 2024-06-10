package essentialcraft.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ISpell {

	public EnumSpellType getSpellType(ItemStack stack);

	public int getUBMRURequired(ItemStack stack);

	public void onSpellUse(int currentUBMRU, int attunement, EntityPlayer player, ItemStack spell, ItemStack holder);

	public int getRequiredContainerTier(ItemStack stack);

	public int getAttunementRequired(ItemStack stack);

	public boolean requiresSpecificAttunement(ItemStack sstacktk);

	public boolean canUseSpell(ItemStack spell, EntityPlayer player);
}
