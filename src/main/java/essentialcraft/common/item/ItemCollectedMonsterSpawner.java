package essentialcraft.common.item;

import java.util.List;

import DummyCore.Client.IModelRegisterer;
import DummyCore.Utils.MiscUtils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCollectedMonsterSpawner extends Item implements IModelRegisterer {

	@Override
	public EnumActionResult onItemUse(EntityPlayer placer, World w, BlockPos pos, EnumHand hand, EnumFacing side, float vecX, float vecY, float vecZ) {
		ItemStack stk = placer.getHeldItem(hand);
		BlockPos nP = pos.offset(side);
		if(placer.canPlayerEdit(nP, side.getOpposite(), stk)) {
			NBTTagCompound tag = MiscUtils.getStackTag(stk);
			if(tag.hasKey("monsterSpawner") && Blocks.MOB_SPAWNER.canPlaceBlockAt(w, nP)) {
				NBTTagCompound spawnerTag = tag.getCompoundTag("monsterSpawner");
				spawnerTag.setInteger("x", nP.getX());
				spawnerTag.setInteger("y", nP.getY());
				spawnerTag.setInteger("z", nP.getZ());
				if(w.setBlockState(nP, Blocks.MOB_SPAWNER.getDefaultState(), 3)) {
					TileEntity tile = w.getTileEntity(nP);
					if(tile != null) {
						tile.readFromNBT(spawnerTag);
					}

					placer.swingArm(hand);
					stk.shrink(1);
				}
			}
		}
		return EnumActionResult.PASS;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stk, World player, List<String> lst, ITooltipFlag flag) {
		super.addInformation(stk, player, lst, flag);
		NBTTagCompound tag = MiscUtils.getStackTag(stk);
		if(tag.hasKey("monsterSpawner")) {
			NBTTagCompound spawnerTag = tag.getCompoundTag("monsterSpawner");
			if(spawnerTag.hasKey("SpawnData")) {
				NBTTagCompound dataTag = spawnerTag.getCompoundTag("SpawnData");
				if(dataTag.hasKey("id")) {
					String id = dataTag.getString("id");
					id = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(id)).getName();
					lst.add(I18n.translateToLocal(id));
				}
			}
			else if(spawnerTag.hasKey("SpawnPotentials")) {
				NBTTagList nbttaglist = spawnerTag.getTagList("SpawnPotentials", 10);

				for(int i = 0; i < nbttaglist.tagCount(); ++i) {
					NBTTagCompound dataTag = nbttaglist.getCompoundTagAt(i);
					if(dataTag.hasKey("id")) {
						String id = dataTag.getString("id");
						id = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(id)).getName();
						int weight = dataTag.getInteger("Weight");
						lst.add(I18n.translateToLocal(id)+" ("+weight+")");
					}
				}
			}
		}
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/collectedspawner", "inventory"));
	}
}
