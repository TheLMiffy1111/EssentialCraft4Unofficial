package essentialcraft.client.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import essentialcraft.utils.common.ECUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.IRenderHandler;

public class RenderSkyHoanna extends IRenderHandler {

	private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
	private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
	private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");

	public RenderSkyHoanna()
	{

	}

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		int colorDay = 0x213141;
		if(ECUtils.isEventActive("essentialcraft.event.darkness")) {
			colorDay = 0x000000;
		}
		GlStateManager.disableFog();
		GlStateManager.disableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.depthMask(false);
		mc.renderEngine.bindTexture(locationEndSkyPng);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder BufferBuilder = tessellator.getBuffer();

		for (int i = 0; i < 6; ++i)
		{
			GlStateManager.pushMatrix();

			if (i == 1)
			{
				GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			}

			if (i == 2)
			{
				GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
			}

			if (i == 3)
			{
				GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
			}

			if (i == 4)
			{
				GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
			}

			if (i == 5)
			{
				GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
			}

			BufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
			BufferBuilder.pos(-100.0D, -100.0D, -100.0D).tex(0.0D, 0.0D).color((colorDay&0xFF0000)>>16, (colorDay&0x00FF00)>>8, colorDay&0x0000FF, 255).endVertex();
			BufferBuilder.pos(-100.0D, -100.0D, 100.0D).tex(0.0D, 16.0D).color((colorDay&0xFF0000)>>16, (colorDay&0x00FF00)>>8, colorDay&0x0000FF, 255).endVertex();
			BufferBuilder.pos(100.0D, -100.0D, 100.0D).tex(16.0D, 16.0D).color((colorDay&0xFF0000)>>16, (colorDay&0x00FF00)>>8, colorDay&0x0000FF, 255).endVertex();
			BufferBuilder.pos(100.0D, -100.0D, -100.0D).tex(16.0D, 0.0D).color((colorDay&0xFF0000)>>16, (colorDay&0x00FF00)>>8, colorDay&0x0000FF, 255).endVertex();
			tessellator.draw();
			GlStateManager.popMatrix();
		}
		GlStateManager.depthMask(true);
		GlStateManager.enableTexture2D();
		GlStateManager.enableAlpha();

		GlStateManager.disableTexture2D();
		Vec3d Vec3d = world.getSkyColor(mc.getRenderViewEntity(), partialTicks);
		float f1 = (float)Vec3d.x;
		float f2 = (float)Vec3d.y;
		float f3 = (float)Vec3d.z;
		float f6;

		if (mc.gameSettings.anaglyph)
		{
			float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
			float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
			f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
			f1 = f4;
			f2 = f5;
			f3 = f6;
		}

		GlStateManager.color(f1, f2, f3);
		GlStateManager.depthMask(false);
		GlStateManager.enableFog();
		GlStateManager.color(f1, f2, f3);
		//GL11.glCallList(this.glSkyList);
		GlStateManager.disableFog();
		GlStateManager.disableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		RenderHelper.disableStandardItemLighting();
		float[] afloat = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);
		float f7;
		float f8;
		float f9;
		float f10;

		if (afloat != null)
		{
			GlStateManager.disableTexture2D();
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
			GlStateManager.pushMatrix();
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
			f6 = afloat[0];
			f7 = afloat[1];
			f8 = afloat[2];
			float f11;

			if (mc.gameSettings.anaglyph)
			{
				f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
				f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
				f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
				f6 = f9;
				f7 = f10;
				f8 = f11;
			}

			BufferBuilder.begin(6, DefaultVertexFormats.POSITION_TEX_COLOR);
			BufferBuilder.pos(0.0D, 100.0D, 0.0D).color(f6, f7, f8, afloat[3]).endVertex();

			for (int j = 0; j <= 16; ++j)
			{
				f11 = j * (float)Math.PI * 2.0F / 16F;
				float f12 = MathHelper.sin(f11);
				float f13 = MathHelper.cos(f11);
				BufferBuilder.pos(f12 * 120.0F, f13 * 120.0F, -f13 * 40.0F * afloat[3]).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
			}

			tessellator.draw();
			GlStateManager.popMatrix();
			GlStateManager.shadeModel(GL11.GL_FLAT);
		}

		GlStateManager.enableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.pushMatrix();
		f6 = 1.0F - world.getRainStrength(partialTicks);
		f7 = 0.0F;
		f8 = 0.0F;
		f9 = 0.0F;
		GlStateManager.color(1.0F, 1.0F, 1.0F, f6);
		GlStateManager.translate(f7, f8, f9);
		Random sunRnd = new Random(54263524L);
		boolean b = ECUtils.isEventActive("essentialcraft.event.sunArray");
		int mod = 1;
		if(b) {
			mod = 3;
		}
		if(!ECUtils.isEventActive("essentialcraft.event.darkness")) {
			for(int i = 0; i < 10*mod; ++i) {
				GlStateManager.pushMatrix();
				GlStateManager.rotate(-90.0F+i*30F, 0.0F+i*i, i-1.0F/i/i, 1.0F*i*i);
				GlStateManager.rotate(world.getCelestialAngle(partialTicks) * 360.0F * i, 1.0F, 0.0F, 0.0F);
				f10 = sunRnd.nextFloat()*20;
				mc.renderEngine.bindTexture(locationSunPng);
				for(int x = 0; x < i+5; ++x) {
					BufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
					float fR = sunRnd.nextFloat();
					float fG = sunRnd.nextFloat();
					float fB = sunRnd.nextFloat();
					float fA = sunRnd.nextFloat();
					BufferBuilder.pos(-f10, 100.0D, -f10).tex(0.0D, 0.0D).color(fR, fG, fB, fA).endVertex();
					BufferBuilder.pos(f10, 100.0D, -f10).tex(1.0D, 0.0D).color(fR, fG, fB, fA).endVertex();
					BufferBuilder.pos(f10, 100.0D, f10).tex(1.0D, 1.0D).color(fR, fG, fB, fA).endVertex();
					BufferBuilder.pos(-f10, 100.0D, f10).tex(0.0D, 1.0D).color(fR, fG, fB, fA).endVertex();
					tessellator.draw();
				}
				GlStateManager.popMatrix();
			}
		}
		Random moonRnd = new Random(23564637563453L);
		if(!ECUtils.isEventActive("essentialcraft.event.darkness")) {
			for(int i = 0; i < 6; ++i) {
				GlStateManager.pushMatrix();
				GlStateManager.rotate(-90.0F+i*30F, 0.0F+i*i, i-1.0F/i/i, 1.0F*i*i);
				GlStateManager.rotate(world.getCelestialAngle(partialTicks) * 360.0F * i, 1.0F, 0.0F, 0.0F);
				f10 = moonRnd.nextFloat()*30F;
				mc.renderEngine.bindTexture(locationMoonPhasesPng);
				for(int x = 0; x < 2*i; ++x) {
					int k = moonRnd.nextInt(8);
					int l = k % 4;
					int i1 = k / 4 % 2;
					float f14 = (l + 0) / 4.0F;
					float f15 = (i1 + 0) / 2.0F;
					float f16 = (l + 1) / 4.0F;
					float f17 = (i1 + 1) / 2.0F;
					BufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
					float fR = moonRnd.nextFloat();
					float fG = moonRnd.nextFloat();
					float fB = moonRnd.nextFloat();
					float fA = moonRnd.nextFloat();
					BufferBuilder.pos(-f10, -100.0D, f10).tex(f16, f17).color(fR, fG, fB, fA).endVertex();
					BufferBuilder.pos(f10, -100.0D, f10).tex(f14, f17).color(fR, fG, fB, fA).endVertex();
					BufferBuilder.pos(f10, -100.0D, -f10).tex(f14, f15).color(fR, fG, fB, fA).endVertex();
					BufferBuilder.pos(-f10, -100.0D, -f10).tex(f16, f15).color(fR, fG, fB, fA).endVertex();
					tessellator.draw();
				}
				GlStateManager.popMatrix();
			}
		}
		GlStateManager.disableTexture2D();
		float f18 = world.getStarBrightness(partialTicks) * f6;

		if (f18 > 0.0F)
		{
			GlStateManager.color(f18, f18, f18, f18);
			//GL11.glCallList(this.starGLCallList);
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableFog();
		GlStateManager.popMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.color(0.0F, 0.0F, 0.0F);
		double d0 = mc.player.getPositionEyes(partialTicks).y - world.getHorizon();

		if (d0 < 0.0D)
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, 12.0F, 0.0F);
			//GL11.glCallList(this.glSkyList2);
			GlStateManager.popMatrix();
			float f19 = -((float)(d0 + 65.0D));
			BufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
			BufferBuilder.pos(-1.0D, f19, 1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1.0D, f19, 1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1.0D, f19, -1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1.0D, f19, -1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1.0D, f19, 1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1.0D, f19, -1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1.0D, f19, -1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1.0D, f19, 1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
			BufferBuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
			tessellator.draw();
		}

		if(world.provider.isSkyColored()) {
			GlStateManager.color(f1 * 0.2F + 0.04F, f2 * 0.2F + 0.04F, f3 * 0.6F + 0.1F);
		}
		else {
			GlStateManager.color(f1, f2, f3);
		}

		GlStateManager.pushMatrix();
		GlStateManager.translate(0.0F, -((float)(d0 - 16.0D)), 0.0F);
		//GL11.glCallList(this.glSkyList2);
		GlStateManager.popMatrix();
		GlStateManager.enableTexture2D();
		GlStateManager.depthMask(true);
	}
}
