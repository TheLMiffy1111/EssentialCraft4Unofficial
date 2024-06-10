package essentialcraft.client.gui.element;

import DummyCore.Utils.DrawUtils;
import essentialcraft.common.tile.TileMithrilineFurnace;
import net.minecraft.util.math.MathHelper;

public class GuiProgressBar_MithrilineFurnace extends GuiTextElement{
	public TileMithrilineFurnace tile;

	public GuiProgressBar_MithrilineFurnace(int i, int j, TileMithrilineFurnace furnace) {
		super(i, j);
		tile = furnace;
	}

	@Override
	public void draw(int posX, int posY, int mouseX, int mouseY) {
		DrawUtils.bindTexture("essentialcraft", "textures/gui/mithrilinefurnaceelements.png");
		double current = tile.progress;
		double max = tile.reqProgress;
		if(max > 0) {
			double m = current/max;
			int n = MathHelper.floor(m*14);
			this.drawTexturedModalRect(posX, posY-n, 14, 14-n, 14, n);
		}
	}

	@Override
	public void drawText(int posX, int posY) {}
}
