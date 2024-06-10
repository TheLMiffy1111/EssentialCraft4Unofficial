package essentialcraft.common.potion;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import essentialcraft.common.item.ItemBaublesSpecial;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class PotionMRUCorruption extends Potion {

	public PotionMRUCorruption(boolean isBad, int color) {
		super(isBad, color);
		setIconIndex(3, 1);
		setEffectiveness(0.25D);
		setPotionName("potion.mruCorruption");
		this.setRegistryName("essentialcraft", "potion.mrucorruption");
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		if(!entity.getEntityWorld().isRemote && entity.getEntityWorld().rand.nextInt(16) < amplifier) {
			float damIndex = 5;
			if(entity instanceof EntityPlayer) {
				damIndex *= ECUtils.getGenResistance(0, (EntityPlayer)entity);
				boolean heal = false;
				IBaublesItemHandler b = BaublesApi.getBaublesHandler((EntityPlayer)entity);
				if(b != null) {
					for(int i = 0; i < b.getSlots(); ++i) {
						ItemStack is = b.getStackInSlot(i);
						if(is.getItem() instanceof ItemBaublesSpecial && is.getItemDamage() == 10) {
							heal = true;
						}
					}
				}
				if(!heal) {
					entity.attackEntityFrom(DamageSource.MAGIC, damIndex);
				}
				else {
					entity.heal(damIndex);
				}
			}
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 20 == 0;
	}

	@Override
	public boolean hasStatusIcon() {
		return true;
	}

	@Override
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(rl);
		return super.getStatusIconIndex();
	}

	static final ResourceLocation rl = new ResourceLocation("essentialcraft", "textures/special/potions.png");
}
