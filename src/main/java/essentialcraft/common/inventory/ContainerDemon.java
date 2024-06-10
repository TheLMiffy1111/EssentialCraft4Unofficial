package essentialcraft.common.inventory;

import DummyCore.Utils.ContainerInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerDemon extends ContainerInventory {

	public ContainerDemon(EntityPlayer player, Entity entity) {
		super(player, entity);
	}

	@Override
	public void setupSlots() {
		addSlotToContainer(new SlotGeneric(inv, 0, 80, 30));
		setupPlayerInventory();
	}
}
