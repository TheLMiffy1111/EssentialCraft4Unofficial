package essentialcraft.client.gui.element;

import DummyCore.Utils.DrawUtils;
import DummyCore.Utils.MathUtils;
import essentialcraft.common.tile.TileFurnaceMagic;
import net.minecraft.util.ResourceLocation;

public class GuiProgressBar_FurnaceMagic extends GuiTextElement{
	public TileFurnaceMagic tile;

	public GuiProgressBar_FurnaceMagic(int i, int j, TileFurnaceMagic table)
	{
		super(i,j);
		tile = table;
	}

	@Override
	public ResourceLocation getElementTexture() {
		return super.getElementTexture();
	}

	@Override
	public void draw(int posX, int posY, int mouseX, int mouseY) {
		DrawUtils.bindTexture("essentialcraft", "textures/gui/progressbars.png");
		int current = tile.progressLevel;
		if(current == 0) {
			current = tile.smeltingLevel;
		}

		int max = TileFurnaceMagic.smeltingTime/(tile.getBlockMetadata()/4+1);
		//System.out.println(current);
		int progress = MathUtils.pixelatedTextureSize(current, max, 25);
		this.drawTexturedModalRect(posX, posY, 0, 17, 24, 17);
		this.drawTexturedModalRect(posX, posY, 0, 0, progress, 17);
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

	}

}
