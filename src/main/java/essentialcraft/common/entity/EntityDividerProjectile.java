package essentialcraft.common.entity;

import essentialcraft.common.item.ItemsCore;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class EntityDividerProjectile extends EntityThrowable {

	public EntityDividerProjectile(World w) {
		super(w);
	}

	public EntityDividerProjectile(World world, EntityLivingBase thower) {
		super(world, thower);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if(result.typeOfHit == Type.BLOCK) {
			EntityDivider div = new EntityDivider(getEntityWorld(), posX, posY, posZ, 0, 2, getThrower());
			if(!getEntityWorld().isRemote) {
				getEntityWorld().spawnEntity(div);
			}

			setDead();
		}
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(ItemsCore.entityEgg, 1, EntitiesCore.REGISTERED_ENTITIES.indexOf(ForgeRegistries.ENTITIES.getValue(EntityList.getKey(this.getClass()))));
	}
}
