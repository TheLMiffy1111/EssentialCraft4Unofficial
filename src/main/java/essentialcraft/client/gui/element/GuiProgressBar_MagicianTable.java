package essentialcraft.client.gui.element;

import DummyCore.Utils.DrawUtils;
import DummyCore.Utils.MathUtils;
import essentialcraft.common.tile.TileMagicianTable;

public class GuiProgressBar_MagicianTable extends GuiTextElement{
	public TileMagicianTable tile;

	public GuiProgressBar_MagicianTable(int i, int j, TileMagicianTable table) {
		super(i, j);
		tile = table;
	}

	@Override
	public void draw(int posX, int posY, int mouseX, int mouseY) {
		DrawUtils.bindTexture("essentialcraft", "textures/gui/gui_magiciantable.png");
		int current = (int) tile.progressLevel;
		int max = (int) tile.progressRequired;
		int progress = MathUtils.pixelatedTextureSize(current, max, 18);
		this.drawTexturedModalRect(posX, posY, 0, 0, 54, 54);
		this.drawTexturedModalRect(posX, posY+18, 54, 18, progress, 18);
		this.drawTexturedModalRect(posX+18, posY, 72, 0, 18, progress);
		this.drawTexturedModalRect(posX+54-progress, posY+18, 108-progress, 18, progress, 18);
		this.drawTexturedModalRect(posX+18, posY+54-progress, 72, 54-progress, 18, progress);
	}

	@Override
	public void drawText(int posX, int posY) {}
}
