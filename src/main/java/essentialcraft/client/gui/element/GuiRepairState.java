package essentialcraft.client.gui.element;

import essentialcraft.common.capabilities.mru.CapabilityMRUHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public class GuiRepairState extends GuiTextElement{

	public TileEntity tile;
	public int slotNum;

	public GuiRepairState(int i, int j, TileEntity t, int slot)
	{
		super(i,j);
		tile = t;
		slotNum = slot;
	}

	@Override
	public void draw(int posX, int posY, int mouseX, int mouseY) {
		this.drawTexturedModalRect(posX, posY, 0, 0, 17, 18);
		this.drawTexturedModalRect(posX+17, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+16, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+32, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+48, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+64, posY, 1, 0, 16, 18);
		this.drawTexturedModalRect(posX+17+80, posY, 1, 0, 17, 18);
		drawText(posX, posY);
	}

	@Override
	public void drawText(int posX, int posY) {
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		IInventory inventory = (IInventory) tile;
		if(inventory.getStackInSlot(1).isEmpty()) {
			fontRenderer.drawStringWithShadow("Nothing To Fix!", posX+5, posY+6, 0xffff00);
		}
		else if(!inventory.getStackInSlot(1).getItem().isRepairable()) {
			fontRenderer.drawStringWithShadow("Can't Fix This!", posX+5, posY+6, 0xff0000);
		}
		else if(inventory.getStackInSlot(1).getItemDamage() == 0) {
			fontRenderer.drawStringWithShadow("Already Fixed!", posX+5, posY+6, 0xffff00);
		}
		else if(tile.getCapability(CapabilityMRUHandler.MRU_HANDLER_CAPABILITY, null).getMRU() == 0) {
			fontRenderer.drawStringWithShadow("No MRU!", posX+5, posY+6, 0xff0000);
		}
		else {
			fontRenderer.drawStringWithShadow("Working...", posX+5, posY+6, 0x00ff00);
		}
	}
}
