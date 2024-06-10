package essentialcraft.client.gui.element;

import essentialcraft.common.tile.TileMRUCUECController;
import essentialcraft.common.tile.TileMRUCUECStateChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiResistanceState extends GuiTextElement{

	public TileEntity tile;

	public GuiResistanceState(int i, int j, TileEntity t) {
		super(i, j);
		tile = t;
	}

	@Override
	public ResourceLocation getElementTexture() {
		return super.getElementTexture();
	}

	@Override
	public void draw(int posX, int posY, int mouseX, int mouseY) {
		super.draw(posX, posY, mouseX, mouseY);
	}

	@Override
	public int getX() {
		return super.getX();
	}

	@Override
	public int getY() {
		return super.getY();
	}

	@Override
	public void drawText(int posX, int posY) {
		if(tile instanceof TileMRUCUECStateChecker) {
			TileMRUCUECController controllerTile = (TileMRUCUECController) ((TileMRUCUECStateChecker)tile).structureController();
			if(controllerTile != null) {
				Minecraft.getMinecraft().fontRenderer.drawString(controllerTile.resistance+" MROV", posX+2, posY+5, 0xffffff, true);
			}
		}
	}

}
