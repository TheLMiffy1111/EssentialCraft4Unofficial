package essentialcraft.common.item;

import DummyCore.Client.IModelRegisterer;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import essentialcraft.common.mod.EssentialCraftCore;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemMagicalWings extends ItemMRUGeneric implements IBauble, IModelRegisterer {

	public ItemMagicalWings() {
		super();
		maxStackSize = 1;
		bFull3D = true;
	}

	@Override
	public void onUpdate(ItemStack s, World world, Entity entity, int indexInInventory, boolean isCurrentItem)
	{
		super.onUpdate(s, world, entity, indexInInventory, isCurrentItem);
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer e = (EntityPlayer)entity;
			{
				if((e.getHeldItemMainhand() == s || e.getHeldItemOffhand() == s) && ECUtils.playerUseMRU(e, s, 1))
				{
					if(!e.isSneaking())
					{
						e.motionY += 0.1F;
						e.fallDistance = 0F;
					}
					else
					{
						e.motionY = -0.2F;
						e.fallDistance = 0F;
					}
					world.spawnParticle(EnumParticleTypes.REDSTONE, e.posX+MathUtils.randomDouble(world.rand)/2, e.posY-1+MathUtils.randomDouble(world.rand), e.posZ+MathUtils.randomDouble(world.rand)/2, 0, 1, 1);
				}
				if(e.motionY < -.2F && e.isSneaking() && ECUtils.playerUseMRU(e, s, 1))
				{
					e.motionY = -.2F;
					e.fallDistance = 0F;
					world.spawnParticle(EnumParticleTypes.REDSTONE, e.posX+MathUtils.randomDouble(world.rand)/2, e.posY-1+MathUtils.randomDouble(world.rand), e.posZ+MathUtils.randomDouble(world.rand)/2, 0, 1, 1);
				}
			}
		}

	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.BELT;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if(player instanceof EntityPlayer)
		{
			EntityPlayer e = (EntityPlayer) player;
			if(e.motionY < -.2F && !e.isSneaking() && ECUtils.playerUseMRU(e, itemstack, 1))
			{
				e.motionY = -.2F;
				e.fallDistance = 0F;
				e.getEntityWorld().spawnParticle(EnumParticleTypes.REDSTONE, e.posX+MathUtils.randomDouble(e.getEntityWorld().rand)/2, e.posY-1+MathUtils.randomDouble(e.getEntityWorld().rand), e.posZ+MathUtils.randomDouble(e.getEntityWorld().rand)/2, 0, 1, 1);
			}
			if(itemstack.getCapability(MRU_HANDLER_ITEM_CAPABILITY, null).getMRU() >= 1) {
				EssentialCraftCore.proxy.wingsAction(e, itemstack);
			}
			MiscUtils.applyPlayerModifier((EntityPlayer)player, SharedMonsterAttributes.MOVEMENT_SPEED, "EC300", 0.1F, false, 0, "bauble");
		}
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		if(player instanceof EntityPlayer) {
			MiscUtils.applyPlayerModifier((EntityPlayer)player, SharedMonsterAttributes.MOVEMENT_SPEED, "EC300", 0.1F, true, 0, "bauble");
		}
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/magicalwings", "inventory"));
	}
}
