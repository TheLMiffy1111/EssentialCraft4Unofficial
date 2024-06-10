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

	public ParticleColoredFlame(World w, double x, double y, double z, double mX, double mY, double mZ) {
		super(w, x, y, z, mX, mY, mZ);
		motionX = motionX * 0.01D + mX;
		motionY = motionY * 0.01D + mY;
		motionZ = motionZ * 0.01D + mZ;
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

	public ParticleColoredFlame(World w, double x, double y, double z, double mX, double mY, double mZ, double r, double g, double b, double scale) {
		super(w, x, y, z, mX, mY, mZ);
		motionX = motionX * 0.01D + mX;
		motionY = motionY * 0.01D + mY;
		motionZ = motionZ * 0.01D + mZ;
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
	public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		TessellatorWrapper.getInstance().draw().begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		Minecraft.getMinecraft().renderEngine.bindTexture(ecparticleTextures);

		float f6 = (particleAge + partialTicks) / particleMaxAge;
		particleScale = flameScale * (1F - f6 * f6 * 0.5F);
		super.renderParticle(buffer, entity, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);

		TessellatorWrapper.getInstance().draw().begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		Minecraft.getMinecraft().renderEngine.bindTexture(particleTextures);
	}

	@Override
	public int getBrightnessForRender(float partialTick) {
		float f1 = (particleAge + partialTick) / particleMaxAge;

		if(f1 < 0F) {
			f1 = 0F;
		}

		if(f1 > 1F) {
			f1 = 1F;
		}

		int i = super.getBrightnessForRender(partialTick);
		int j = i & 255;
		int k = i >> 16 & 255;
		j += (int)(f1 * 15F * 16F);

		if(j > 240) {
			j = 240;
		}

		return j | k << 16;
	}

	public float getBrightness(float partialTick) {
		float f1 = (particleAge + partialTick) / particleMaxAge;

		if(f1 < 0F) {
			f1 = 0F;
		}

		if(f1 > 1F) {
			f1 = 1F;
		}

		float f2 = super.getBrightnessForRender(partialTick);
		return f2 * f1 + (1F - f1);
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		if(particleAge++ >= particleMaxAge) {
			setExpired();
		}

		move(motionX, motionY, motionZ);
		motionX *= 0.96D;
		motionY *= 0.96D;
		motionZ *= 0.96D;

		if(onGround) {
			motionX *= 0.7D;
			motionZ *= 0.7D;
		}
	}
}
