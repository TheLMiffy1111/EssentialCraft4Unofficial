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

public class PotionFrozenMind extends Potion {

	public PotionFrozenMind(boolean isBad, int color) {
		super(isBad, color);
		setIconIndex(5, 1);
		setEffectiveness(0.25D);
		setPotionName("potion.frozenMind");
		this.setRegistryName("essentialcraft", "potion.frozenmind");
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		if(!(entity instanceof EntityPlayer)) {
			return;
		}
		boolean remove = false;
		IBaublesItemHandler b = BaublesApi.getBaublesHandler((EntityPlayer) entity);
		if(b != null) {
			for(int i = 0; i < b.getSlots(); ++i) {
				ItemStack is = b.getStackInSlot(i);
				if(is.getItem() instanceof ItemBaublesSpecial && is.getItemDamage() == 31) {
					remove = true;
				}
			}
		}
		if(remove) {
			entity.removePotionEffect(this);
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
