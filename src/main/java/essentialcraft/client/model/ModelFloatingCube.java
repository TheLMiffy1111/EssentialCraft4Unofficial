package essentialcraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelFloatingCube extends ModelBase {

	private ModelRenderer cube;
	private ModelRenderer glass = new ModelRenderer(this, "glass");
	private ModelRenderer base;

	public ModelFloatingCube(float f, boolean hasBase) {
		glass.setTextureOffset(0, 0).addBox(-4F, -4F, -4F, 8, 8, 8);
		cube = new ModelRenderer(this, "cube");
		cube.setTextureOffset(32, 0).addBox(-4F, -4F, -4F, 8, 8, 8);
		if(hasBase) {
			base = new ModelRenderer(this, "base");
			base.setTextureOffset(0, 16).addBox(-6F, 0F, -6F, 12, 4, 12);
		}
	}

	public void render(TileEntity tile, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch, float scale) {
		GlStateManager.pushMatrix();
		GlStateManager.scale(2F, 2F, 2F);
		GlStateManager.translate(0F, -0.5F, 0F);
		GlStateManager.rotate(limbSwingAmount, 0F, 1F, 0F);
		GlStateManager.translate(0F, 0.8F + age, 0F);
		GlStateManager.rotate(60F, 0.7071F, 0F, 0.7071F);
		glass.render(scale);
		float s = 0.875F;
		GlStateManager.scale(s, s, s);
		GlStateManager.rotate(60F, 0.7071F, 0F, 0.7071F);
		GlStateManager.rotate(limbSwingAmount, 0F, 1F, 0F);
		GlStateManager.scale(s, s, s);
		GlStateManager.rotate(60F, 0.7071F, 0F, 0.7071F);
		GlStateManager.rotate(limbSwingAmount, 0F, 1F, 0F);
		cube.render(scale);
		GlStateManager.popMatrix();
	}
}
