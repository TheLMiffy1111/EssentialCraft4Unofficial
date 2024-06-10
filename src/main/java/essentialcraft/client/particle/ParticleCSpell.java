package essentialcraft.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ParticleCSpell extends Particle{

	private double mruPosX;
	private double mruPosY;
	private double mruPosZ;
	public ParticleCSpell(World w, double x, double y, double z, double i, double j, double k) {
		super(w, x, y, z, i, j, k);
		motionX = i;
		motionY = j;
		motionZ = k;
		mruPosX = posX = x;
		mruPosY = posY = y;
		mruPosZ = posZ = z;
		particleScale = 1F;
		particleRed = 0;
		particleGreen = 0F;
		particleBlue = 0F;
		particleAlpha = 0.99F;
		particleMaxAge = (int)(Math.random() * 10D) + 40;
		canCollide = false;
		setParticleTextureIndex((int)(Math.random() * 8D));
	}

	@Override
	public void renderParticle(BufferBuilder var1, Entity var2, float par2, float par3, float par4, float par5, float par6, float par7) {
		particleScale = 1F;
		particleRed = 0;
		particleGreen = 0F;
		particleBlue = 0F;
		super.renderParticle(var1, var2, par2, par3, par4, par5, par6, par7);
		particleRed = 1;
		particleGreen = 1F;
		particleBlue = 1F;
		particleScale = 0.4F;
		super.renderParticle(var1, var2, par2, par3, par4, par5, par6, par7);
	}

	@Override
	public int getBrightnessForRender(float partialTick) {
		int i = super.getBrightnessForRender(partialTick);
		float f1 = (float)particleAge / (float)particleMaxAge;
		f1 *= f1;
		f1 *= f1;
		int j = i & 255;
		int k = i >> 16 & 255;
		k += (int)(f1 * 15F * 16F);

		if(k > 240) {
			k = 240;
		}

		return j | k << 16;
	}

	public float getBrightness(float partialTick) {
		float f1 = super.getBrightnessForRender(partialTick);
		float f2 = (float)particleAge / (float)particleMaxAge;
		f2 = f2 * f2 * f2 * f2;
		return f1 * (1F - f2) + f2;
	}

	@Override
	public void onUpdate() {
		setParticleTextureIndex(7 - particleAge * 8 / particleMaxAge);
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		float f = (float)particleAge / (float)particleMaxAge;
		posX = mruPosX + motionX * f;
		posY = mruPosY + motionY * f;
		posZ = mruPosZ + motionZ * f;

		if(particleAge++ >= particleMaxAge) {
			setExpired();
			for(int t = 0; t < 10; ++t) {
				//this.getEntityWorld().spawnParticle("smoke", posX, posY, posZ, MathUtils.randomFloat(rand)/6, MathUtils.randomFloat(rand)/6, MathUtils.randomFloat(rand)/6);
				//if(this.getEntityWorld().rand.nextFloat() < 0.01F)
				//this.getEntityWorld().spawnParticle("explode", posX, posY, posZ, MathUtils.randomFloat(rand)/6, MathUtils.randomFloat(rand)/6, MathUtils.randomFloat(rand)/6);
			}
		}
		// if(this.getEntityWorld().rand.nextFloat() < 0.01F)
		//this.getEntityWorld().spawnParticle("redstone", posX, posY, posZ, -1, 0, 0);
	}
}
