package essentialcraft.common.world.event;

import essentialcraft.api.IWorldEvent;
import essentialcraft.common.world.dim.WorldProviderHoanna;
import essentialcraft.utils.cfg.Config;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class WorldEventDarkness implements IWorldEvent{

	@Override
	public void onEventBeginning(World w) {
		ECUtils.sendChatMessageToAllPlayersInDim(Config.dimensionID, TextFormatting.RED+"Why is it so dark?!");
		((WorldProviderHoanna)w.provider).generateLightBrightnessTable();
	}

	@Override
	public void worldTick(World w, int leftoverTime) {
		if(w.provider.getDimension() == Config.dimensionID) {
			((WorldProviderHoanna)w.provider).generateLightBrightnessTable();
		}
	}

	@Override
	public void playerTick(EntityPlayer p, int leftoverTime) {

	}

	@Override
	public void onEventEnd(World w) {
		ECUtils.sendChatMessageToAllPlayersInDim(Config.dimensionID, TextFormatting.GREEN+"The lights are back!");
		if(w.provider.getDimension() == Config.dimensionID) {
			((WorldProviderHoanna)w.provider).generateLightBrightnessTable();
		}
	}

	@Override
	public int getEventDuration(World w) {
		return 24000*(w.rand.nextInt(3)+1);
	}

	@Override
	public boolean possibleToApply(World w) {
		return w.provider.getDimension()  == Config.dimensionID;
	}

	@Override
	public float getEventProbability(World w) {
		return 0.00005F;
	}

	@Override
	public String getEventID() {
		return "essentialcraft.event.darkness";
	}
}
