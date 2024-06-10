package essentialcraft.common.entity;

import java.util.Collections;
import java.util.List;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import essentialcraft.api.DemonTrade;
import essentialcraft.common.block.BlockDemonicPentacle;
import essentialcraft.common.item.ItemsCore;
import essentialcraft.common.mod.EssentialCraftCore;
import essentialcraft.common.registry.SoundRegistry;
import essentialcraft.common.tile.TileDemonicPentacle;
import essentialcraft.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

public class EntityDemon extends EntityLiving implements IInventory {

	public ItemStack inventory = ItemStack.EMPTY;
	public ItemStack desiredItem = ItemStack.EMPTY;
	public static final DataParameter<ItemStack> DESIRED = EntityDataManager.<ItemStack>createKey(EntityDemon.class, DataSerializers.ITEM_STACK);

	@Override
	protected boolean canDespawn() {
		return false;
	}

	public EntityDemon(World w) {
		super(w);
		DemonTrade trade = MathUtils.randomElement(DemonTrade.TRADES, w.rand);
		if(trade.entityType != null) {
			ItemStack stack = new ItemStack(ItemsCore.soul, w.rand.nextInt(7)+1, 0);
			MiscUtils.getStackTag(stack).setString("entity", trade.entityType.getRegistryName().toString());
		}
		else {
			desiredItem = trade.desiredItem;
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return getEntityWorld().rand.nextBoolean() ? SoundRegistry.entityDemonSay : SoundRegistry.entityDemonSummon;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource s) {
		return SoundRegistry.entityDemonDepart;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float amount) {
		playSound(getHurtSound(damageSource), 1, 1);
		setDead();
		for(int i = 0; i < 400; ++i) {
			double d2 = rand.nextGaussian() * 0.02D;
			double d0 = rand.nextGaussian() * 0.02D;
			double d1 = rand.nextGaussian() * 0.02D;
			getEntityWorld().spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, posX + rand.nextFloat() * width * 2.0F - width, posY + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, d2, d0, d1);
		}
		return false;
	}

	@Override
	public void onUpdate() {
		if(!getEntityWorld().isRemote) {
			getDataManager().set(DESIRED,desiredItem);
		}

		super.onUpdate();

		if(!getStackInSlot(0).isEmpty() && !desiredItem.isEmpty()) {
			if(desiredItem.getItemDamage() != OreDictionary.WILDCARD_VALUE && getStackInSlot(0).isItemEqual(desiredItem) && ItemStack.areItemStackTagsEqual(getStackInSlot(0), desiredItem) && getStackInSlot(0).getCount() >= desiredItem.getCount() || desiredItem.getItemDamage() == OreDictionary.WILDCARD_VALUE && getStackInSlot(0).getItem() == desiredItem.getItem() && getStackInSlot(0).getCount() >= desiredItem.getCount()) {
				setDead();
				for(int i = 0; i < 400; ++i) {
					double d2 = rand.nextGaussian() * 0.02D;
					double d0 = rand.nextGaussian() * 0.02D;
					double d1 = rand.nextGaussian() * 0.02D;
					getEntityWorld().spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, posX + rand.nextFloat() * width * 2.0F - width, posY + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, d2, d0, d1);
				}
				getEntityWorld().playSound(posX,posY,posZ,SoundRegistry.entityDemonDoom,SoundCategory.HOSTILE, getSoundVolume(), getSoundPitch(),false);
				ItemStack result = new ItemStack(ItemsCore.genericItem,3+getEntityWorld().rand.nextInt(6),52);
				EntityItem itm = new EntityItem(getEntityWorld(),posX,posY,posZ,result);
				if(!getEntityWorld().isRemote) {
					getEntityWorld().spawnEntity(itm);
				}
			}
		}

		if(getEntityWorld().isRaining() && getEntityWorld().canBlockSeeSky(new BlockPos(MathHelper.floor(posX), MathHelper.floor(posY+1), MathHelper.floor(posZ)))) {
			for(int i = 0; i < 20; ++i) {
				getEntityWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX+MathUtils.randomDouble(getRNG()), posY+1.3D+MathUtils.randomDouble(getRNG())*2, posZ+MathUtils.randomDouble(getRNG()), 0, 0.1D, 0);
			}
		}
		if(ticksExisted % 40 == 0) {
			for(int dx = -1; dx <= 1; ++dx) {
				for(int dz = -1; dz <= 1; ++dz) {
					Block b = getEntityWorld().getBlockState(new BlockPos(MathHelper.floor(posX)+dx, MathHelper.floor(posY), MathHelper.floor(posZ)+dz)).getBlock();
					if(b instanceof BlockDemonicPentacle) {
						TileDemonicPentacle tile = (TileDemonicPentacle) getEntityWorld().getTileEntity(new BlockPos(MathHelper.floor(posX)+dx, MathHelper.floor(posY), MathHelper.floor(posZ)+dz));
						if(tile.tier >= 0) {
							return;
						}
					}
				}
			}
			attackEntityFrom(DamageSource.OUT_OF_WORLD, 1);
		}
		if(ticksExisted % 20 == 0) {
			List<EntityMob> zombies = getEntityWorld().getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(posX-0.5D, posY-0.5D, posZ-0.5D, posX+0.5D, posY+0.5D, posZ+0.5D).grow(12, 12, 12));
			if(!zombies.isEmpty()) {
				EntityMob z = zombies.get(getRNG().nextInt(zombies.size()));
				if(z.isEntityAlive()) {
					swingArm(EnumHand.MAIN_HAND);
					z.attackEntityFrom(DamageSource.causeMobDamage(this), z.getMaxHealth()*1.6F);
					getEntityWorld().createExplosion(this, z.posX, z.posY, z.posZ, 2, false);
				}
			}
		}
		if(getEntityWorld().isRemote)
		 {
			desiredItem = getDataManager().get(DESIRED);
		//EssentialCraftCore.proxy.SmokeFX(posX,posY+1.5D+MathUtils.randomDouble(getRNG()),posZ,MathUtils.randomDouble(getRNG())/18,-0.09D+MathUtils.randomDouble(getRNG())/18,MathUtils.randomDouble(getRNG())/18,3,1,0.6D-this.getEntityWorld().rand.nextDouble()/3D,0.2D);
		}
	}


	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(DESIRED, new ItemStack(Items.APPLE,1,0));
	}

	@Override
	public Iterable<ItemStack> getHeldEquipment()
	{
		return Collections.emptySet();
	}

	@Override
	public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn)
	{
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack p_70062_2_) {}

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory;
	}

	@Override
	public ItemStack decrStackSize(int slot, int i) {
		inventory.shrink(i);
		if(inventory.getCount() <= 0) {
			setInventorySlotContents(0, ItemStack.EMPTY);
		}
		return inventory;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stk) {
		inventory = stk;
	}

	@Override
	public String getName() {
		return "demon";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void markDirty() {}

	@Override
	public boolean isUsableByPlayer(EntityPlayer p) {
		return !isDead && p.dimension == dimension && getPositionVector().squareDistanceTo(p.posX, p.posY, p.posZ) <= 64D;
	}

	@Override
	public void openInventory(EntityPlayer p) {}

	@Override
	public void closeInventory(EntityPlayer p) {}

	@Override
	public boolean processInteract(EntityPlayer p, EnumHand hand) {
		playSound(SoundRegistry.entityDemonTrade, getSoundVolume(), getSoundPitch());
		p.openGui(EssentialCraftCore.core, Config.guiID[1], getEntityWorld(), MathHelper.floor(posX), MathHelper.floor(posY), MathHelper.floor(posZ));
		return true;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		super.writeEntityToNBT(tag);
		if(!desiredItem.isEmpty()) {
			NBTTagCompound itemTag = new NBTTagCompound();
			desiredItem.writeToNBT(itemTag);
			tag.setTag("desired", itemTag);
		}
		else {
			tag.removeTag("desired");
		}
		if(!inventory.isEmpty()) {
			NBTTagCompound itemTag = new NBTTagCompound();
			inventory.writeToNBT(itemTag);
			tag.setTag("inventory", itemTag);
		}
		else {
			tag.removeTag("inventory");
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		super.readEntityFromNBT(tag);
		if(tag.hasKey("desired")) {
			desiredItem = new ItemStack(tag.getCompoundTag("desired"));
		}
		if(tag.hasKey("inventory")) {
			inventory = new ItemStack(tag.getCompoundTag("inventory"));
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stk) {
		return true;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stk = inventory;
		inventory = ItemStack.EMPTY;
		return stk;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		inventory = ItemStack.EMPTY;
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ItemsCore.entityEgg,1,EntitiesCore.REGISTERED_ENTITIES.indexOf(ForgeRegistries.ENTITIES.getValue(EntityList.getKey(this.getClass()))));
	}

	@Override
	public boolean isEmpty() {
		return inventory.isEmpty();
	}
}
