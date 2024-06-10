package essentialcraft.network;

import java.util.UUID;

import DummyCore.Utils.MiscUtils;
import essentialcraft.client.gui.GuiMIMScreen;
import essentialcraft.common.capabilities.mru.CapabilityMRUHandler;
import essentialcraft.common.mod.EssentialCraftCore;
import essentialcraft.common.tile.TileMIM;
import essentialcraft.common.tile.TileMIMInventoryStorage;
import essentialcraft.common.tile.TileMIMScreen;
import essentialcraft.common.tile.TileMagicalQuarry;
import essentialcraft.utils.common.ECUtils;
import io.netty.channel.ChannelHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@ChannelHandler.Sharable
public class ECPacketDispatcher implements IMessageHandler<PacketNBT, IMessage>{

	@Override
	public IMessage onMessage(PacketNBT message, MessageContext ctx) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(()->handleMessage(message, ctx));
		return null;
	}

	public void handleMessage(PacketNBT message, MessageContext ctx) {
		switch(message.packetID) {
		case 0: {
			//Generic data SYNC.
			UUID p = message.theTag.hasKey("syncplayer") ? UUID.fromString(message.theTag.getString("syncplayer")) : null;
			ECUtils.readOrCreatePlayerData(p != null ? p : MiscUtils.getUUIDFromPlayer(EssentialCraftCore.proxy.getClientPlayer()), message.theTag);
			break;
		}
		case 1: {
			//Server-side to client Sync request
			EntityPlayer target = MiscUtils.getPlayerFromUUID(message.theTag.getString("syncplayer"));
			EntityPlayer sender = MiscUtils.getPlayerFromUUID(message.theTag.getString("sender"));
			if(target != null) {
				NBTTagCompound theTag = new NBTTagCompound();
				theTag.setString("syncplayer", message.theTag.getString("syncplayer"));
				ECUtils.getData(target).writeToNBTTagCompound(theTag);
				PacketNBT pkt = new PacketNBT(theTag).setID(0);
				EssentialCraftCore.network.sendTo(pkt, (EntityPlayerMP)sender);
			}
			break;
		}
		case 2: {
			ECUtils.ec3WorldTag = message.theTag;
			break;
		}
		case 3: {
			TileEntity tile = EssentialCraftCore.proxy.getClientPlayer().getEntityWorld().getTileEntity(new BlockPos(message.theTag.getInteger("x"), message.theTag.getInteger("y"), message.theTag.getInteger("z")));
			if(tile != null && tile instanceof TileMIMInventoryStorage) {
				((TileMIMInventoryStorage)tile).items.clear();
				for(int i = 0; i < message.theTag.getTagList("items", 10).tagCount(); ++i) {
					ItemStack s = new ItemStack(message.theTag.getTagList("items", 10).getCompoundTagAt(i));
					s.setCount(message.theTag.getTagList("items", 10).getCompoundTagAt(i).getInteger("stackSize"));
					((TileMIMInventoryStorage)tile).items.add(s);
				}
			}
			break;
		}
		case 4: {
			TileEntity tile = EssentialCraftCore.proxy.getClientPlayer().getEntityWorld().getTileEntity(new BlockPos(message.theTag.getInteger("x"), message.theTag.getInteger("y"), message.theTag.getInteger("z")));
			if(tile != null && tile instanceof TileMagicalQuarry)
			{
				((TileMagicalQuarry)tile).miningX = message.theTag.getInteger("mx");
				((TileMagicalQuarry)tile).miningY = message.theTag.getInteger("my");
				((TileMagicalQuarry)tile).miningZ = message.theTag.getInteger("mz");
			}
			break;
		}
		case 5: {
			ItemStack retrieved = new ItemStack(message.theTag.getCompoundTag("requestedItem"));

			EntityPlayerMP requester = (EntityPlayerMP)MiscUtils.getPlayerFromUUID(message.theTag.getString("requester"));

			if(!message.theTag.getBoolean("craft")) {
				retrieved.setCount(message.theTag.getInteger("size"));
			}

			int size = message.theTag.getInteger("size");
			TileEntity tile = requester.getEntityWorld().getTileEntity(new BlockPos(message.theTag.getInteger("px"), message.theTag.getInteger("py"), message.theTag.getInteger("pz")));
			if(tile != null && tile instanceof TileMIM) {
				TileMIM mim = (TileMIM)tile;

				if(!message.theTag.getBoolean("craft")) {
					int left = mim.retrieveItemStackFromSystem(retrieved, false, true);
					if(left == 0) {

					}
					else {
						size -= left;
					}
					TileEntity t = requester.getEntityWorld().getTileEntity(new BlockPos(message.theTag.getInteger("x"), message.theTag.getInteger("y"), message.theTag.getInteger("z")));
					if(t != null && t instanceof TileMIMScreen) {
						TileMIMScreen sc = (TileMIMScreen)t;
						int left64 = size % 64;
						int times64 = size/64;
						for(int i = 0; i < size; ++i) {
							sc.getCapability(CapabilityMRUHandler.MRU_HANDLER_CAPABILITY, null).extractMRU(TileMIMScreen.mruForOut, true);
						}
						for(int i = 0; i < times64; ++i) {
							ItemStack added = retrieved.copy();
							added.setCount(64);
							if(!requester.inventory.addItemStackToInventory(added)) {
								requester.dropItem(added, false);
							}
						}
						ItemStack added = retrieved.copy();
						added.setCount(left64);
						if(!requester.inventory.addItemStackToInventory(added)) {
							requester.dropItem(added, false);
						}

						NBTTagCompound canDo = new NBTTagCompound();
						PacketNBT packet = new PacketNBT(canDo).setID(6);
						EssentialCraftCore.network.sendTo(packet, requester);
					}
				}
				else {
					mim.craftFromTheSystem(retrieved, size);
					NBTTagCompound canDo = new NBTTagCompound();
					PacketNBT packet = new PacketNBT(canDo).setID(6);
					EssentialCraftCore.network.sendTo(packet, requester);
				}
			}
			break;
		}
		case 6: {
			GuiMIMScreen.packetArrived = true;
			break;
		}
		case 7: {
			NBTTagCompound tag = message.theTag;
			if(tag != null && !tag.isEmpty()) {
				String playername = tag.getString("playername");
				EntityPlayer requester = MiscUtils.getPlayerFromUUID(playername);
				if(requester != null) {
					int dimID = tag.getInteger("dim");
					WorldServer world = DimensionManager.getWorld(dimID);
					if(world != null) {
						int x = tag.getInteger("x");
						int y = tag.getInteger("y");
						int z = tag.getInteger("z");
						if(world.isBlockLoaded(new BlockPos(x, y, z))) {
							if(world.getTileEntity(new BlockPos(x, y, z)) != null) {
								ECUtils.requestScheduledTileSync(world.getTileEntity(new BlockPos(x, y, z)), requester);
							}
						}
					}
				}
			}
			break;
		}
		}
	}
}
