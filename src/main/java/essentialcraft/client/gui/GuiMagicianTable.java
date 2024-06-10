package essentialcraft.client.gui;

import DummyCore.Client.GuiCommon;
import essentialcraft.client.gui.element.GuiBalanceState;
import essentialcraft.client.gui.element.GuiBoundGemState;
import essentialcraft.client.gui.element.GuiMRUState;
import essentialcraft.client.gui.element.GuiMRUStorage;
import essentialcraft.client.gui.element.GuiProgressBar_MagicianTable;
import essentialcraft.common.tile.TileMagicianTable;
import net.minecraft.inventory.Container;

public class GuiMagicianTable extends GuiCommon{

	public GuiMagicianTable(Container c, TileMagicianTable tile) {
		super(c,tile);
		elementList.add(new GuiMRUStorage(7, 4, tile));
		elementList.add(new GuiProgressBar_MagicianTable(25, 4, tile));
		elementList.add(new GuiMRUState(25, 58, tile, 0));
		elementList.add(new GuiBalanceState(88, 22, tile));
		elementList.add(new GuiBoundGemState(88, 40, tile, 0));
	}
}
