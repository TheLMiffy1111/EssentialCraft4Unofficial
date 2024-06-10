package essentialcraft.client.gui.element;

import DummyCore.Client.GuiElement;
import DummyCore.Utils.DrawUtils;
import DummyCore.Utils.MathUtils;
import essentialcraft.api.IMRUDisplay;
import essentialcraft.api.IMRUHandler;
import essentialcraft.common.capabilities.mru.CapabilityMRUHandler;
import essentialcraft.common.mod.EssentialCraftCore;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiMRUStorage extends GuiElement{
	private ResourceLocation rec = new ResourceLocation("essentialcraft", "textures/gui/mrustorage.png");

	public int x;
	public int y;
	public IMRUHandler tile;

	public GuiMRUStorage(int i, int j, IMRUHandler t) {
		x = i;
		y = j;
		tile = t;
	}

	public GuiMRUStorage(int i, int j, TileEntity t) {
		x = i;
		y = j;
		if(t.hasCapability(CapabilityMRUHandler.MRU_HANDLER_CAPABILITY, null)) {
			tile = t.getCapability(CapabilityMRUHandler.MRU_HANDLER_CAPABILITY, null);
		}
		else if(t instanceof IMRUDisplay) {
			tile = ((IMRUDisplay)t).getMRUHandler();
		}
		else {
			throw new IllegalArgumentException("Tile does not handle MRU");
		}
	}

	@Override
	public ResourceLocation getElementTexture() {
		return rec;
	}

	@Override
	public void draw(int posX, int posY, int mouseX, int mouseY) {
		this.drawTexturedModalRect(posX, posY, 0, 0, 18, 72);
		int percentageScaled = MathUtils.pixelatedTextureSize(tile.getMRU(), tile.getMaxMRU(), 72);
		TextureAtlasSprite icon = (TextureAtlasSprite) EssentialCraftCore.proxy.getClientIcon("mru");
		DrawUtils.drawTexture(posX+1, posY+73-percentageScaled, icon, 16, percentageScaled-2, 0);
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
