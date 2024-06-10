package essentialcraft.client.render.entity;

import essentialcraft.common.entity.EntityMRUArrow;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMRUArrow extends Render<EntityMRUArrow> {

	public RenderMRUArrow(RenderManager renderManager) {
		super(renderManager);
	}

	private static final ResourceLocation arrowTextures = new ResourceLocation("textures/entity/arrow.png");

	@Override
	public void doRender(EntityMRUArrow entity, double x, double y, double z, float entityYaw, float partialTicks) {
		bindEntityTexture(entity);
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.translate((float)x, (float)y, (float)z);
		GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90F, 0F, 1F, 0F);
		GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0F, 0F, 1F);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.enableRescaleNormal();
		float f9 = entity.arrowShake - partialTicks;

		if(f9 > 0F) {
			float f10 = -MathHelper.sin(f9 * 3F) * f9;
			GlStateManager.rotate(f10, 0F, 0F, 1F);
		}

		GlStateManager.rotate(45F, 1F, 0F, 0F);
		GlStateManager.scale(0.05625F, 0.05625F, 0.05625F);
		GlStateManager.translate(-4F, 0F, 0F);

		if(renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(getTeamColor(entity));
		}

		GlStateManager.glNormal3f(0.05625F, 0F, 0F);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(-7D, -2D, -2D).tex(0D, 0.15625D).endVertex();
		bufferbuilder.pos(-7D, -2D, 2D).tex(0.15625D, 0.15625D).endVertex();
		bufferbuilder.pos(-7D, 2D, 2D).tex(0.15625D, 0.3125D).endVertex();
		bufferbuilder.pos(-7D, 2D, -2D).tex(0D, 0.3125D).endVertex();
		tessellator.draw();
		GlStateManager.glNormal3f(-0.05625F, 0F, 0F);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(-7D, 2D, -2D).tex(0D, 0.15625D).endVertex();
		bufferbuilder.pos(-7D, 2D, 2D).tex(0.15625D, 0.15625D).endVertex();
		bufferbuilder.pos(-7D, -2D, 2D).tex(0.15625D, 0.3125D).endVertex();
		bufferbuilder.pos(-7D, -2D, -2D).tex(0D, 0.3125D).endVertex();
		tessellator.draw();

		for(int j = 0; j < 4; ++j) {
			GlStateManager.rotate(90F, 1F, 0F, 0F);
			GlStateManager.glNormal3f(0F, 0F, 0.05625F);
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			bufferbuilder.pos(-8D, -2D, 0D).tex(0D, 0D).endVertex();
			bufferbuilder.pos(8D, -2D, 0D).tex(0.5D, 0D).endVertex();
			bufferbuilder.pos(8D, 2D, 0D).tex(0.5D, 0.15625D).endVertex();
			bufferbuilder.pos(-8D, 2D, 0D).tex(0D, 0.15625D).endVertex();
			tessellator.draw();
		}

		if(renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.disableRescaleNormal();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMRUArrow entity) {
		return arrowTextures;
	}

	public static class Factory implements IRenderFactory<EntityMRUArrow> {
		@Override
		public Render<? super EntityMRUArrow> createRenderFor(RenderManager manager) {
			return new RenderMRUArrow(manager);
		}
	}
}
