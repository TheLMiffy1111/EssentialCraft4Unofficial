package essentialcraft.client.gui.element;

import DummyCore.Client.GuiElement;
import essentialcraft.api.IESPEHandler;
import essentialcraft.common.capabilities.espe.CapabilityESPEHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiESPEStorage extends GuiElement {

	private ResourceLocation rec = new ResourceLocation("essentialcraft", "textures/gui/mithrilinefurnaceelements.png");

	public int x;
	public int y;
	public IESPEHandler tile;

	public GuiESPEStorage(int i, int j, IESPEHandler t) {
		x = i;
		y = j;
		tile = t;
	}

	public GuiESPEStorage(int i, int j, TileEntity t) {
		x = i;
		y = j;
		if(!t.hasCapability(CapabilityESPEHandler.ESPE_HANDLER_CAPABILITY, null)) {
			throw new IllegalArgumentException("Tile does not handle ESPE");
		}
		tile = t.getCapability(CapabilityESPEHandler.ESPE_HANDLER_CAPABILITY, null);
	}

	@Override
	public ResourceLocation getElementTexture() {
		return rec;
	}

	@Override
	public void draw(int posX, int posY, int mouseX, int mouseY) {
		double current = tile.getESPE();
		double max = tile.getMaxESPE();

		double m = current/max;
		int n = MathHelper.floor(m*18);

		this.drawTexturedModalRect(posX, posY, 0, 14, 18, 18);
		this.drawTexturedModalRect(posX, posY+18-n, 18, 32-n, 18, n);
		this.drawTexturedModalRect(posX+70, posY-49, 0, 32, 28, 28);
		this.drawTexturedModalRect(posX+77, posY-16, 0, 0, 14, 14);
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
}
