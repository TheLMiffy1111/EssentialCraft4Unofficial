package essentialcraft.common.item;

import java.util.List;

import DummyCore.Client.IModelRegisterer;
import DummyCore.Utils.MiscUtils;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMRUMatrixProjection extends Item implements IModelRegisterer {

	public static String[] names = {"empty", "chaos", "frozen", "magic", "shade"};

	public ItemMRUMatrixProjection() {
		super();
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		return super.getTranslationKey(stack)+"_"+names[Math.min(stack.getItemDamage(), names.length-1)];
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(stack.getTagCompound() != null) {
			String username = stack.getTagCompound().getString("playerName");
			if(username.equals(MiscUtils.getUUIDFromPlayer(player).toString())) {
				player.setActiveHand(hand);
			}
		}
		if(stack.getTagCompound() == null && !world.isRemote && !player.isSneaking()) {
			NBTTagCompound playerTag = new NBTTagCompound();
			playerTag.setString("playerName", MiscUtils.getUUIDFromPlayer(player).toString());
			stack.setTagCompound(playerTag);
		}
		return super.onItemRightClick(world, player, hand);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flag) {
		if(stack.getTagCompound() != null) {
			String username = MiscUtils.getUsernameFromUUID(stack.getTagCompound().getString("playerName"));
			list.add(TextFormatting.DARK_GRAY+"Thus is a projection of "+TextFormatting.GOLD+username+TextFormatting.DARK_GRAY+"'s MRU Matrix");
		}
	}

	@Override
	public void getSubItems(CreativeTabs p_150895_2_, NonNullList<ItemStack> p_150895_3_) {
		if(isInCreativeTab(p_150895_2_)) {
			for(int i = 0; i < 5; ++i) {
				p_150895_3_.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BLOCK;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 200;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		if(count % 40 == 0) {
			player.playSound(SoundEvents.BLOCK_PORTAL_TRAVEL, 0.3F, 2);
			player.playSound(SoundEvents.BLOCK_PORTAL_TRIGGER, 0.3F, 2);
		}
		if(count == 100) {
			player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0, true, true));
		}
		if(count <= 50) {
			player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 2000, 0, true, true));
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity) {
		if(entity instanceof EntityPlayer) {
			entity.playSound(SoundEvents.BLOCK_PORTAL_TRIGGER, 0.3F, 0.01F);
			entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 760, 0, true, true));
			entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 760, 4, true, true));
			entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 760, 4, true, true));
			entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 760, 4, true, true));
			entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 760, 0, true, true));
			if(!entity.getEntityWorld().isRemote) {
				entity.sendMessage(new TextComponentString("Your MRU Matrix twists with new colors!").setStyle(new Style().setColor(TextFormatting.AQUA)));
				ECUtils.getData((EntityPlayer)entity).modifyMatrixType(stack.getItemDamage());
			}
			stack.shrink(1);
		}
		return stack;
	}

	@Override
	public void registerModels() {
		for(int i = 0; i < names.length; i++) {
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation("essentialcraft:item/mrumatrixprojection", "type=" + names[i]));
		}
	}
}
