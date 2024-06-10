package essentialcraft.client.gui;

import DummyCore.Utils.DrawUtils;
import essentialcraft.common.inventory.ContainerMIMCraftingManager;
import essentialcraft.common.tile.TileMIMCraftingManager;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiMIMCraftingManager extends GuiContainer{

	TileMIMCraftingManager tile;

	public GuiMIMCraftingManager(InventoryPlayer inventoryPlayer, TileMIMCraftingManager t) {
		super(new ContainerMIMCraftingManager(inventoryPlayer, t));
		tile = t;

		xSize = 176;
		ySize = 222;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mX, int mY) {
		int k = (width - xSize)/2;
		int l = (height - ySize)/2;

		DrawUtils.bindTexture("essentialcraft", "textures/gui/magical_chest.png");

		this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}
}
