package essentialcraft.utils.common;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import essentialcraft.api.IWindModifyHandler;
import essentialcraft.api.IWindResistHandler;
import essentialcraft.common.item.ItemBaublesSpecial;
import essentialcraft.common.item.ItemsCore;
import essentialcraft.common.registry.PotionRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.FakePlayer;

public class WindRelations {

	public static int getPlayerWindRelations(EntityPlayer player) {
		if(player == null || player instanceof FakePlayer) {
			return 0;
		}
		return ECUtils.getData(player).getPlayerWindPoints();
	}

	public static float getPlayerWindRevModifier(EntityPlayer player) {
		float retFlt = 0.1F;
		for(ItemStack armor : player.inventory.armorInventory) {
			if(!armor.isEmpty()) {
				if(armor.getItem() == ItemsCore.magicArmorItems[12] || armor.getItem() == ItemsCore.magicArmorItems[13] || armor.getItem() == ItemsCore.magicArmorItems[14] || armor.getItem() == ItemsCore.magicArmorItems[15]) {
					retFlt += 0.23F;
				}
				if(armor.getItem() instanceof IWindModifyHandler) {
					retFlt += ((IWindModifyHandler)armor.getItem()).getModifier(armor, player);
				}
			}
		}
		if(BaublesApi.getBaublesHandler(player) != null) {
			for(int i = 0; i < BaublesApi.getBaublesHandler(player).getSlots(); ++i) {
				ItemStack armor = BaublesApi.getBaublesHandler(player).getStackInSlot(i);
				if(armor.getItem() instanceof IWindModifyHandler) {
					retFlt += ((IWindModifyHandler)armor.getItem()).getModifier(armor, player);
				}
			}
		}
		return retFlt;
	}

	public static void setPlayerWindRelations(EntityPlayer player, int amount) {
		if(!(player instanceof FakePlayer)) {
			ECUtils.getData(player).modifyWindpoints(amount);
		}
	}

	public static void increasePlayerWindRelations(EntityPlayer e, int amount) {
		int current = getPlayerWindRelations(e);
		amount *= getPlayerWindRevModifier(e);
		setPlayerWindRelations(e, current+amount);
		e.addPotionEffect(new PotionEffect(PotionRegistry.windTouch, 1000, 0, true, true));
	}

	public static void playerTick(EntityPlayer player) {
		if((player instanceof FakePlayer) || !ECUtils.getData(player).isWindbound()) {
			return;
		}

		if(!player.getEntityWorld().isRemote) {
			int mod = 1;
			if(player.getActivePotionEffect(PotionRegistry.paranormalLightness) != null) {
				mod = player.getActivePotionEffect(PotionRegistry.paranormalLightness).getAmplifier()+1;
			}
			Style messageStyle = new Style().setColor(TextFormatting.DARK_AQUA).setItalic(true);
			if(player.getEntityWorld().rand.nextDouble() < 0.0002F*mod) {
				increasePlayerWindRelations(player, 1000);
				boolean elemental = false;
				IBaublesItemHandler b = BaublesApi.getBaublesHandler(player);
				if(b != null) {
					for(int i = 0; i < b.getSlots(); ++i) {
						ItemStack is = b.getStackInSlot(i);
						if(is.getItem() instanceof ItemBaublesSpecial && is.getItemDamage() == 9) {
							elemental = true;
						}
					}
				}
				if(!elemental) {
					player.sendMessage(new TextComponentString("The wind timidly touches your hair...").setStyle(messageStyle));
				}
				else {
					int rndID = player.getEntityWorld().rand.nextInt(4);
					//Fire
					if(rndID == 0) {
						player.sendMessage(new TextComponentString("You feel a warm touch of the wind...").setStyle(messageStyle));
						player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 7200, 1, true, true));
					}
					//Water
					if(rndID == 1) {
						player.sendMessage(new TextComponentString("You feel a chilling touch of the wind...").setStyle(messageStyle));
						player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 7200, 0, true, true));
					}
					//Earth
					if(rndID == 2) {
						player.sendMessage(new TextComponentString("You feel a strong push of the wind...").setStyle(messageStyle));
						player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 7200, 0, true, true));
					}
					//Air
					if(rndID == 2) {
						player.sendMessage(new TextComponentString("You feel a strong blow of the wind...").setStyle(messageStyle));
						player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 7200, 1, true, true));
					}
				}
			}
			if(player.getEntityWorld().rand.nextDouble() < 0.001F*mod) {
				if(player.isSprinting()) {
					player.sendMessage(new TextComponentString("The wind pushes you in the back...").setStyle(messageStyle));
					increasePlayerWindRelations(player, 1000);
					boolean addBuff = true;

					if(BaublesApi.getBaublesHandler(player) != null) {
						for(int i = 0; i < BaublesApi.getBaublesHandler(player).getSlots(); ++i) {
							if(BaublesApi.getBaublesHandler(player).getStackInSlot(i).getItem() instanceof IWindResistHandler) {
								if(addBuff) {
									addBuff &= !((IWindResistHandler)BaublesApi.getBaublesHandler(player).getStackInSlot(i).getItem()).resistWind(player, BaublesApi.getBaublesHandler(player).getStackInSlot(i));
								}
							}
						}
					}

					if(player.inventory != null) {
						for(ItemStack element : player.inventory.armorInventory) {
							if(element.getItem() instanceof IWindResistHandler) {
								if(addBuff) {
									addBuff &= !((IWindResistHandler)element.getItem()).resistWind(player, element);
								}
							}
						}
					}

					if(addBuff) {
						player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20, 31));
					}
				}
			}
		}
	}
}
