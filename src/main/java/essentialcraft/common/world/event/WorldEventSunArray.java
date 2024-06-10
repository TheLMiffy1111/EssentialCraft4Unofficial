package essentialcraft.common.world.event;

import DummyCore.Utils.MathUtils;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import essentialcraft.api.IWorldEvent;
import essentialcraft.common.item.ItemBaublesSpecial;
import essentialcraft.utils.cfg.Config;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class WorldEventSunArray implements IWorldEvent{

	@Override
	public void onEventBeginning(World w) {
		ECUtils.sendChatMessageToAllPlayersInDim(Config.dimensionID, TextFormatting.RED+"This is going to be a hot day...");
	}

	@Override
	public void worldTick(World w, int leftoverTime) {

	}

	@Override
	public void playerTick(EntityPlayer p, int leftoverTime) {
		boolean ignoreSun = false;
		IBaublesItemHandler b = BaublesApi.getBaublesHandler(p);
		if(b != null) {
			for(int i = 0; i < b.getSlots(); ++i) {
				ItemStack is = b.getStackInSlot(i);
				if(is.getItem() instanceof ItemBaublesSpecial && is.getItemDamage() == 19) {
					ignoreSun = true;
				}
			}
		}
		if(!p.capabilities.isCreativeMode && p.dimension == Config.dimensionID && p.getEntityWorld().canBlockSeeSky(new BlockPos(MathHelper.floor(p.posX), MathHelper.floor(p.posY+2), MathHelper.floor(p.posZ))) && !ignoreSun) {
			p.attackEntityFrom(DamageSource.ON_FIRE, 1);
			p.setFire(10);
		}
		if(p.dimension == Config.dimensionID) {
			EntityFallingBlock sand = new EntityFallingBlock(p.getEntityWorld(), Math.floor(p.posX+MathUtils.randomDouble(p.getEntityWorld().rand)*128)+0.5D, 255, Math.floor(p.posZ+MathUtils.randomDouble(p.getEntityWorld().rand)*128)+0.5D, Blocks.FIRE.getDefaultState());
			sand.fallTime = 3;
			sand.shouldDropItem = false;
			if(!p.getEntityWorld().isRemote) {
				p.getEntityWorld().spawnEntity(sand);
			}
		}

	}

	@Override
	public void onEventEnd(World w) {
		ECUtils.sendChatMessageToAllPlayersInDim(Config.dimensionID, TextFormatting.GREEN+"The suns are back to normal!");
	}

	@Override
	public int getEventDuration(World w) {
		return 12000;
	}

	@Override
	public boolean possibleToApply(World w) {
		return w.getWorldTime() % 24000L == 0;
	}

	@Override
	public float getEventProbability(World w) {
		return 0.3F;
	}

	@Override
	public String getEventID() {
		return "essentialcraft.event.sunArray";
	}
}
