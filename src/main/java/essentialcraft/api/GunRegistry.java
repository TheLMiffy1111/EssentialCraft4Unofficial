package essentialcraft.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DummyCore.Utils.DummyData;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

public class GunRegistry {

	public static final List<GunMaterial> GUN_MATERIALS = new ArrayList<>();
	public static final List<LenseMaterial> LENSE_MATERIALS = new ArrayList<>();
	public static final List<ScopeMaterial> SCOPE_MATERIALS = new ArrayList<>();
	public static final List<ScopeMaterial> SCOPE_MATERIALS_SNIPER = new ArrayList<>();

	public static ScopeMaterial getScopeFromID(String id) {
		for(ScopeMaterial material : SCOPE_MATERIALS) {
			if(material.id.equalsIgnoreCase(id)) {
				return material;
			}
		}
		return null;
	}

	public static ScopeMaterial getScopeSniperFromID(String id) {
		for(ScopeMaterial material : SCOPE_MATERIALS_SNIPER) {
			if(material.id.equalsIgnoreCase(id)) {
				return material;
			}
		}
		return null;
	}

	public static LenseMaterial getLenseFromID(String id) {
		for(LenseMaterial material : LENSE_MATERIALS) {
			if(material.id.equalsIgnoreCase(id)) {
				return material;
			}
		}
		return null;
	}

	public static GunMaterial getGunFromID(String id) {
		for(GunMaterial material : GUN_MATERIALS) {
			if(material.id.equalsIgnoreCase(id)) {
				return material;
			}
		}
		return null;
	}

	public static class ScopeMaterial {

		public String id;
		public ItemStack recipe = ItemStack.EMPTY;
		public boolean sniper;
		public HashMap<GunType, ArrayList<DummyData>> materialData = new HashMap<>();
		public HashMap<String, String> textures = new HashMap<>();

		public ScopeMaterial(String id, boolean sniper) {
			this.id = id;
			this.sniper = sniper;
		}

		public ScopeMaterial setRecipe(ItemStack recipe) {
			this.recipe = recipe;
			return this;
		}

		public ScopeMaterial setTextures(String... textures) {
			if(textures.length == 3) {
				this.textures.put("pistol", textures[0]);
				this.textures.put("rifle", textures[1]);
				this.textures.put("sniper", textures[2]);
				for(int i = 0; i < 3; i++) {
					ApiCore.registerTexture(new ResourceLocation(textures[i]));
				}
			}
			return this;
		}

		public ScopeMaterial setTexture(String texture) {
			textures.put("sniper", texture);
			ApiCore.registerTexture(new ResourceLocation(texture));
			return this;
		}

		public ScopeMaterial appendData(String key, float value, GunType gun) {
			ArrayList<DummyData> data = materialData.computeIfAbsent(gun, k->new ArrayList<>());
			data.add(new DummyData(key, value));
			materialData.put(gun, data);
			return this;
		}

		public ScopeMaterial appendData(String key, float value) {
			for(GunType gun : GunType.values()) {
				ArrayList<DummyData> d = materialData.computeIfAbsent(gun, k->new ArrayList<>());
				d.add(new DummyData(key, value));
				materialData.put(gun, d);
			}
			return this;
		}

		public ScopeMaterial register() {
			if(!sniper) {
				SCOPE_MATERIALS.add(this);
			}
			else {
				SCOPE_MATERIALS_SNIPER.add(this);
			}
			return this;
		}
	}

	public static class LenseMaterial {

		public HashMap<GunType, ArrayList<DummyData>> materialData = new HashMap<>();
		public String id;
		public ItemStack recipe = ItemStack.EMPTY;
		public HashMap<String, String> textures = new HashMap<>();

		public LenseMaterial(String id) {
			this.id = id;
		}

		public LenseMaterial setRecipe(ItemStack recipe) {
			this.recipe = recipe;
			return this;
		}

		public LenseMaterial setTextures(String... textures) {
			if(textures.length == 4) {
				this.textures.put("pistol", textures[0]);
				this.textures.put("rifle", textures[1]);
				this.textures.put("sniper", textures[2]);
				this.textures.put("gatling", textures[3]);
				for(int i = 0; i < 4; i++) {
					ApiCore.registerTexture(new ResourceLocation(textures[i]));
				}
			}
			return this;
		}

		public LenseMaterial appendData(String key, float value, GunType gun) {
			ArrayList<DummyData> data = materialData.computeIfAbsent(gun, k->new ArrayList<>());
			data.add(new DummyData(key, value));
			materialData.put(gun, data);
			return this;
		}

		public LenseMaterial appendData(String key, float value) {
			for(GunType gun : GunType.values()) {
				ArrayList<DummyData> data = materialData.computeIfAbsent(gun, k->new ArrayList<>());
				data.add(new DummyData(key, value));
				materialData.put(gun, data);
			}
			return this;
		}

		public LenseMaterial register() {
			LENSE_MATERIALS.add(this);
			return this;
		}
	}

	public static class GunMaterial {

		public HashMap<GunType, ArrayList<DummyData>> materialData = new HashMap<>();
		public String id;
		public ItemStack recipe = ItemStack.EMPTY;
		public HashMap<String, String> baseTextures = new HashMap<>();
		public HashMap<String, String> handleTextures = new HashMap<>();
		public HashMap<String, String> deviceTextures = new HashMap<>();

		public GunMaterial(String id) {
			this.id = id;
		}

		public GunMaterial setRecipe(ItemStack recipe) {
			this.recipe = recipe;
			return this;
		}

		public GunMaterial setTextures(String... textures) {
			if(textures.length == 12) {
				baseTextures.put("pistol", textures[0]);
				baseTextures.put("rifle", textures[1]);
				baseTextures.put("sniper", textures[2]);
				baseTextures.put("gatling", textures[3]);

				handleTextures.put("pistol", textures[4]);
				handleTextures.put("rifle", textures[5]);
				handleTextures.put("sniper", textures[6]);
				handleTextures.put("gatling", textures[7]);

				deviceTextures.put("pistol", textures[8]);
				deviceTextures.put("rifle", textures[9]);
				deviceTextures.put("sniper", textures[10]);
				deviceTextures.put("gatling", textures[11]);

				for(int i = 0; i < 12; i++) {
					ApiCore.registerTexture(new ResourceLocation(textures[i]));
				}
			}
			return this;
		}

		public GunMaterial appendData(String key, float value, GunType gun) {
			ArrayList<DummyData> data = materialData.computeIfAbsent(gun, k->new ArrayList<>());
			data.add(new DummyData(key, value));
			materialData.put(gun, data);
			return this;
		}

		public GunMaterial appendData(String key, float value) {
			for(GunType gun : GunType.values()) {
				ArrayList<DummyData> data = materialData.computeIfAbsent(gun, k->new ArrayList<>());
				data.add(new DummyData(key, value));
				materialData.put(gun, data);
			}
			return this;
		}

		public GunMaterial register() {
			GUN_MATERIALS.add(this);
			return this;
		}
	}

	public static enum GunType implements IStringSerializable {

		PISTOL(0, "pistol"),
		RIFLE(1, "rifle"),
		SNIPER(2, "sniper"),
		GATLING(3, "gatling");

		private int index;
		private String name;

		private GunType(int i, String s) {
			index = i;
			name = s;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}

		public int getIndex() {
			return index;
		}

		public static GunType fromIndex(int i) {
			return values()[i];
		}
	}
}
