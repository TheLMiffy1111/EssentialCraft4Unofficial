package essentialcraft.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.IRenderHandler;

public class RenderSkyParadox  extends IRenderHandler {
	private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
	private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
	public float rotation = -90F;
	public float rotationSpeed = 0.01F;
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		rotationSpeed *= 1.007F;
		rotation += rotationSpeed;
		GlStateManager.disableTexture2D();
		Vec3d Vec3d = world.getSkyColor(mc.getRenderViewEntity(), partialTicks);
		float f1 = (float)Vec3d.x;
		float f2 = (float)Vec3d.y;
		float f3 = (float)Vec3d.z;
		float f6;
		GlStateManager.color(f1, f2, f3);
		Tessellator tessellator1 = Tessellator.getInstance();
		BufferBuilder BufferBuilder = tessellator1.getBuffer();
		GlStateManager.depthMask(false);
		GlStateManager.enableFog();
		GlStateManager.color(f1, f2, f3);
		//GL11.glCallList(this.glSkyList);
		GlStateManager.disableFog();
		GlStateManager.disableAlpha();
		GlStateManager.enableBlend();
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		RenderHelper.disableStandardItemLighting();
		float[] afloat = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);
		float f7;
		float f8;
		float f9;
		float f10;
		if (afloat != null) {
			GlStateManager.disableTexture2D();
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
			GlStateManager.pushMatrix();
			GlStateManager.rotate(90F, 1F, 0F, 0F);
			GlStateManager.rotate(MathHelper.sin((float)Math.toRadians(System.currentTimeMillis())), 0F, 0F, 1F);
			GlStateManager.rotate(90F, 0F, 0F, 1F);
			f6 = afloat[0];
			f7 = afloat[1];
			f8 = afloat[2];
			float f11;

			BufferBuilder.begin(6, DefaultVertexFormats.POSITION_TEX_COLOR);
			BufferBuilder.pos(0D, 100D, 0D).color(f6, f7, f8, afloat[3]).endVertex();
			for (int j = 0; j <= 16; ++j) {
				f11 = j * (float)Math.PI * 2F / 16F;
				float f12 = MathHelper.sin(f11);
				float f13 = MathHelper.cos(f11);
				BufferBuilder.pos(f12 * 120F, f13 * 120F, -f13 * 40F * afloat[3]).color(afloat[0], afloat[1], afloat[2], 0F).endVertex();
			}

			tessellator1.draw();
			GlStateManager.popMatrix();
			GlStateManager.shadeModel(GL11.GL_FLAT);
		}

		GlStateManager.enableTexture2D();
		OpenGlHelper.glBlendFunc(770, 1, 1, 0);
		GlStateManager.pushMatrix();
		f6 = 1F - world.getRainStrength(partialTicks);
		f7 = 0F;
		f8 = 0F;
		f9 = 0F;
		if(rotationSpeed == 0) {
			rotationSpeed = 0.01F;
		}
		GlStateManager.color(1F, 1F, 1F, f6);
		GlStateManager.translate(f7, f8, f9);
		GlStateManager.rotate(-90F, 0F, 1F, 0F);
		GlStateManager.rotate(rotation, 1F, 0F, 0F);
		f10 = 30F;
		mc.renderEngine.bindTexture(locationSunPng);
		BufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		BufferBuilder.pos(-f10, 100D, -f10).tex(0D, 0D).endVertex();
		BufferBuilder.pos(f10, 100D, -f10).tex(1D, 0D).endVertex();
		BufferBuilder.pos(f10, 100D, f10).tex(1D, 1D).endVertex();
		BufferBuilder.pos(-f10, 100D, f10).tex(0D, 1D).endVertex();
		tessellator1.draw();
		f10 = 20F;
		mc.renderEngine.bindTexture(locationMoonPhasesPng);
		int k = world.getMoonPhase();
		int l = k % 4;
		int i1 = k / 4 % 2;
		float f14 = (l + 0) / 4F;
		float f15 = (i1 + 0) / 2F;
		float f16 = (l + 1) / 4F;
		float f17 = (i1 + 1) / 2F;
		BufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		BufferBuilder.pos(-f10, -100D, f10).tex(f16, f17).endVertex();
		BufferBuilder.pos(f10, -100D, f10).tex(f14, f17).endVertex();
		BufferBuilder.pos(f10, -100D, -f10).tex(f14, f15).endVertex();
		BufferBuilder.pos(-f10, -100D, -f10).tex(f16, f15).endVertex();
		tessellator1.draw();
		GlStateManager.disableTexture2D();
		float f18 = world.getStarBrightness(partialTicks) * f6;

		if (f18 > 0F) {
			GlStateManager.color(f18, f18, f18, f18);
		}

		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableFog();
		GlStateManager.popMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.color(0F, 0F, 0F);
		double d0 = mc.player.getPositionEyes(partialTicks).y - world.getHorizon();

		if (d0 < 0D) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 12F, 0F);
			//GL11.glCallList(this.glSkyList2);
			GlStateManager.popMatrix();
			float f19 = -((float)(d0 + 65D));
			BufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
			BufferBuilder.pos(-1D, f19, 1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1D, f19, 1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1D, -1D, 1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1D, -1D, 1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1D, -1D, -1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1D, -1D, -1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1D, f19, -1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1D, f19, -1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1D, -1D, -1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1D, -1D, 1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1D, f19, 1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1D, f19, -1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1D, f19, -1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1D, f19, 1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1D, -1D, 1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1D, -1D, -1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1D, -1D, -1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1D, -1D, 1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1D, -1D, 1D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1D, -1D, -1D).color(0, 0, 0, 255).endVertex();
			tessellator1.draw();
		}

		if (world.provider.isSkyColored()) {
			GlStateManager.color(f1 * 0.2F + 0.04F, f2 * 0.2F + 0.04F, f3 * 0.6F + 0.1F);
		}
		else {
			GlStateManager.color(f1, f2, f3);
		}

		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, -((float)(d0 - 16D)), 0F);
		GlStateManager.popMatrix();
		GlStateManager.enableTexture2D();
		GlStateManager.depthMask(true);
	}
}
