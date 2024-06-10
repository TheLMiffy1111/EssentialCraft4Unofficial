package essentialcraft.client.gui.element;

import essentialcraft.api.IMRUDisplay;
import essentialcraft.api.IMRUHandler;
import essentialcraft.common.capabilities.mru.CapabilityMRUHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiBalanceState extends GuiTextElement{

	public IMRUHandler tile;

	public GuiBalanceState(int i, int j, IMRUHandler t)
	{
		super(i, j);
		tile = t;
	}

	public GuiBalanceState(int i, int j, TileEntity t)
	{
		super(i, j);
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
		float balance = tile.getBalance();
		String str = Float.toString(tile.getBalance());
		if(str.length() > 6) {
			str = str.substring(0, 6);
		}

		for(int i = str.length()-1; i > 0; --i)
		{
			if(i > 2)
			{
				char c = str.charAt(i);
				if(c == '0')
				{
					str = str.substring(0, i);
				}
			}
		}
		String balanceType = "Pure";
		int color = 0x00ffff;
		if(balance < 1)
		{
			balanceType = "Frozen";
			color = 0x0000ff;
		}
		if(balance > 1)
		{
			balanceType = "Chaos";
			color = 0xff0000;
		}

		Minecraft.getMinecraft().fontRenderer.drawString(balanceType+": "+str, posX+2, posY+5, color, true);
	}

}
