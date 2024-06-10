package essentialcraft.common.potion;

import DummyCore.Utils.MiscUtils;
import essentialcraft.common.registry.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.translation.I18n;

public class PotionMindfoldParadox extends Potion {

	public PotionMindfoldParadox(boolean isBad, int color) {
		super(isBad, color);
		setIconIndex(7, 2);
		setPotionName("potion.paradox");
		this.setRegistryName("essentialcraft", "potion.paradox");
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		int duration = entity.getActivePotionEffect(this).getDuration();
		if(duration == 2000) {
			if(entity.getEntityWorld().isRemote) {
				entity.getEntityWorld().playSound(entity.posX,entity.posY,entity.posZ, SoundRegistry.potionTinnitus, SoundCategory.PLAYERS, 100, 1, true);
			}
			MiscUtils.setShaders(2);
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

	@Override
	public boolean shouldRenderInvText(PotionEffect effect) {
		return false;
	}

	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		String s1 = I18n.translateToLocal(effect.getEffectName());
		mc.fontRenderer.drawStringWithShadow(s1, x + 10 + 18, y + 6, 16777215);
		mc.fontRenderer.drawStringWithShadow(I18n.translateToLocal("potion.paradox.txt"), x + 10 + 18, y + 16, 16777215);
	}

	static final ResourceLocation rl = new ResourceLocation("essentialcraft", "textures/special/potions.png");
}
