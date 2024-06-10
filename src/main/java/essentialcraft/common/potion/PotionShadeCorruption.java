package essentialcraft.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionShadeCorruption extends Potion {

	public PotionShadeCorruption(boolean isBad, int color) {
		super(isBad, color);
		setIconIndex(5, 2);
		setEffectiveness(0.25D);
		setPotionName("potion.shadeCorruption");
		this.setRegistryName("essentialcraft", "potion.shadecorruption");
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		//welp idk wat shade iz
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
