package essentialcraft.integration.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DummyCore.Utils.DrawUtils;
import DummyCore.Utils.MathUtils;
import essentialcraft.api.RadiatingChamberRecipe;
import essentialcraft.common.mod.EssentialCraftCore;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class RadiatingChamber {

	public static final String UID = "essentialcraft:radiatingChamber";

	public static class Wrapper implements IRecipeWrapper {

		private RadiatingChamberRecipe rec;

		public Wrapper(RadiatingChamberRecipe rec) {
			this.rec = rec;
		}

		@Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
			int percentageScaled = MathUtils.pixelatedTextureSize((int)(rec.mruRequired*rec.costModifier), 5000, 72);
			TextureAtlasSprite icon = (TextureAtlasSprite)EssentialCraftCore.proxy.getClientIcon("mru");
			DrawUtils.drawTexture(1, -1+74-percentageScaled, icon, 16, percentageScaled-2, 0);

			minecraft.fontRenderer.drawString((int)(rec.mruRequired*rec.costModifier)+" MRU", 20, 59, 0xFFFFFF, true);
			minecraft.fontRenderer.drawString(rec.mruRequired/20/60+"Min "+(rec.mruRequired/20-rec.mruRequired/20/60*60)+"Sec", 80, 59, 0xFFFFFF, true);

			float upperBalance = Math.min(rec.upperBalanceLine, 2F);
			float lowerBalance = Math.max(rec.lowerBalanceLine, 0F);

			minecraft.fontRenderer.drawString("Upper Balance: "+upperBalance, 56, 5, 0xFFFFFF, true);
			minecraft.fontRenderer.drawString("MRU/Tick: "+(int)rec.costModifier, 56, 23, 0xFFFFFF, true);
			minecraft.fontRenderer.drawString("Lower Balance: "+lowerBalance, 56, 41, 0xFFFFFF, true);
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			List<List<ItemStack>> ret = new ArrayList<>();
			for(Ingredient ing : rec.recipeItems) {
				ret.add(Arrays.asList(ing.getMatchingStacks()));
			}
			ingredients.setInputLists(VanillaTypes.ITEM, ret);
			ingredients.setOutput(VanillaTypes.ITEM, rec.result);
		}
	}

	public static class Category implements IRecipeCategory<RadiatingChamber.Wrapper> {

		private final IDrawable BG;

		public Category(IGuiHelper gh) {
			BG = gh.createDrawable(new ResourceLocation("essentialcraft:textures/gui/jei/radiating_chamber.png"), 0, 0, 162, 72);
		}

		@Override
		public IDrawable getBackground() {
			return BG;
		}

		@Override
		public String getTitle() {
			return I18n.translateToLocal("jei.essentialcraft.recipe.radiatingChamber");
		}

		@Override
		public String getUid() {
			return UID;
		}

		@Override
		public void setRecipe(IRecipeLayout recipeLayout, RadiatingChamber.Wrapper recipeWrapper, IIngredients ingredients) {
			recipeLayout.getItemStacks().init(0, true, 18, 0);
			recipeLayout.getItemStacks().init(1, true, 18, 36);
			recipeLayout.getItemStacks().init(2, false, 36, 18);

			recipeLayout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
			if(ingredients.getInputs(VanillaTypes.ITEM).size()>1) {
				recipeLayout.getItemStacks().set(1, ingredients.getInputs(VanillaTypes.ITEM).get(1));
			}
			recipeLayout.getItemStacks().set(2, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		}

		@Override
		public String getModName() {
			return "essentialcraft";
		}
	}
}
