package essentialcraft.client.gui;

import DummyCore.Client.GuiCommon;
import DummyCore.Utils.MiscUtils;
import essentialcraft.common.tile.TileWeaponMaker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class GuiWeaponBench extends GuiCommon {

	public GuiWeaponBench(Container c, TileEntity tile)
	{
		super(c, tile);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		buttonList.add(new GuiButton(0, k+145, l+20, 28, 12, "Done"));
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		MiscUtils.handleButtonPress(par1GuiButton.id, this.getClass(), GuiButton.class, Minecraft.getMinecraft().player, genericTile.getPos().getX(), genericTile.getPos().getY(), genericTile.getPos().getZ());
	}

	public ResourceLocation guiGenLocation_0 = new ResourceLocation("essentialcraft", "textures/gui/pistol_maker.png");
	public ResourceLocation guiGenLocation_1 = new ResourceLocation("essentialcraft", "textures/gui/rifle_maker.png");
	public ResourceLocation guiGenLocation_2 = new ResourceLocation("essentialcraft", "textures/gui/sniper_maker.png");
	public ResourceLocation guiGenLocation_3 = new ResourceLocation("essentialcraft", "textures/gui/gatling_maker.png");

	private void drawItemStack(ItemStack p_146982_1_, int p_146982_2_, int p_146982_3_, String p_146982_4_)
	{
		FontRenderer font = null;
		if (p_146982_1_ != null) {
			font = p_146982_1_.getItem().getFontRenderer(p_146982_1_);
		}
		if (font == null) {
			font = fontRenderer;
		}
		itemRender.renderItemAndEffectIntoGUI(p_146982_1_, p_146982_2_, p_146982_3_);
		itemRender.renderItemOverlayIntoGUI(font, p_146982_1_, p_146982_2_, p_146982_3_ - 0, p_146982_4_);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f1, int i1, int i2)
	{
		GlStateManager.color(1, 1, 1);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		TileWeaponMaker w = (TileWeaponMaker)genericTile;
		String t = "item.ec3.gun.pistol.name";
		if(w.index == 1) {
			t = "item.ec3.gun.rifle.name";
		}
		if(w.index == 2) {
			t = "item.ec3.gun.sniper.name";
		}
		if(w.index == 3) {
			t = "item.ec3.gun.gatling.name";
		}

		if(w.index == 0) {
			mc.renderEngine.bindTexture(guiGenLocation_0);
		}
		if(w.index == 1) {
			mc.renderEngine.bindTexture(guiGenLocation_1);
		}
		if(w.index == 2) {
			mc.renderEngine.bindTexture(guiGenLocation_2);
		}
		if(w.index == 3) {
			mc.renderEngine.bindTexture(guiGenLocation_3);
		}

		this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);

		fontRenderer.drawString(I18n.translateToLocal(t), k+60, l+5, 0x000000);

		if(!w.previewStack.isEmpty())
		{
			drawItemStack(w.previewStack, k+153, l+5, "");
		}
		if(!w.areIngridientsCorrect())
		{
			buttonList.get(0).enabled = false;
		}else
		{
			buttonList.get(0).enabled = true;
		}
	}
}
