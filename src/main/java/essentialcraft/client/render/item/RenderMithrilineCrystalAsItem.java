package essentialcraft.client.render.item;

import java.util.List;

import com.google.common.collect.ImmutableList;

import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import essentialcraft.client.render.tile.RenderMithrilineCrystal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.model.IModelState;

public class RenderMithrilineCrystalAsItem implements IItemRenderer {

	@Override
	public void renderItem(ItemStack item, TransformType type) {
		int metadata = item.getItemDamage();
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.5F, -0.5F, 0.5F);
		GlStateManager.scale(2, 2, 2);
		Minecraft.getMinecraft().renderEngine.bindTexture(metadata == 0 ? RenderMithrilineCrystal.textures_mithriline : metadata == 3 ? RenderMithrilineCrystal.textures_pale : metadata == 6 ? RenderMithrilineCrystal.textures_void : metadata == 9 ? RenderMithrilineCrystal.textures_demonic : RenderMithrilineCrystal.textures_shade);
		RenderMithrilineCrystal.model.renderAll();
		GlStateManager.popMatrix();
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		return ImmutableList.<BakedQuad>of();
	}

	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return false;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return true;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/glass");
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;
	}

	@Override
	public IModelState getTransforms() {
		return TransformUtils.DEFAULT_BLOCK;
	}
}
