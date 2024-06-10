package essentialcraft.client.gui.element;

import essentialcraft.common.capabilities.mru.CapabilityMRUHandler;
import essentialcraft.common.tile.TileCrystalController;
import essentialcraft.common.tile.TileElementalCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class GuiCrystalState extends GuiTextElement{
	public TileCrystalController tile;

	public GuiCrystalState(int i, int j, TileEntity t)
	{
		super(i, j);
		tile = (TileCrystalController) t;
	}

	@Override
	public void draw(int posX, int posY, int mouseX, int mouseY) {
		if(tile.getCapability(CapabilityMRUHandler.MRU_HANDLER_CAPABILITY, null).getMRU() > 0)
		{
			this.drawTexturedModalRect(posX, posY, 0, 0, 17, 17);
			//this.drawTexturedModalRect(posX+19, posY, 1, 0, 17, 17);
			this.drawTexturedModalRect(posX, posY+19, 0, 1, 17, 17);
			//this.drawTexturedModalRect(posX+19, posY+19, 1, 1, 17, 17);
			this.drawTexturedModalRect(posX, posY+17, 0, 1, 17, 2);
			//this.drawTexturedModalRect(posX+19, posY+17, 1, 1, 17, 2);


			for(int x = 0; x < 5; ++x)
			{
				this.drawTexturedModalRect(posX+16+16*x, posY, 1, 0, 16, 17);
				this.drawTexturedModalRect(posX+16+16*x, posY+19, 1, 1, 16, 17);
				this.drawTexturedModalRect(posX+16+16*x, posY+17, 1, 1, 16, 2);
			}

			this.drawTexturedModalRect(posX+3+16*5, posY, 1, 0, 17, 17);
			this.drawTexturedModalRect(posX+3+16*5, posY+19, 1, 1, 17, 17);
			this.drawTexturedModalRect(posX+3+16*5, posY+17, 1, 1, 17, 2);

			GlStateManager.pushMatrix();

			GlStateManager.popMatrix();
			drawText(posX, posY);
		}
	}

	@Override
	public void drawText(int posX, int posY) {
		TileElementalCrystal crystal = tile.getCrystal();
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		if(crystal != null)
		{
			fontRenderer.drawStringWithShadow("Fire: "+(int)crystal.fire+"%", posX+2, posY+4, 0xffffff);
			fontRenderer.drawStringWithShadow("Water: "+(int)crystal.water+"%", posX+2, posY+14, 0xffffff);
			fontRenderer.drawStringWithShadow("Earth: "+(int)crystal.earth+"%", posX+2, posY+24, 0xffffff);
			fontRenderer.drawStringWithShadow("Air: "+(int)crystal.air+"%", posX+50, posY+4, 0xffffff);
			fontRenderer.drawStringWithShadow("Size: "+(int)crystal.size+"%", posX+50, posY+14, 0xffffff);
			ItemStack e = tile.getStackInSlot(1);
			if(!e.isEmpty())
			{
				int rarity = (int)((float)e.getItemDamage()/4);
				float chance = 2*(rarity+1);
				fontRenderer.drawStringWithShadow("Chance: "+(int)chance+"%", posX+50, posY+24, 0xffffff);
			}
		}
	}

}
