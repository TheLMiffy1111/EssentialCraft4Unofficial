package essentialcraft.client.gui.element;

import DummyCore.Client.TextureUtils;
import DummyCore.Utils.DrawUtils;
import DummyCore.Utils.MathUtils;
import essentialcraft.common.tile.TilePotionSpreader;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;

public class GuiPotionState extends GuiTextElement{
	public TilePotionSpreader tile;

	public GuiPotionState(int i, int j, TileEntity t)
	{
		super(i, j);
		tile = (TilePotionSpreader) t;
	}

	@Override
	public void draw(int posX, int posY, int mouseX, int mouseY) {
		this.drawTexturedModalRect(posX, posY, 0, 0, 17, 17);
		this.drawTexturedModalRect(posX+19, posY, 1, 0, 17, 17);
		this.drawTexturedModalRect(posX, posY+19, 0, 1, 17, 17);
		this.drawTexturedModalRect(posX+19, posY+19, 1, 1, 17, 17);
		this.drawTexturedModalRect(posX+17, posY, 1, 0, 2, 17);
		this.drawTexturedModalRect(posX+17, posY+19, 1, 1, 2, 17);
		this.drawTexturedModalRect(posX, posY+17, 0, 1, 17, 2);
		this.drawTexturedModalRect(posX+19, posY+17, 1, 1, 17, 2);
		this.drawTexturedModalRect(posX+17, posY+17, 1, 1, 2, 2);
		GlStateManager.pushMatrix();
		TextureAtlasSprite icon = TextureUtils.fromItem(Items.POTIONITEM);
		DrawUtils.drawTexture_Items(posX+9, posY+9, icon, 18, 18, 10);
		if(tile.potionID != null)
		{
			icon = TextureUtils.fromItem(Items.POTIONITEM);
			int j = Potion.REGISTRY.getObject(tile.potionID).getLiquidColor();
			float f = 0F;
			float f1 = 0F;
			float f2 = 0F;
			f += (j >> 16 & 255) / 255F;
			f1 += (j >> 8 & 255) / 255F;
			f2 += (j >> 0 & 255) / 255F;
			GlStateManager.color(f, f1, f2);
			int scale = MathUtils.pixelatedTextureSize(8-tile.potionUseTime/2, 8, 16);
			int scaledPos = scale - 4;
			if(scaledPos < 0) {
				scaledPos = 0;
			}
			DrawUtils.drawTexture_Items(posX+9, posY+9+scaledPos, icon, 18, 18-scale, 10);
		}
		GlStateManager.popMatrix();
		drawText(posX, posY);
	}

	@Override
	public void drawText(int posX, int posY) {}
}
