package essentialcraft.common.entity;

import java.util.List;

import DummyCore.Utils.MathUtils;
import essentialcraft.common.item.ItemsCore;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class EntityOrbitalStrike extends Entity {

	public static final DataParameter<String> DATA = EntityDataManager.<String>createKey(EntityOrbitalStrike.class, DataSerializers.STRING);

	public EntityOrbitalStrike(World w) {
		super(w);
		setSize(0.3F, 0.3F);
	}

	public EntityOrbitalStrike(World w, double x, double y, double z) {
		this(w);
		setPositionAndRotation(x, y, z, 0, 0);
	}

	public EntityOrbitalStrike(World w, double x, double y, double z, double damage, double delay, EntityLivingBase base) {
		this(w, x, y, z);
		this.damage = damage;
		this.delay = delay;
		attacker = base;
	}

	public EntityLivingBase attacker;
	public double delay = 3;
	public double damage = 1;

	@Override
	protected void entityInit() {
		getDataManager().register(DATA, "||null:null");
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		delay = tag.getDouble("delay");
		damage = tag.getDouble("damage");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		tag.setDouble("delay", delay);
		tag.setDouble("damage", damage);
	}

	@Override
	public void onUpdate() {
		delay -= 0.05D;
		if(!getEntityWorld().isRemote) {
			getDataManager().set(DATA, String.valueOf(delay));
		}
		if(ticksExisted == 3) {
			ECUtils.playSoundToAllNearby(posX, posY, posZ, "essentialcraft:sound.orbital_strike", 1, 1F, 16, dimension);
		}
		if(delay <= 0 && !isDead) {
			if(!getEntityWorld().isRemote) {
				List<EntityLivingBase> allEntities = getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(posX-0.5D, posY-0.5D, posZ-0.5D, posX+0.5D, posY+0.5D, posZ+0.5D).grow(2, 2, 2));
				for(EntityLivingBase elb : allEntities) {
					if((elb == null) || elb.isDead || (elb == attacker)) {
						continue;
					}
					elb.setFire(2);
					elb.attackEntityFrom(new DamageSource("orbitalStrike") {
						@Override
						public Entity getImmediateSource() {
							return attacker;
						}
					}.setDamageIsAbsolute(), (float)damage);
				}
				setDead();
			}

			for(int i = 0; i < 3; ++i) {
				getEntityWorld().playSound(posX, posY, posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1, rand.nextFloat()*2, false);
			}
			for(int i = 0; i < 20; ++i) {
				getEntityWorld().spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX+MathUtils.randomDouble(rand), posY+MathUtils.randomDouble(rand), posZ+MathUtils.randomDouble(rand), 0, 0, 0);
			}
			if(!getEntityWorld().isRemote && getEntityWorld().getGameRules().getBoolean("mobGriefing")) {
				for(int dx = -2; dx <= 2; ++dx) {
					int x = MathHelper.floor(posX) + dx;
					for(int dy = -2; dy <= 2; ++dy) {
						int y = MathHelper.floor(posY) + dy;
						for(int dz = -2; dz <= 2; ++dz) {
							int z = MathHelper.floor(posZ) + dz;
							IBlockState b = getEntityWorld().getBlockState(new BlockPos(x, y, z));
							if(!getEntityWorld().isAirBlock(new BlockPos(x, y, z))) {
								if(b.getMaterial() == Material.WATER || b.getMaterial() == Material.ICE || b.getMaterial() == Material.SNOW) {
									if(!getEntityWorld().isRemote) {
										getEntityWorld().setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), 3);
									}
									getEntityWorld().playSound(x + 0.5F, y + 0.5F, z + 0.5F, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (getEntityWorld().rand.nextFloat() - getEntityWorld().rand.nextFloat()) * 0.8F, false);
									for(int l = 0; l < 8; ++l) {
										getEntityWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, x + Math.random(), y + Math.random(), z + Math.random(), 0D, 0D, 0D);
									}
									continue;
								}
								ItemStack is = new ItemStack(b.getBlock(), 1, b.getBlock().getMetaFromState(b));
								ItemStack result = FurnaceRecipes.instance().getSmeltingResult(is);
								if(!result.isEmpty()) {
									if(result.getItem() instanceof ItemBlock) {
										Block setTo = ((ItemBlock)result.getItem()).getBlock();
										if(setTo != null && !getEntityWorld().isRemote) {
											getEntityWorld().setBlockState(new BlockPos(x, y, z), setTo.getStateFromMeta(result.getItemDamage()), 3);
										}
									}
									else {
										if(!getEntityWorld().isRemote) {
											getEntityWorld().setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), 3);
										}
										EntityItem itm = new EntityItem(getEntityWorld(), x, y, z, result.copy());
										if(!getEntityWorld().isRemote) {
											getEntityWorld().spawnEntity(itm);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if(getEntityWorld().isRemote) {
			try {
				delay = Double.parseDouble(getDataManager().get(DATA));
			}
			catch(Exception e) {

			}
		}
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ItemsCore.entityEgg, 1, EntitiesCore.REGISTERED_ENTITIES.indexOf(ForgeRegistries.ENTITIES.getValue(EntityList.getKey(this.getClass()))));
	}
}
