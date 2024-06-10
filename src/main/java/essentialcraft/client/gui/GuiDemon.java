package essentialcraft.client.gui;

import DummyCore.Client.GuiCommon;
import essentialcraft.common.entity.EntityDemon;
import essentialcraft.common.inventory.ContainerDemon;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiDemon extends GuiCommon {

	public ResourceLocation DguiGenLocation = new ResourceLocation("essentialcraft", "textures/gui/demon.png");

	public GuiDemon(Container c) {
		super(c);
	}

	private void drawItemStack(ItemStack stack, int x, int y, String text) {
		FontRenderer font = null;
		if(stack != null) {
			font = stack.getItem().getFontRenderer(stack);
		}
		if(font == null) {
			font = fontRenderer;
		}
		itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		itemRender.renderItemOverlayIntoGUI(font, stack, x, y, text);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f1, int i1, int i2) {
		GlStateManager.color(1, 1, 1);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		mc.renderEngine.bindTexture(DguiGenLocation);
		this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		ContainerDemon cd = (ContainerDemon)inventorySlots;
		EntityDemon demon = (EntityDemon)cd.entity;
		if(demon != null && !demon.desiredItem.isEmpty()) {
			GlStateManager.translate(0, 0, 100);
			drawItemStack(demon.desiredItem, k + 80, l + 30, demon.desiredItem.getCount()+"");
			fontRenderer.drawString(demon.desiredItem.getDisplayName(), k + 5, l + 59, 0xffffff);
			GlStateManager.translate(0, 0, -100);
		}
	}
}
