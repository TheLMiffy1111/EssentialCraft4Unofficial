package essentialcraft.client.gui;

import DummyCore.Client.GuiCommon;
import essentialcraft.client.gui.element.GuiBalanceState;
import essentialcraft.client.gui.element.GuiBoundGemStateDimTransciever;
import essentialcraft.client.gui.element.GuiMRUState;
import essentialcraft.client.gui.element.GuiMRUStorage;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class GuiMRUDimTransciever extends GuiCommon {

	public GuiMRUDimTransciever(Container c, TileEntity tile) {
		super(c, tile);
		elementList.add(new GuiMRUStorage(7, 4, tile));
		elementList.add(new GuiBoundGemStateDimTransciever(88, 4, tile, 0));
		elementList.add(new GuiBalanceState(46, 31, tile));
		elementList.add(new GuiMRUState(25, 58, tile, 0));
	}
}
