package essentialcraft.common.item;

import DummyCore.Client.IModelRegisterer;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemMagicalShield extends ItemMRUGeneric implements IModelRegisterer {

	public ItemMagicalShield() {
		super();
		maxStackSize = 1;
		bFull3D = true;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		player.hurtResistantTime = 20;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 40;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BLOCK;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(ECUtils.playerUseMRU(player, player.getHeldItem(hand), 2000)) {
			player.setActiveHand(hand);
		}
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/magicalshield", "inventory"));
	}
}
