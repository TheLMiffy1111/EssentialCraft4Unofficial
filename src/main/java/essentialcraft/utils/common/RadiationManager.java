package essentialcraft.utils.common;

import java.util.Random;

import essentialcraft.common.registry.BiomeRegistry;
import essentialcraft.common.registry.PotionRegistry;
import essentialcraft.utils.cfg.Config;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.util.FakePlayer;

public class RadiationManager {

	public static void increasePlayerRadiation(EntityPlayer e, int amount) {
		int current = getPlayerRadiation(e);
		setPlayerRadiation(e, current+amount);
	}

	public static void setPlayerRadiation(EntityPlayer player, int amount) {
		if(amount < 0) {
			amount = 0;
		}
		if(!(player instanceof FakePlayer)) {
			ECUtils.getData(player).modifyRadiation(amount);
		}
	}

	public static int getPlayerRadiation(EntityPlayer player) {
		if(player instanceof FakePlayer) {
			return 0;
		}
		return ECUtils.getData(player).getPlayerRadiation();
	}

	public static void playerTick(EntityPlayer player) {
		if(player instanceof FakePlayer) {
			return;
		}
		int dimID = player.dimension;
		if(player.ticksExisted % 20 == 0 && dimID == Config.dimensionID && !player.capabilities.isCreativeMode) {
			int chunkX = player.chunkCoordX;
			int chunkZ = player.chunkCoordZ;
			Random rnd = new Random(Long.parseLong(Math.abs(chunkX)*128+""+Math.abs(chunkZ)*128));
			Biome biome = player.getEntityWorld().getBiome(player.getPosition());
			int rndRad = rnd.nextInt(6);
			if(biome == BiomeRegistry.desert) {
				rndRad += 2;
			}
			if(biome == BiomeRegistry.dreadlands) {
				rndRad += 6;
			}
			rndRad = (int)(rndRad * ECUtils.getGenResistance(2, player));
			increasePlayerRadiation(player, rndRad);
		}
		int amount = getPlayerRadiation(player);
		if(player.ticksExisted % 20 == 0 && amount > 0) {
			if(player.dimension == Config.dimensionID) {
				increasePlayerRadiation(player, -1);
			}
			else {
				increasePlayerRadiation(player, -5);
			}
		}
		if(amount > 0) {
			boolean hasEffect = player.getActivePotionEffect(PotionRegistry.radiation) != null;
			int currentDuration = amount;
			int newModifier = currentDuration/10000;
			if(hasEffect) {
				player.removeActivePotionEffect(PotionRegistry.radiation);
				player.addPotionEffect(new PotionEffect(PotionRegistry.radiation, currentDuration, newModifier, true, false));
			}
			else {
				player.addPotionEffect(new PotionEffect(PotionRegistry.radiation, currentDuration, newModifier, true, false));
			}
		}
	}
}
