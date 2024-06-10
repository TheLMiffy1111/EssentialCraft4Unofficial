package essentialcraft.common.item;

import java.util.List;

import DummyCore.Client.IModelRegisterer;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemSpikyShield extends ItemMRUGeneric implements IModelRegisterer {

	public ItemSpikyShield() {
		super();
		maxStackSize = 1;
		bFull3D = true;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
		if(entityLiving instanceof EntityPlayer && ECUtils.playerUseMRU((EntityPlayer)entityLiving, stack, 100)) {
			List<EntityMob> mobs = world.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(entityLiving.posX-5, entityLiving.posY-2, entityLiving.posZ-5, entityLiving.posX+5, entityLiving.posY+2, entityLiving.posZ+5));
			if(!mobs.isEmpty()) {
				for(EntityMob mob : mobs) {
					mob.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entityLiving), 12F);
				}
			}
		}
		return stack;
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

		}
		{
			player.setActiveHand(hand);
		}
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/spikyshield", "inventory"));
	}
}
