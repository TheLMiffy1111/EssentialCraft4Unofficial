package essentialcraft.common.entity;

import java.util.List;

import essentialcraft.common.item.ItemsCore;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class EntitySolarBeam extends EntityWeatherEffect {

	public int beamLiveTime = 20;

	public EntitySolarBeam(World world) {
		super(world);
		ignoreFrustumCheck = true;
		setSize(0.3F, 0.3F);
	}

	public EntitySolarBeam(World world, double x, double y, double z) {
		super(world);
		setLocationAndAngles(x, y, z, 0F, 0F);
		beamLiveTime = 20;
		if(!world.isRemote && world.getGameRules().getBoolean("doFireTick") && world.isAreaLoaded(new BlockPos(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)), 10)) {
			int i = MathHelper.floor(x);
			int j = MathHelper.floor(y);
			int k = MathHelper.floor(z);

			if(world.getBlockState(new BlockPos(i, j, k)).getMaterial() == Material.AIR && Blocks.FIRE.canPlaceBlockAt(world, new BlockPos(i, j, k))) {
				world.setBlockState(new BlockPos(i, j, k), Blocks.FIRE.getDefaultState());
			}

			for(i = 0; i < 32; ++i) {
				j = MathHelper.floor(x) + rand.nextInt(13) - 1;
				k = MathHelper.floor(y) + rand.nextInt(13) - 1;
				int l = MathHelper.floor(z) + rand.nextInt(13) - 1;

				if(world.getBlockState(new BlockPos(j, k, l)).getMaterial() == Material.AIR && Blocks.FIRE.canPlaceBlockAt(world, new BlockPos(j, k, l))) {
					world.setBlockState(new BlockPos(j, k, l), Blocks.FIRE.getDefaultState());
				}
			}
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if(--beamLiveTime <= 0) {
			setDead();
		}
		if(beamLiveTime%5 == 0) {
			getEntityWorld().playSound(null, posX, posY, posZ, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.WEATHER, 10F, 2F);
		}
		double d0 = 6D;
		List<?> list = getEntityWorld().getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(posX - d0, posY - d0, posZ - d0, posX + d0, posY + 128D + d0, posZ + d0));

		for(Object element : list) {
			Entity entity = (Entity)element;
			entity.setFire(5);
			entity.attackEntityFrom(DamageSource.ON_FIRE, 3F);
		}
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ItemsCore.entityEgg, 1, EntitiesCore.REGISTERED_ENTITIES.indexOf(ForgeRegistries.ENTITIES.getValue(EntityList.getKey(this.getClass()))));
	}
}
