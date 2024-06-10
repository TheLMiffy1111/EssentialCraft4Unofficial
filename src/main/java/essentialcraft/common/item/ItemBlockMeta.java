package essentialcraft.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockMeta extends ItemBlockGeneric {

	public ItemBlockMeta(Block block) {
		super(block);
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		return super.getTranslationKey(stack)+"."+stack.getItemDamage();
	}
}
