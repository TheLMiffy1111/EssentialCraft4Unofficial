package essentialcraft.common.item;

import DummyCore.Client.IModelRegisterer;
import essentialcraft.common.registry.PotionRegistry;
import essentialcraft.utils.common.ECUtils;
import essentialcraft.utils.common.WindRelations;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemLiquidAir extends Item implements IModelRegisterer {

	public ItemLiquidAir() {
		super();
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
		if(entityLiving instanceof EntityPlayer && !world.isRemote) {
			((EntityPlayer)entityLiving).inventory.decrStackSize(((EntityPlayer)entityLiving).inventory.currentItem, 1);
			ECUtils.calculateAndAddPE((EntityPlayer)entityLiving, PotionRegistry.paranormalLightness, 8*60*20, 2*60*20);
			WindRelations.increasePlayerWindRelations((EntityPlayer) entityLiving, 100);
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
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/air_potion", "inventory"));
	}
}
