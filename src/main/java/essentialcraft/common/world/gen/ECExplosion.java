package essentialcraft.common.world.gen;

import java.util.HashSet;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ECExplosion extends Explosion {

	private World world;
	private float explosionSize;
	private double explosionX;
	private double explosionY;
	private double explosionZ;
	private Entity exploder;

	public ECExplosion(World world, Entity exploder, double x, double y, double z, float size) {
		super(world, exploder, x, y, z, size, false, true);
		this.world = world;
		this.exploder = exploder;
		explosionX = x;
		explosionY = y;
		explosionZ = z;
		explosionSize = size;
	}

	@Override
	public void doExplosionA() {
		float f = explosionSize;
		HashSet<BlockPos> hashset = new HashSet<>();
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;

		for(i = 0; i < 16; ++i) {
			for(j = 0; j < 16; ++j) {
				for(k = 0; k < 16; ++k) {
					if(i == 0 || i == 15 || j == 0 || j == 15 || k == 0 || k == 15) {
						double d0 = i / (15F) * 2F - 1F;
						double d1 = j / (15F) * 2F - 1F;
						double d2 = k / (15F) * 2F - 1F;
						double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
						d0 /= d3;
						d1 /= d3;
						d2 /= d3;
						float f1 = explosionSize * (0.7F + world.rand.nextFloat() * 0.6F);
						d5 = explosionX;
						d6 = explosionY;
						d7 = explosionZ;

						for(float f2 = 0.3F; f1 > 0F; f1 -= f2 * 0.75F) {
							int j1 = MathHelper.floor(d5);
							int k1 = MathHelper.floor(d6);
							int l1 = MathHelper.floor(d7);
							BlockPos pos = new BlockPos(j1, k1, l1);
							IBlockState block = world.getBlockState(pos);

							if(block.getMaterial() != Material.AIR) {
								float f3 = exploder != null ? exploder.getExplosionResistance(this, world, pos, block) : block.getBlock().getExplosionResistance(world, pos, exploder, this);
								f1 -= (f3 + 0.3F) * f2;
							}

							if(f1 > 0F && (exploder == null || exploder.canExplosionDestroyBlock(this, world, pos, block, f1))) {
								hashset.add(new BlockPos(j1, k1, l1));
							}

							d5 += d0 * f2;
							d6 += d1 * f2;
							d7 += d2 * f2;
						}
					}
				}
			}
		}

		getAffectedBlockPositions().addAll(hashset);
		explosionSize *= 2F;
		i = MathHelper.floor(explosionX - explosionSize - 1D);
		j = MathHelper.floor(explosionX + explosionSize + 1D);
		k = MathHelper.floor(explosionY - explosionSize - 1D);
		explosionSize = f;
	}

	@Override
	public void doExplosionB(boolean p_77279_1_) {
		IBlockState block;

		for(BlockPos chunkposition : getAffectedBlockPositions()) {
			chunkposition.getX();
			chunkposition.getY();
			chunkposition.getZ();
			block = world.getBlockState(chunkposition);

			if(block.getMaterial() != Material.AIR) {
				block.getBlock().onBlockExploded(world, chunkposition, this);
			}
		}
	}
}