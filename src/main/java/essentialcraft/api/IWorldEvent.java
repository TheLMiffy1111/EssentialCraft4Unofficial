package essentialcraft.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IWorldEvent {

	public void onEventBeginning(World world);

	public void worldTick(World world, int leftoverTime);

	public void playerTick(EntityPlayer player, int leftoverTime);

	public void onEventEnd(World world);

	public int getEventDuration(World world);

	public boolean possibleToApply(World world);

	public float getEventProbability(World world);

	public String getEventID();
}
