package essentialcraft.common.entity;

import essentialcraft.common.item.ItemsCore;
import essentialcraft.common.registry.LootTableRegistry;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

//TODO split this
public class EntityWindMage extends EntityMob implements IRangedAttackMob {

	public static final DataParameter<Byte> TYPE = EntityDataManager.<Byte>createKey(EntityWindMage.class, DataSerializers.BYTE);
	private EntityAIAttackRanged aiArrowAttack = new EntityAIAttackRanged(this, 1D, 20, 60, 15F);
	private EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, false);

	public EntityWindMage(World world) {
		super(world);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(5, new EntityAIWander(this, 1D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8F));
		tasks.addTask(6, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, false, null));

		if(world != null && !world.isRemote) {
			setCombatTask();
		}
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		getDataManager().register(TYPE, new Byte((byte)0));
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource s) {
		return SoundEvents.ENTITY_VILLAGER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_VILLAGER_DEATH;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEFINED;
	}

	@Override
	public void updateRidden() {
		super.updateRidden();

		if(getRidingEntity() instanceof EntityCreature) {
			EntityCreature entitycreature = (EntityCreature)getRidingEntity();
			renderYawOffset = entitycreature.renderYawOffset;
		}
	}

	@Override
	protected ResourceLocation getLootTable() {
		switch(getType()) {
		case 0: return LootTableRegistry.ENTITY_WINDMAGE_APPRENTICE;
		case 1: return LootTableRegistry.ENTITY_WINDMAGE_NORMAL;
		case 2: return LootTableRegistry.ENTITY_WINDMAGE_ARCHMAGE;
		default: return null;
		}
	}


	public void setCombatTask() {
		tasks.removeTask(aiAttackOnCollide);
		tasks.removeTask(aiArrowAttack);
		tasks.addTask(4, aiArrowAttack);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		EntityMRUArrow entityarrow = new EntityMRUArrow(getEntityWorld(), this, 1.6F);
		double d0 = target.posX - posX;
		double d1 = target.getEntityBoundingBox().minY + target.height / 3F - entityarrow.posY;
		double d2 = target.posZ - posZ;
		double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
		entityarrow.shoot(d0, d1 + d3 * 0.2D, d2, 1.6F, 14 - getEntityWorld().getDifficulty().getId() * 4);
		entityarrow.setDamage((getType()+1)*3);
		getEntityWorld().spawnEntity(entityarrow);
	}

	public int getType() {
		return getDataManager().get(TYPE);
	}

	public void setType(int type) {
		getDataManager().set(TYPE, Byte.valueOf((byte)type));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);

		if(nbt.hasKey("Type")) {
			byte b0 = nbt.getByte("Type");
			setType(b0);
		}

		setCombatTask();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("Type", (byte)getType());
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingData) {
		livingData = super.onInitialSpawn(difficulty, livingData);
		setType(getEntityWorld().rand.nextInt(3));
		return livingData;
	}

	@Override
	public double getYOffset() {
		return super.getYOffset() - 0.5D;
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ItemsCore.entityEgg, 1, EntitiesCore.REGISTERED_ENTITIES.indexOf(ForgeRegistries.ENTITIES.getValue(EntityList.getKey(this.getClass()))));
	}

	@Override
	public void setSwingingArms(boolean swingingArms) {}
}