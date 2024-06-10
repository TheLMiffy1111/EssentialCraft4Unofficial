package essentialcraft.client.particle;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.TessellatorWrapper;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleMRU extends Particle{

	private double mruPosX;
	private double mruPosY;
	private double mruPosZ;
	public float tickPos;
	private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
	private static final ResourceLocation ecparticleTextures = new ResourceLocation("essentialcraft","textures/special/particles.png");

	public ParticleMRU(World w, double x, double y,double z, double i, double j,double k)
	{
		super(w, x, y, z, i, j,k);
		motionX = i;
		motionY = j;
		motionZ = k;
		mruPosX = posX = x;
		mruPosY = posY = y;
		mruPosZ = posZ = z;
		rand.nextFloat();
		particleScale = 0.5F;
		particleRed = 0;
		particleGreen = 0F;
		particleBlue = 0.8F;
		particleAlpha = 0.99F;
		particleMaxAge = (int)(Math.random() * 10.0D) + 40;
		canCollide = true;
		setParticleTextureIndex((int)(Math.random() * 8.0D));
	}

	public ParticleMRU(World w, double x, double y,double z, double i, double j,double k, double cR, double cG, double cB)
	{
		this(w,x,y,z,i,j,k);
		particleRed = (float) cR;
		particleGreen = (float) cG;
		particleBlue = (float) cB;
	}

	@Override
	public boolean shouldDisableDepth() {
		return true;
	}

	@Override
	public void renderParticle(BufferBuilder var1, Entity var2, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		TessellatorWrapper.getInstance().draw().begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		Minecraft.getMinecraft().renderEngine.bindTexture(ecparticleTextures);
		boolean enabled = GL11.glIsEnabled(GL11.GL_BLEND);
		GlStateManager.enableBlend();
		if(ECUtils.canPlayerSeeMRU(Minecraft.getMinecraft().player))
		{
			float sc = particleScale;
			float cR = particleRed;
			float cG = particleGreen;
			float cB = particleBlue;
			float cA = particleAlpha;
			particleScale *= 1.5F;
			particleRed = 1;
			particleGreen = 0F;
			particleBlue = 1F;
			particleAlpha = 0.99F;
			super.renderParticle(var1, var2, par2, par3, par4, par5, par6, par7);
			particleScale = sc;
			particleRed = cR;
			particleGreen =  cG;
			particleBlue =  cB;
			particleAlpha = cA;
			super.renderParticle(var1, var2, par2, par3, par4, par5, par6, par7);
		}
		TessellatorWrapper.getInstance().draw().begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		Minecraft.getMinecraft().renderEngine.bindTexture(particleTextures);
		if(!enabled) {
			GlStateManager.disableBlend();
		}
	}

	@Override
	public int getBrightnessForRender(float p_70070_1_)
	{
		return 255;
	}

	/**
	 * Gets how bright this entity is.
	 */
	public float getBrightness(float p_70013_1_)
	{
		float f1 = super.getBrightnessForRender(p_70013_1_);
		float f2 = (float)particleAge / (float)particleMaxAge;
		f2 = f2 * f2 * f2 * f2;
		return f1 * (1.0F - f2) + f2;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		tickPos += 15+world.rand.nextFloat()*15;
		if(particleAge < particleMaxAge/2) {
			setParticleTextureIndex(7 - particleAge * 8 / (particleMaxAge/2));
		}
		else {
			setParticleTextureIndex((particleAge-particleMaxAge/2) * 8 / (particleMaxAge/2));
		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		float f = (float)particleAge / (float)particleMaxAge;
		posX = mruPosX + motionX * f + Math.sin(Math.toRadians(tickPos + world.getWorldTime()*10))/10;
		posY = mruPosY + motionY * f + Math.cos(Math.toRadians(tickPos + world.getWorldTime()*10))/10;
		posZ = mruPosZ + motionZ * f - Math.sin(Math.toRadians(tickPos + world.getWorldTime()*10))/10;

		if (particleAge++ >= particleMaxAge)
		{
			setExpired();
		}
	}
}
