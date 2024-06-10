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

public class ItemEmeraldHeart extends ItemMRUGeneric implements IModelRegisterer {

	public ItemEmeraldHeart() {
		super();
		maxStackSize = 1;
		bFull3D = true;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack p_77654_1_, World p_77654_2_, EntityLivingBase p_77654_3_)
	{
		if(p_77654_3_ instanceof EntityPlayer && ECUtils.playerUseMRU((EntityPlayer)p_77654_3_, p_77654_1_, 500))
		{
			p_77654_3_.heal(10);
		}
		return p_77654_1_;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_)
	{
		return 32;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{
		return EnumAction.EAT;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer, enumHand
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World p_77659_2_, EntityPlayer p_77659_3_, EnumHand hand)
	{
		p_77659_3_.setActiveHand(hand);
		return super.onItemRightClick(p_77659_2_, p_77659_3_, hand);
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/emeraldheart", "inventory"));
	}
}
