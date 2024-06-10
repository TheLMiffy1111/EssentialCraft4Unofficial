package essentialcraft.common.entity;

import java.util.List;

import DummyCore.Utils.MathUtils;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import essentialcraft.common.item.ItemBaublesSpecial;
import essentialcraft.common.item.ItemsCore;
import essentialcraft.common.mod.EssentialCraftCore;
import essentialcraft.utils.cfg.Config;
import essentialcraft.utils.common.ECUtils;
import essentialcraft.utils.common.RadiationManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class EntityPoisonFume extends EntityMob {

	private float heightOffset = 0.5F;
	private int heightOffsetUpdateTime;
	public double mX, mY, mZ;

	public EntityPoisonFume(World world) {
		super(world);
		isImmuneToFire = true;
		setSize(0.6F, 0.6F);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float amount) {
		return false;
	}

	@Override
	public int getBrightnessForRender() {
		return 15728880;
	}

	@Override
	public float getBrightness() {
		return 1F;
	}

	@Override
	public void onLivingUpdate() {
		if(!(dimension == Config.dimensionID && ECUtils.isEventActive("essentialcraft.event.fumes"))) {
			setDead();
		}

		if(!getEntityWorld().isRemote) {
			--heightOffsetUpdateTime;

			if(heightOffsetUpdateTime <= 0) {
				heightOffsetUpdateTime = 100;
				mX = MathUtils.randomDouble(getEntityWorld().rand);
				mY = MathUtils.randomDouble(getEntityWorld().rand);
				mZ = MathUtils.randomDouble(getEntityWorld().rand);
				setHeightOffset(0.5F + (float)rand.nextGaussian() * 3F);
			}
			motionX = mX/10;
			motionY = mY/10;
			motionZ = mZ/10;
			if(ticksExisted > 1000) {
				setDead();
			}
		}
		EssentialCraftCore.proxy.spawnParticle("fogFX", (float)posX, (float)posY+2, (float)posZ, 0F, 1F, 0F);
		List<EntityPlayer> players = getEntityWorld().<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(posX-1, posY-1, posZ-1, posX+1, posY+1, posZ+1).grow(6, 3, 6));
		for(EntityPlayer p : players) {
			boolean ignorePoison = false;
			IBaublesItemHandler b = BaublesApi.getBaublesHandler(p);
			if(b != null) {
				for(int i1 = 0; i1 < b.getSlots(); ++i1) {
					ItemStack is = b.getStackInSlot(i1);
					if(is.getItem() instanceof ItemBaublesSpecial && is.getItemDamage() == 19 || p.capabilities.isCreativeMode) {
						ignorePoison = true;
					}
				}
			}
			if(!p.getEntityWorld().isRemote && !ignorePoison) {
				RadiationManager.increasePlayerRadiation(p, 10);
				p.addPotionEffect(new PotionEffect(MobEffects.POISON, 200, 1));
			}
		}
		super.onLivingUpdate();
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		return false;
	}

	@Override
	public void fall(float distance, float s) {}

	@Override
	public boolean isBurning() {
		return false;
	}

	@Override
	protected boolean isValidLightLevel() {
		return true;
	}

	@Override
	public boolean getCanSpawnHere() {
		return dimension == Config.dimensionID && ECUtils.isEventActive("essentialcraft.event.fumes");
	}

	public float getHeightOffset() {
		return heightOffset;
	}

	public void setHeightOffset(float heightOffset) {
		this.heightOffset = heightOffset;
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ItemsCore.entityEgg, 1, EntitiesCore.REGISTERED_ENTITIES.indexOf(ForgeRegistries.ENTITIES.getValue(EntityList.getKey(this.getClass()))));
	}
}