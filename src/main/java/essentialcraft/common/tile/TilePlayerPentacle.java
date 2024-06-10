package essentialcraft.common.tile;

import java.util.function.BiPredicate;

import essentialcraft.api.IESPEHandler;
import essentialcraft.common.block.BlocksCore;
import essentialcraft.common.capabilities.espe.CapabilityESPEHandler;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TilePlayerPentacle extends TileEntity implements ITickable {
	public int tier = -1;
	public int sCheckTick = 0;
	protected static Vec3i[] coords = {
			new Vec3i(2, 0, 1), new Vec3i(2, 0, -1), new Vec3i(-2, 0, 1), new Vec3i(-2, 0, -1),
			new Vec3i(1, 0, 2), new Vec3i(-1, 0, 2), new Vec3i(1, 0, -2), new Vec3i(-1, 0, -2),
			new Vec3i(1, 1, 1), new Vec3i(-1, 1, 1), new Vec3i(1, 1, -1), new Vec3i(-1, 1, -1),
			new Vec3i(2, 2, 2), new Vec3i(-2, 2, 2), new Vec3i(2, 2, -2), new Vec3i(-2, 2, -2),
			new Vec3i(2, 3, 0), new Vec3i(-2, 3, 0), new Vec3i(0, 3, 2), new Vec3i(0, 3, -2)
	};

	public int getEnderstarEnergy() {
		int tierCheck = 0;
		if(tier == 0) {
			tierCheck = 8;
		}
		if(tier == 1) {
			tierCheck = 12;
		}
		if(tier == 2) {
			tierCheck = 16;
		}
		if(tier >= 3) {
			tierCheck = 20;
		}
		double energy = 0;
		double consumeModifier = 0.36D;
		for(int i = 0; i < tierCheck; ++i) {
			TileEntity tile = getWorld().getTileEntity(pos.add(coords[i]));
			if(tile == null) {
				continue;
			}
			if(tile.hasCapability(CapabilityESPEHandler.ESPE_HANDLER_CAPABILITY, null)) {
				IESPEHandler handler = tile.getCapability(CapabilityESPEHandler.ESPE_HANDLER_CAPABILITY, null);
				if(tier < 3) {
					energy += handler.getESPE();
				}
				else {
					energy += handler.getESPE()*2;
				}
			}
		}

		return MathHelper.floor(energy*consumeModifier);
	}

	public boolean consumeEnderstarEnergy(int consumed) {
		int tierCheck = 0;
		if(tier == 0) {
			tierCheck = 8;
		}
		if(tier == 1) {
			tierCheck = 12;
		}
		if(tier == 2) {
			tierCheck = 16;
		}
		if(tier >= 3) {
			tierCheck = 20;
		}
		double aconsumed = 0;
		double consumeModifier = 0.36D;
		consumed /= consumeModifier;
		for(int i = 0; i < tierCheck; ++i) {
			TileEntity tile = getWorld().getTileEntity(pos.add(coords[i]));
			if(tile == null) {
				continue;
			}
			if(tile.hasCapability(CapabilityESPEHandler.ESPE_HANDLER_CAPABILITY, null)) {
				IESPEHandler handler = tile.getCapability(CapabilityESPEHandler.ESPE_HANDLER_CAPABILITY, null);
				double req = (consumed - aconsumed) / (tier < 3 ? 1 : 2);
				aconsumed += handler.extractESPE(req, false) * (tier < 3 ? 1 : 2);
				if(aconsumed >= consumed) {
					break;
				}
			}
		}
		if(aconsumed < consumed) {
			return false;
		}

		aconsumed = 0;
		for(int i = 0; i < tierCheck; ++i) {
			TileEntity tile = getWorld().getTileEntity(pos.add(coords[i]));
			if(tile == null) {
				continue;
			}
			if(tile.hasCapability(CapabilityESPEHandler.ESPE_HANDLER_CAPABILITY, null)) {
				IESPEHandler handler = tile.getCapability(CapabilityESPEHandler.ESPE_HANDLER_CAPABILITY, null);
				double req = (consumed - aconsumed) / (tier < 3 ? 1 : 2);
				aconsumed += handler.extractESPE(req, true) * (tier < 3 ? 1 : 2);
				if(aconsumed >= consumed) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void update() {
		if(sCheckTick == 0) {
			checkStructureAndTier();
			sCheckTick = 40;
		}
		else {
			--sCheckTick;
		}

		if(getWorld().isRemote) {
			int movement = (int)(getWorld().getWorldTime() % 60);
			if(movement > 30) {
				movement = 60 - movement;
			}
			if(tier >= 0) {
				for(int i = 0; i < 8; ++i) {
					if(getWorld().getTileEntity(pos.add(coords[i])) instanceof TileMithrilineCrystal) {
						getWorld().spawnParticle(EnumParticleTypes.REDSTONE, pos.getX()+coords[i].getX()+0.5D, pos.getY()+coords[i].getY()+movement/30D, pos.getZ()+coords[i].getZ()+0.5D, -1, 1, 0);
						getWorld().spawnParticle(EnumParticleTypes.REDSTONE, pos.getX()+coords[i].getX()+0.5D, pos.getY()+coords[i].getY()+2+movement/30D, pos.getZ()+coords[i].getZ()+0.5D, -1, 1, 0);
					}
				}
			}
			if(tier >= 1) {
				for(int i = 8; i < 12; ++i) {
					if(getWorld().getTileEntity(pos.add(coords[i])) instanceof TileMithrilineCrystal) {
						getWorld().spawnParticle(EnumParticleTypes.REDSTONE, pos.getX()+coords[i].getX()+0.5D, pos.getY()+coords[i].getY()+movement/30D, pos.getZ()+coords[i].getZ()+0.5D, -1, 0, 1);
						getWorld().spawnParticle(EnumParticleTypes.REDSTONE, pos.getX()+coords[i].getX()+0.5D, pos.getY()+coords[i].getY()+2+movement/30D, pos.getZ()+coords[i].getZ()+0.5D, -1, 0, 1);
					}
				}
			}
			if(tier >= 2) {
				for(int i = 12; i < 16; ++i) {
					if(getWorld().getTileEntity(pos.add(coords[i])) instanceof TileMithrilineCrystal) {
						getWorld().spawnParticle(EnumParticleTypes.REDSTONE, pos.getX()+coords[i].getX()+0.5D, pos.getY()+coords[i].getY()+movement/30D, pos.getZ()+coords[i].getZ()+0.5D, 0.1D, 0, 0.1D);
						getWorld().spawnParticle(EnumParticleTypes.REDSTONE, pos.getX()+coords[i].getX()+0.5D, pos.getY()+coords[i].getY()+2+movement/30D, pos.getZ()+coords[i].getZ()+0.5D, 0.1D, 0, 0.1D);
					}
				}
			}
			if(tier >= 3) {
				for(int i = 16; i < 20; ++i) {
					if(getWorld().getTileEntity(pos.add(coords[i])) instanceof TileMithrilineCrystal) {
						getWorld().spawnParticle(EnumParticleTypes.REDSTONE, pos.getX()+coords[i].getX()+0.5D, pos.getY()+coords[i].getY()+movement/30D, pos.getZ()+coords[i].getZ()+0.5D, 1D, 0, 0D);
						getWorld().spawnParticle(EnumParticleTypes.REDSTONE, pos.getX()+coords[i].getX()+0.5D, pos.getY()+coords[i].getY()+2+movement/30D, pos.getZ()+coords[i].getZ()+0.5D, 1D, 0, 0D);
					}
				}
			}
		}
	}

	protected static boolean testBlock(IBlockAccess world, BlockPos pos, Block block) {
		return world.getBlockState(pos).getBlock() == block;
	}

	protected static boolean testESPEHandler(IBlockAccess world, BlockPos pos, int minTier) {
		TileEntity tile = world.getTileEntity(pos);
		return tile != null &&
				tile.hasCapability(CapabilityESPEHandler.ESPE_HANDLER_CAPABILITY, null) &&
				tile.getCapability(CapabilityESPEHandler.ESPE_HANDLER_CAPABILITY, null).getTier() >= minTier;
	}

	protected static BiPredicate<IBlockAccess, BlockPos> tier0Checker = (w, cp)->
	testBlock(w, cp.add( 0, 0, 0), BlocksCore.invertedBlock) &&
	testBlock(w, cp.add( 1, 0, 0), BlocksCore.invertedBlock) &&
	testBlock(w, cp.add(-1, 0, 0), BlocksCore.invertedBlock) &&
	testBlock(w, cp.add( 0, 0, 1), BlocksCore.invertedBlock) &&
	testBlock(w, cp.add( 0, 0,-1), BlocksCore.invertedBlock) &&
	testBlock(w, cp.add( 2, 0,-1), BlocksCore.invertedBlock) &&
	testBlock(w, cp.add( 2, 0, 1), BlocksCore.invertedBlock) &&
	testBlock(w, cp.add(-2, 0,-1), BlocksCore.invertedBlock) &&
	testBlock(w, cp.add(-2, 0, 1), BlocksCore.invertedBlock) &&
	testBlock(w, cp.add(-1, 0, 2), BlocksCore.invertedBlock) &&
	testBlock(w, cp.add( 1, 0, 2), BlocksCore.invertedBlock) &&
	testBlock(w, cp.add(-1, 0,-2), BlocksCore.invertedBlock) &&
	testBlock(w, cp.add( 1, 0,-2), BlocksCore.invertedBlock) &&
	testESPEHandler(w, cp.add(-1, 1, 2), 0) &&
	testESPEHandler(w, cp.add( 1, 1, 2), 0) &&
	testESPEHandler(w, cp.add(-1, 1,-2), 0) &&
	testESPEHandler(w, cp.add( 1, 1,-2), 0) &&
	testESPEHandler(w, cp.add( 2, 1,-1), 0) &&
	testESPEHandler(w, cp.add( 2, 1, 1), 0) &&
	testESPEHandler(w, cp.add(-2, 1,-1), 0) &&
	testESPEHandler(w, cp.add(-2, 1, 1), 0);

	protected static BiPredicate<IBlockAccess, BlockPos> tier1Checker = (w, cp)->
	testBlock(w, cp.add( 1, 1, 1), BlocksCore.blockPale) &&
	testBlock(w, cp.add(-1, 1, 1), BlocksCore.blockPale) &&
	testBlock(w, cp.add( 1, 1,-1), BlocksCore.blockPale) &&
	testBlock(w, cp.add(-1, 1,-1), BlocksCore.blockPale) &&
	testESPEHandler(w, cp.add( 1, 2, 1), 1) &&
	testESPEHandler(w, cp.add(-1, 2, 1), 1) &&
	testESPEHandler(w, cp.add( 1, 2,-1), 1) &&
	testESPEHandler(w, cp.add(-1, 2,-1), 1);

	protected static BiPredicate<IBlockAccess, BlockPos> tier2Checker = (w, cp)->
	testBlock(w, cp.add( 2, 1, 2), BlocksCore.voidStone) &&
	testBlock(w, cp.add(-2, 1, 2), BlocksCore.voidStone) &&
	testBlock(w, cp.add( 2, 1,-2), BlocksCore.voidStone) &&
	testBlock(w, cp.add(-2, 1,-2), BlocksCore.voidStone) &&
	testBlock(w, cp.add( 2, 2, 2), BlocksCore.voidStone) &&
	testBlock(w, cp.add(-2, 2, 2), BlocksCore.voidStone) &&
	testBlock(w, cp.add( 2, 2,-2), BlocksCore.voidStone) &&
	testBlock(w, cp.add(-2, 2,-2), BlocksCore.voidStone) &&
	testESPEHandler(w, cp.add( 2, 3, 2), 2) &&
	testESPEHandler(w, cp.add(-2, 3, 2), 2) &&
	testESPEHandler(w, cp.add( 2, 3,-2), 2) &&
	testESPEHandler(w, cp.add(-2, 3,-2), 2);

	protected static BiPredicate<IBlockAccess, BlockPos> tier3Checker = (w, cp)->
	testBlock(w, cp.add( 2, 0, 0), BlocksCore.demonicPlating) &&
	testBlock(w, cp.add(-2, 0, 0), BlocksCore.demonicPlating) &&
	testBlock(w, cp.add( 0, 0, 2), BlocksCore.demonicPlating) &&
	testBlock(w, cp.add( 0, 0,-2), BlocksCore.demonicPlating) &&
	testESPEHandler(w, cp.add( 2, 4, 0), 3) &&
	testESPEHandler(w, cp.add(-2, 4, 0), 3) &&
	testESPEHandler(w, cp.add( 0, 4, 2), 3) &&
	testESPEHandler(w, cp.add( 0, 4,-2), 3);

	public void checkStructureAndTier() {
		World w = getWorld();
		BlockPos cp = pos.down();
		tier = -1;
		if(!tier0Checker.test(w, cp)) {
			return;
		}
		tier = 0;
		if(!tier1Checker.test(w, cp)) {
			return;
		}
		tier = 1;
		if(!tier2Checker.test(w, cp)) {
			return;
		}
		tier = 2;
		if(!tier3Checker.test(w, cp)) {
			return;
		}
		tier = 3;
	}
}
