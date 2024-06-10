package essentialcraft.client.gui;

import DummyCore.Client.GuiCommon;
import essentialcraft.client.gui.element.GuiBoundGemState;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class GuiMRUAcceptor extends GuiCommon {

	public GuiMRUAcceptor(Container c, TileEntity tile) {
		super(c, tile);
		elementList.add(new GuiBoundGemState(48, 50, tile, 0));
	}



}
