package essentialcraft.client.particle;

import DummyCore.Utils.TessellatorWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleColoredFlame extends Particle {

	private float flameScale;

	private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
	private static final ResourceLocation ecparticleTextures = new ResourceLocation("essentialcraft", "textures/special/particles.png");

	public ParticleColoredFlame(World w, double x, double y, double z, double mX, double mY, double mZ)
	{
		super(w, x, y, z, mX, mY, mZ);
		motionX = motionX * 0.009999999776482582D + mX;
		motionY = motionY * 0.009999999776482582D + mY;
		motionZ = motionZ * 0.009999999776482582D + mZ;
		flameScale = particleScale;
		particleRed = particleGreen = particleBlue = 1F;
		particleAlpha = 0.99F;
		particleMaxAge = (int)(8D / (Math.random() * 0.8D + 0.2D)) + 4;
		canCollide = true;
		setParticleTextureIndex(48);
	}

	@Override
	public boolean shouldDisableDepth() {
		return true;
	}

	public ParticleColoredFlame(World w, double x, double y, double z, double mX, double mY, double mZ, double r, double g, double b, double scale)
	{
		super(w, x, y, z, mX, mY, mZ);
		motionX = motionX * 0.009999999776482582D + mX;
		motionY = motionY * 0.009999999776482582D + mY;
		motionZ = motionZ * 0.009999999776482582D + mZ;
		flameScale = (float) scale;
		particleRed = (float) r;
		particleGreen = (float) g;
		particleBlue = (float) b;
		particleAlpha = 0.99F;
		particleMaxAge = (int)(8D / (Math.random() * 0.8D + 0.2D)) + 4;
		canCollide = true;
		setParticleTextureIndex(48);
	}

	@Override
	public void renderParticle(BufferBuilder var1, Entity var2, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_)
	{
		TessellatorWrapper.getInstance().draw().begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		Minecraft.getMinecraft().renderEngine.bindTexture(ecparticleTextures);

		float f6 = (particleAge + p_70539_2_) / particleMaxAge;
		particleScale = flameScale * (1F - f6 * f6 * 0.5F);
		super.renderParticle(var1, var2, p_70539_2_, p_70539_3_, p_70539_4_, p_70539_5_, p_70539_6_, p_70539_7_);

		TessellatorWrapper.getInstance().draw().begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		Minecraft.getMinecraft().renderEngine.bindTexture(particleTextures);
	}

	@Override
	public int getBrightnessForRender(float p_70070_1_)
	{
		float f1 = (particleAge + p_70070_1_) / particleMaxAge;

		if (f1 < 0F)
		{
			f1 = 0F;
		}

		if (f1 > 1F)
		{
			f1 = 1F;
		}

		int i = super.getBrightnessForRender(p_70070_1_);
		int j = i & 255;
		int k = i >> 16 & 255;
		j += (int)(f1 * 15F * 16F);

		if (j > 240)
		{
			j = 240;
		}

		return j | k << 16;
	}

	/**
	 * Gets how bright this entity is.
	 */
	public float getBrightness(float p_70013_1_)
	{
		float f1 = (particleAge + p_70013_1_) / particleMaxAge;

		if (f1 < 0F)
		{
			f1 = 0F;
		}

		if (f1 > 1F)
		{
			f1 = 1F;
		}

		float f2 = super.getBrightnessForRender(p_70013_1_);
		return f2 * f1 + (1F - f1);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		if (particleAge++ >= particleMaxAge)
		{
			setExpired();
		}

		move(motionX, motionY, motionZ);
		motionX *= 0.9599999785423279D;
		motionY *= 0.9599999785423279D;
		motionZ *= 0.9599999785423279D;

		if (onGround)
		{
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
		}
	}
}
