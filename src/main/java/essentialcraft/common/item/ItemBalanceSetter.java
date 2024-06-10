package essentialcraft.common.item;

import DummyCore.Client.IModelRegisterer;
import DummyCore.Utils.MiscUtils;
import essentialcraft.api.IMRUHandler;
import essentialcraft.common.capabilities.mru.CapabilityMRUHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemBalanceSetter extends Item implements IModelRegisterer {

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(!stack.hasTagCompound()) {
			MiscUtils.getStackTag(stack).setInteger("Mode", 0);
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stackIn = playerIn.getHeldItem(handIn);
		if(!playerIn.isSneaking()) {
			int currentMode = MiscUtils.getStackTag(stackIn).getInteger("Mode");
			currentMode = (currentMode+1)%3;
			if(!worldIn.isRemote) {
				playerIn.sendMessage(new TextComponentString("Set Mode To "+currentMode));
			}
			MiscUtils.getStackTag(stackIn).setInteger("Mode", currentMode);
		}
		return new ActionResult<>(EnumActionResult.PASS, stackIn);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(player.isSneaking()) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile != null) {
				if(tile.hasCapability(CapabilityMRUHandler.MRU_HANDLER_CAPABILITY, null)) {
					IMRUHandler handler = tile.getCapability(CapabilityMRUHandler.MRU_HANDLER_CAPABILITY, null);
					handler.setBalance(MiscUtils.getStackTag(player.getHeldItem(hand)).getInteger("Mode"));
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return EnumActionResult.PASS;
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("minecraft:blaze_rod", "inventory"));
	}
}
