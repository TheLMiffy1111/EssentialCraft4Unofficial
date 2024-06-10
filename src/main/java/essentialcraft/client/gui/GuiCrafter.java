package essentialcraft.client.gui;

import org.lwjgl.opengl.GL11;

import DummyCore.Client.GuiCommon;
import DummyCore.Client.GuiElement;
import essentialcraft.common.tile.TileCrafter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiCrafter extends GuiCommon{

	public TileCrafter crafter;

	public GuiCrafter(Container c, TileCrafter inv) {
		super(c);
		guiGenLocation = new ResourceLocation("textures/gui/container/crafting_table.png");
		crafter = inv;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f1, int i1, int i2) {
		GlStateManager.color(1, 1, 1);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		mc.renderEngine.bindTexture(guiGenLocation);
		this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);

		RenderHelper.disableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();

		for(Slot slt : inventorySlots.inventorySlots) {
			renderSlot(slt);
			GlStateManager.color(1, 1, 1);

		}

		GlStateManager.color(1, 1, 1);

		RenderHelper.enableStandardItemLighting();
		for(GuiElement element : elementList) {
			Minecraft.getMinecraft().renderEngine.bindTexture(element.getElementTexture());
			element.draw(k+element.getX(), l+element.getY(), i1, i2);
			GlStateManager.color(1, 1, 1);
		}
	}

	@Override
	public void initGui()
	{
		super.initGui();
	}

	@Override
	public void renderSlot(Slot slt)
	{
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		mc.renderEngine.bindTexture(slotLocation);
		if(slt.slotNumber != 9) {
			this.drawTexturedModalRect(k+slt.xPos-1, l+slt.yPos-1, 7, 83, 18, 18);
		}

		if(slt.slotNumber < 9)
		{
			if(crafter.hasFrame())
			{
				if(!slt.getHasStack())
				{
					ItemStack[] retStk = crafter.getRecipeFromFrame();
					if(!retStk[slt.slotNumber].isEmpty())
					{
						itemRender.zLevel = 100F;
						zLevel = 100F;
						GL11.glColor4d(0.5D, 0.5D, 0.5D, 1D);
						itemRender.renderItemAndEffectIntoGUI(retStk[slt.slotNumber], k+slt.xPos, l+slt.yPos);
						itemRender.zLevel = 0F;
						zLevel = 0F;
					}
				}
			}
		}
	}

}
