package essentialcraft.client.gui;

import DummyCore.Client.GuiCommon;
import essentialcraft.client.gui.element.GuiESPEStorage;
import essentialcraft.client.gui.element.GuiProgressBar_MithrilineFurnace;
import essentialcraft.common.tile.TileMithrilineFurnace;
import net.minecraft.inventory.Container;

public class GuiMithrilineFurnace extends GuiCommon {

	public GuiMithrilineFurnace(Container c, TileMithrilineFurnace tile) {
		super(c, tile);
		elementList.add(new GuiESPEStorage(4, 64, tile));
		elementList.add(new GuiProgressBar_MithrilineFurnace(81, 62, tile));
	}
}
