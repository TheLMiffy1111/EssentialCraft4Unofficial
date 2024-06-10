package essentialcraft.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionWindTouch extends Potion {

	public PotionWindTouch(boolean isBad, int color) {
		super(isBad, color);
		setIconIndex(6, 1);
		setEffectiveness(0.25D);
		setPotionName("potion.windTouch");
		this.setRegistryName("essentialcraft", "potion.windtouch");
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {

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

	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		mc.fontRenderer.drawStringWithShadow(effect.getAmplifier()+1+"", x+19, y+19, 0xffffff);
	}

	static final ResourceLocation rl = new ResourceLocation("essentialcraft", "textures/special/potions.png");
}
