package essentialcraft.common.item;

import DummyCore.Client.IModelRegisterer;
import DummyCore.Utils.MiscUtils;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemSpawnerCollector extends ItemMRUGeneric implements IModelRegisterer {

	public ItemSpawnerCollector() {
		super();
		setMaxMRU(10000);
		maxStackSize = 1;
		bFull3D = true;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		Block b = world.getBlockState(pos).getBlock();
		if(b != null && b instanceof BlockMobSpawner)
		{
			if(world.getTileEntity(pos) == null || !(world.getTileEntity(pos) instanceof TileEntityMobSpawner)) {
				return EnumActionResult.PASS;
			}

			if(ECUtils.playerUseMRU(player, player.getHeldItem(hand), 5000))
			{
				TileEntityMobSpawner t = (TileEntityMobSpawner)world.getTileEntity(pos);
				NBTTagCompound mobTag = new NBTTagCompound();
				t.writeToNBT(mobTag);
				ItemStack collectedSpawner = new ItemStack(ItemsCore.collectedSpawner, 1, 0);
				MiscUtils.getStackTag(collectedSpawner).setTag("monsterSpawner", mobTag);
				EntityItem item = new EntityItem(world, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, collectedSpawner);
				if(!world.isRemote) {
					world.spawnEntity(item);
				}

				world.setBlockToAir(pos);
				player.swingArm(hand);
			}
		}

		return EnumActionResult.PASS;
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/spawnercollector", "inventory"));
	}
}
