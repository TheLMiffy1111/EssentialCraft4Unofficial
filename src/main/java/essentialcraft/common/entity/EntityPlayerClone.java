package essentialcraft.common.entity;

import java.util.UUID;

import com.google.common.base.Optional;

import essentialcraft.common.item.ItemsCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIZombieAttack;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class EntityPlayerClone extends EntityZombie {

	public EntityPlayer playerToAttack = null;
	protected UUID clonedPlayer;
	private boolean firstTick = true;
	public static final DataParameter<Optional<UUID>> CLONED = EntityDataManager.<Optional<UUID>>createKey(EntityPlayerClone.class, DataSerializers.OPTIONAL_UNIQUE_ID);

	public EntityPlayerClone(World w) {
		super(w);
		inventoryHandsDropChances[0] = 0;
		inventoryHandsDropChances[1] = 0;
		inventoryArmorDropChances[0] = 0;
		inventoryArmorDropChances[1] = 0;
		inventoryArmorDropChances[2] = 0;
		inventoryArmorDropChances[3] = 0;
	}

	public Entity findPlayerToAttack() {
		playerToAttack = getEntityWorld().getNearestAttackablePlayer(this, 16, 16);
		return playerToAttack;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2D);
	}

	@Override
	protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {}

	@Override
	public void onUpdate() {
		if(!isPotionActive(MobEffects.SPEED)) {
			addPotionEffect(new PotionEffect(MobEffects.SPEED, 200, 3, true, true));
		}

		if(deathTime > 0) {
			setDead();
		}

		super.onUpdate();
		if(ticksExisted % 200 == 0) {
			setDead();
		}

		firstTick = false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource s) {
		return null;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return null;
	}

	@Override
	protected ResourceLocation getLootTable() {
		return null;
	}

	public UUID getClonedPlayer() {
		return dataManager.get(CLONED).orNull();
	}

	public void setClonedPlayer(UUID clonedPlayer) {
		if(!isDead) {
			dataManager.set(CLONED, Optional.<UUID>fromNullable(clonedPlayer));
		}
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(CLONED, Optional.<UUID>absent());
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		if(clonedPlayer != null) {
			compound.setUniqueId("cloned", clonedPlayer);
		}
		else {
			compound.removeTag("cloned");
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		clonedPlayer = compound.getUniqueId("cloned");
	}

	public void updateClonedPlayer() {
		if(!getEntityWorld().isRemote) {
			setClonedPlayer(getClonedPlayer());
		}
		if(getEntityWorld().isRemote && !firstTick) {
			clonedPlayer = getClonedPlayer();
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		extinguish();
	}

	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIZombieAttack(this, 1D, false));
		tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1D));
		tasks.addTask(7, new EntityAIWander(this, 1D));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8F));
		tasks.addTask(8, new EntityAILookIdle(this));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ItemsCore.entityEgg, 1, EntitiesCore.REGISTERED_ENTITIES.indexOf(ForgeRegistries.ENTITIES.getValue(EntityList.getKey(this.getClass()))));
	}
}
