package essentialcraft.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class PotionPurpleFlame extends Potion {

	public PotionPurpleFlame(boolean isBad, int color) {
		super(isBad, color);
		setIconIndex(6, 2);
		setEffectiveness(0.25D);
		setPotionName("potion.purpleFlame");
		this.setRegistryName("essentialcraft", "potion.purpleflame");
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		if(!entity.getEntityWorld().isRemote && entity.getEntityWorld().rand.nextInt(16) < amplifier) {
			entity.attackEntityFrom(DamageSource.MAGIC, 5);
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
