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

public class ItemMagicalWater extends ItemMRUGeneric implements IModelRegisterer {

	public ItemMagicalWater() {
		super();
		maxStackSize = 1;
		bFull3D = true;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
		if(entityLiving instanceof EntityPlayer && ECUtils.playerUseMRU((EntityPlayer)entityLiving, stack, 500)) {
			entityLiving.clearActivePotions();
		}
		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/magicwaterbottle", "inventory"));
	}
}
