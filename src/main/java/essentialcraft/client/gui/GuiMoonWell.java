package essentialcraft.client.gui;

import DummyCore.Client.GuiCommon;
import essentialcraft.client.gui.element.GuiBalanceState;
import essentialcraft.client.gui.element.GuiHeightState;
import essentialcraft.client.gui.element.GuiMRUGenerated;
import essentialcraft.client.gui.element.GuiMRUState;
import essentialcraft.client.gui.element.GuiMRUStorage;
import essentialcraft.client.gui.element.GuiMoonState;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class GuiMoonWell extends GuiCommon{

	public GuiMoonWell(Container c, TileEntity tile) {
		super(c,tile);
		elementList.add(new GuiMRUStorage(7, 4, tile));
		elementList.add(new GuiMRUState(25, 58, tile, 0));
		elementList.add(new GuiMoonState(25, 40));
		elementList.add(new GuiHeightState(152, 40,tile));
		elementList.add(new GuiMRUGenerated(43, 40,tile,"moonwell"));
		elementList.add(new GuiBalanceState(25, 22, tile));
	}



}
