package essentialcraft.common.item;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import DummyCore.Client.IModelRegisterer;
import essentialcraft.api.IMRUHandlerItem;
import essentialcraft.common.capabilities.mru.CapabilityMRUHandler;
import essentialcraft.common.capabilities.mru.MRUItemStorage;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFrostMace extends ItemSword implements IModelRegisterer {

	public ItemFrostMace() {
		super(ItemsCore.elemental);
		maxStackSize = 1;
		bFull3D = true;
		setMaxDamage(0);
	}

	public static Capability<IMRUHandlerItem> MRU_HANDLER_ITEM_CAPABILITY = CapabilityMRUHandler.MRU_HANDLER_ITEM_CAPABILITY;
	int maxMRU = 5000;

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag par4) {
		super.addInformation(stack, player, list, par4);
		list.add(stack.getCapability(MRU_HANDLER_ITEM_CAPABILITY, null).getMRU() + "/" + stack.getCapability(MRU_HANDLER_ITEM_CAPABILITY, null).getMaxMRU() + " MRU");
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(isInCreativeTab(tab)) {
			ItemStack min = new ItemStack(this, 1, 0);
			ItemStack max = new ItemStack(this, 1, 0);
			min.getCapability(MRU_HANDLER_ITEM_CAPABILITY, null).setMRU(0);
			max.getCapability(MRU_HANDLER_ITEM_CAPABILITY, null).setMRU(maxMRU);
			items.add(min);
			items.add(max);
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
		Vec3d playerLookVec = entityLiving.getLookVec();
		entityLiving.motionX += playerLookVec.x;
		entityLiving.motionY += playerLookVec.y;
		entityLiving.motionZ += playerLookVec.z;
		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		try {
			if(attacker instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) attacker;
				if(ECUtils.playerUseMRU(player, stack, 250)) {
					int att = ECUtils.getData(player).getMatrixTypeID();
					if(att == 2) {
						PotionEffect eff = target.getActivePotionEffect(MobEffects.SLOWNESS);
						if(eff != null && target.hurtResistantTime == 0 || target.hurtResistantTime >= 15 && eff != null) {
							target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1000, eff.getAmplifier()+1));
							return true;
						}
						if(target.hurtResistantTime == 0 || target.hurtResistantTime >= 15) {
							target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1000, 0));
							return true;
						}
					}
				}
			}
		}
		catch(Exception e) {
			return false;
		}
		return false;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot s, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
		if(s == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 16, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4D, 0));
		}
		return multimap;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new MRUItemStorage(stack, maxMRU);
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("essentialcraft:item/frozenmace", "inventory"));
	}
}
