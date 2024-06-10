package essentialcraft.utils.common;

import java.util.ArrayList;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import baubles.api.BaublesApi;
import essentialcraft.api.ApiCore;
import essentialcraft.api.IWindResistHandler;
import essentialcraft.common.item.ItemComputerArmor;
import essentialcraft.common.item.ItemGenericArmor;
import essentialcraft.common.item.ItemsCore;
import essentialcraft.common.registry.PotionRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerTracker {

	@SubscribeEvent
	public void onPlayerLogOn(PlayerEvent event) {
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onDescrAdded(ItemTooltipEvent event) {
		ItemStack stk = event.getItemStack();
		Item i = stk.getItem();
		if(ApiCore.ITEM_RESISTANCE_MAP.containsKey(i)) {
			ArrayList<Float> fltLst = ApiCore.ITEM_RESISTANCE_MAP.get(i);
			event.getToolTip().add(TextFormatting.GOLD+"+"+(int)(fltLst.get(0)*100)+"% "+TextFormatting.DARK_PURPLE+"to MRUCorruption resistance");
			event.getToolTip().add(TextFormatting.GOLD+"+"+(int)(fltLst.get(1)*100)+"% "+TextFormatting.DARK_PURPLE+"to MRURadiation resistance");
			event.getToolTip().add(TextFormatting.GOLD+"-"+(int)(fltLst.get(2)*100)+"% "+TextFormatting.DARK_PURPLE+"to Corruption affection");
		}
	}

	@SubscribeEvent
	public void onPlayerJump(LivingJumpEvent event)
	{
		if(event.getEntityLiving() instanceof EntityPlayer && !event.getEntityLiving().getEntityWorld().isRemote && event.getEntityLiving().getEntityWorld().rand.nextFloat() < 0.0003F)
		{
			EntityPlayer player = (EntityPlayer)event.getEntityLiving();
			boolean addBuff = true;
			if(ECUtils.getData(player).isWindbound())
			{
				if(BaublesApi.getBaublesHandler(player) != null)
				{
					for(int i = 0; i < BaublesApi.getBaublesHandler(player).getSlots(); ++i)
					{
						if(BaublesApi.getBaublesHandler(player).getStackInSlot(i).getItem() instanceof IWindResistHandler)
						{
							if(addBuff) {
								addBuff = !((IWindResistHandler)BaublesApi.getBaublesHandler(player).getStackInSlot(i).getItem()).resistWind(player, BaublesApi.getBaublesHandler(player).getStackInSlot(i));
							}
						}
					}
				}

				if(player.inventory != null)
				{
					for(int i = 0; i < player.inventory.armorInventory.size(); ++i)
					{
						if(!player.inventory.armorInventory.get(i).isEmpty())
						{
							if(player.inventory.armorInventory.get(i).getItem() instanceof IWindResistHandler)
							{
								if(addBuff) {
									addBuff = !((IWindResistHandler)player.inventory.armorInventory.get(i).getItem()).resistWind(player, BaublesApi.getBaublesHandler(player).getStackInSlot(i));
								}
							}
						}
					}
				}

				if(addBuff) {
					event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 100, 12));
				}
				player.sendMessage(new TextComponentString("The wind pushes you upwards...").setStyle(new Style().setColor(TextFormatting.DARK_AQUA).setItalic(true)));

				WindRelations.increasePlayerWindRelations(player, 1000);
			}

		}
		if(event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if(!player.inventory.armorInventory.get(1).isEmpty() && player.inventory.armorInventory.get(1).getItem() instanceof ItemGenericArmor)
			{
				player.motionY += 0.2D;
			}
			if(!player.inventory.armorInventory.get(1).isEmpty() && player.inventory.armorInventory.get(1).getItem() instanceof ItemComputerArmor)
			{
				player.motionY += 0.3D;
			}
		}

	}

	@SubscribeEvent
	public void onPlayerHurt(LivingHurtEvent event)
	{
		if(event.getEntityLiving() instanceof EntityPlayer && !event.getEntityLiving().getEntityWorld().isRemote)
		{
			EntityPlayer player = (EntityPlayer)event.getEntityLiving();
			ItemStack chestplate = player.inventory.armorInventory.get(2);
			if(!chestplate.isEmpty() && chestplate.getItem() == ItemsCore.magicArmorItems[5] && event.getEntityLiving().getEntityWorld().rand.nextFloat() <= 0.2F)
			{
				event.setAmount(0F);
				for(int i = 0; i < 100; ++i)
				{
					MiscUtils.spawnParticlesOnServer("redstone", (float)(player.posX+MathUtils.randomDouble(event.getEntityLiving().getEntityWorld().rand)/2), (float)(player.posY+1+MathUtils.randomDouble(event.getEntityLiving().getEntityWorld().rand)), (float)(player.posZ+MathUtils.randomDouble(event.getEntityLiving().getEntityWorld().rand)/2), -1, 0, 0);
				}
				return;
			}
			if(!chestplate.isEmpty() && chestplate.getItem() == ItemsCore.computer_chestplate)
			{
				if(event.getSource() != null && (event.getSource() == DamageSource.WITHER || event.getSource() == DamageSource.MAGIC || event.getSource() == DamageSource.STARVE)) {
					event.setCanceled(true);
				}
				return;
			}
			ItemStack boots = player.inventory.armorInventory.get(0);
			if(!boots.isEmpty() && boots.getItem() == ItemsCore.magicArmorItems[7])
			{
				if(event.getSource() == DamageSource.FALL)
				{
					event.setAmount(event.getAmount() - event.getAmount()*0.9F);
				}
			}
			if(!boots.isEmpty() && boots.getItem() == ItemsCore.computer_boots)
			{
				if(event.getSource() == DamageSource.FALL)
				{
					event.setAmount(0);
					return;
				}
			}
			if(player.getActivePotionEffect(PotionRegistry.chaosInfluence) != null)
			{
				int amplifier = player.getActivePotionEffect(PotionRegistry.chaosInfluence).getAmplifier();
				event.setAmount(event.getAmount() * (1+amplifier));
			}
			if(player.getActivePotionEffect(PotionRegistry.frozenMind) != null)
			{
				ECUtils.calculateAndAddPE(player, MobEffects.SLOWNESS, 400, 100);
				ECUtils.calculateAndAddPE(player, MobEffects.WEAKNESS, 400, 100);
				ECUtils.calculateAndAddPE(player, MobEffects.MINING_FATIGUE, 400, 100);
			}
		}
	}
}
