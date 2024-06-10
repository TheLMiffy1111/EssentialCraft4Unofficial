package essentialcraft.common.item;

import DummyCore.Client.IModelRegisterer;
import essentialcraft.utils.common.WindRelations;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemWindKeeper extends Item implements IModelRegisterer {

	public int rel;

	public ItemWindKeeper(int windReleased) {
		super();
		rel = windReleased;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		stack.shrink(1);
		player.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1, 1);
		if(!world.isRemote) {
			WindRelations.increasePlayerWindRelations(player, rel);
			player.sendMessage(new TextComponentString("You hear a very pale 'Thank you'...").setStyle(new Style().setColor(TextFormatting.DARK_AQUA).setItalic(true)));
		}
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/" + getRegistryName().getPath(), "inventory"));
	}
}
