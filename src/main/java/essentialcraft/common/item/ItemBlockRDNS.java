package essentialcraft.common.item;

import essentialcraft.common.block.BlockRedstoneDeviceNotSided;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockRDNS extends ItemBlock{

	public ItemBlockRDNS(Block block) {
		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public int getMetadata(int par1) {
		return par1;
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		return super.getTranslationKey(stack)+"."+BlockRedstoneDeviceNotSided.NAMES[stack.getItemDamage()];
	}
}
