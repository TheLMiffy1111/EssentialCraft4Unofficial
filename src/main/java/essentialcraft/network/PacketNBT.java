package essentialcraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketNBT implements IMessage {

	public NBTTagCompound theTag;
	public int packetID;

	public PacketNBT() {}

	public PacketNBT(NBTTagCompound t)
	{
		theTag = t;
	}

	public PacketNBT setID(int i)
	{
		packetID = i;
		return this;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		theTag = ByteBufUtils.readTag(buf);
		packetID = theTag.getInteger("ec3packetData.id");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		theTag.setInteger("ec3packetData.id", packetID);
		ByteBufUtils.writeTag(buf, theTag);
	}
}
