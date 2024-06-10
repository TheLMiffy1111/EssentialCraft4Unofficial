package essentialcraft.api;

import java.util.ArrayList;
import java.util.List;

public class CorruptionEffectRegistry {

	public static final List<ICorruptionEffect> EFFECTS = new ArrayList<>();

	public static void addEffect(ICorruptionEffect effect) {
		if(effect != null) {
			EFFECTS.add(effect);
		}
	}

	public static ArrayList<ICorruptionEffect> findSuitableEffects(int maxCost) {
		ArrayList<ICorruptionEffect> retLst = new ArrayList<>();
		for(ICorruptionEffect effect : EFFECTS) {
			if(effect.getStickiness() <= maxCost) {
				retLst.add(effect);
				if(effect.getType().equals(EnumCorruptionEffect.BODY)) {
					retLst.add(effect);
					retLst.add(effect);
				}
			}
		}
		return retLst;
	}
}
