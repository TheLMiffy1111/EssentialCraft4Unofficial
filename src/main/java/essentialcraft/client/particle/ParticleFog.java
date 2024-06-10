package essentialcraft.client.particle;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.TessellatorWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleFog extends Particle{

	private double mruPosX;
	private double mruPosY;
	private double mruPosZ;
	public double red, green, blue;
	public static final ResourceLocation rec = new ResourceLocation("essentialcraft", "textures/items/particles/fog.png");
	private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
	public ParticleFog(World w, double x, double y, double z, double i, double j, double k) {
		super(w, x, y, z, i, j, k);
		if(w != null && w.rand != null) {
			motionX = MathUtils.randomDouble(w.rand);
			motionY = MathUtils.randomDouble(w.rand);
			motionZ = MathUtils.randomDouble(w.rand);
			red = i;
			green = j;
			blue = k;
			mruPosX = posX = x;
			mruPosY = posY = y;
			mruPosZ = posZ = z;
			rand.nextFloat();
			particleScale = 10F;
			particleRed = (float) red;
			particleGreen = (float) green;
			particleBlue = (float) blue;
			particleAlpha = 0.99F;
			particleMaxAge = (int)(Math.random() * 10D) + 100;
			canCollide = true;
			setParticleTextureIndex((int)(Math.random() * 8D));
		}
	}

	@Override
	public void renderParticle(BufferBuilder var1, Entity var2, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		TessellatorWrapper var3 = TessellatorWrapper.getInstance();
		var3.draw().begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		Minecraft.getMinecraft().renderEngine.bindTexture(rec);
		boolean enabled = GL11.glIsEnabled(GL11.GL_BLEND);
		GlStateManager.enableBlend();
		super.renderParticle(var1, var2, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
		var3.draw().begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		Minecraft.getMinecraft().renderEngine.bindTexture(particleTextures);
		if(!enabled) {
			GlStateManager.disableBlend();
		}
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
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		float f = (float)particleAge / (float)particleMaxAge;
		posX = mruPosX + motionX * f;
		posY = mruPosY + motionY * f;
		posZ = mruPosZ + motionZ * f;
		particleScale *= 1.01F;
		if(particleAge++ >= particleMaxAge) {
			setExpired();
		}
	}
}
