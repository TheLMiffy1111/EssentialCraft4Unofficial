package essentialcraft.common.potion;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import essentialcraft.common.item.ItemBaublesSpecial;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionRadiation extends Potion {

	public PotionRadiation(boolean isBad, int color) {
		super(isBad, color);
		setIconIndex(4, 2);
		setEffectiveness(0.25D);
		setPotionName("potion.radiation");
		this.setRegistryName("essentialcraft", "potion.radiation");
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		if(!entity.getEntityWorld().isRemote && entity.getEntityWorld().rand.nextInt(16) < amplifier) {
			boolean divide = false;
			if(entity instanceof EntityPlayer) {
				IBaublesItemHandler b = BaublesApi.getBaublesHandler((EntityPlayer)entity);
				if(b != null) {
					for(int i = 0; i < b.getSlots(); ++i) {
						ItemStack is = b.getStackInSlot(i);
						if(is.getItem() instanceof ItemBaublesSpecial && is.getItemDamage() == 11) {
							divide = true;
						}
					}
				}
			}

			if(divide) {
				amplifier/=2;
			}
			int maxHealth = (int)entity.getMaxHealth()-(amplifier+1);
			float currentHealth = entity.getHealth();
			if(maxHealth < 1) {
				maxHealth = 1;
			}
			if(currentHealth > maxHealth) {
				entity.setHealth(maxHealth);
			}
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
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
