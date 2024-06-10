package essentialcraft.client.gui;

import DummyCore.Client.GuiCommon;
import essentialcraft.client.gui.element.GuiBalanceState;
import essentialcraft.client.gui.element.GuiBoundGemState;
import essentialcraft.client.gui.element.GuiMRUState;
import essentialcraft.client.gui.element.GuiMRUStorage;
import essentialcraft.client.gui.element.GuiPotionState;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class GuiPotionSpreader extends GuiCommon{

	public GuiPotionSpreader(Container c, TileEntity tile) {
		super(c, tile);
		elementList.add(new GuiMRUStorage(7, 4, tile));
		elementList.add(new GuiBalanceState(25, 4, tile));
		elementList.add(new GuiBoundGemState(90, 4, tile, 0));
		elementList.add(new GuiMRUState(25, 58, tile, 0));
		elementList.add(new GuiPotionState(97, 22, tile));
	}



}
