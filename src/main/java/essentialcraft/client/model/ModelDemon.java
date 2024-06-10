package essentialcraft.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelDemon extends ModelBiped {

	public ModelRenderer bipedWings;
	public ModelRenderer bipedWingsBack;
	private static final ResourceLocation wingsTexture = new ResourceLocation("essentialcraft", "textures/entities/demon_wings.png");

	public ModelDemon(float scale, float bodyPos, int textureWidth, int textureHeight) {
		bipedWings = new ModelRenderer(this, 0, 0);
		bipedWings.addBox(-16F, -4F, 2.1F, 32, 16, 0, 0);
		bipedWings.setRotationPoint(0F, 0F + bodyPos, 0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
		super.render(entity, limbSwing, limbSwingAmount, age, yaw, pitch, scale);
		Minecraft.getMinecraft().renderEngine.bindTexture(wingsTexture);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		bipedWings.render(scale);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale, Entity entity) {
		super.setRotationAngles(limbSwing, limbSwingAmount, age, yaw, pitch, scale, entity);
		bipedWings.rotateAngleX = bipedBody.rotateAngleX;
		bipedWings.rotateAngleY = bipedBody.rotateAngleY;
		bipedWings.rotateAngleZ = bipedBody.rotateAngleZ;
	}
}
