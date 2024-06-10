package essentialcraft.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionUnnormalLightness extends Potion{

	public PotionUnnormalLightness(boolean isBad, int color) {
		super(isBad, color);
		setIconIndex(3, 2);
		setEffectiveness(0.25D);
		setPotionName("potion.paranormalLightness");
		this.setRegistryName("essentialcraft", "potion.paranormalLightness");
		registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070636", 0.4D, 2);
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		int duration = entity.getActivePotionEffect(this).getDuration();
		entity.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, duration, amplifier));
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
