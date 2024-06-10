package essentialcraft.integration.jei;

import java.util.Arrays;
import java.util.Collections;

import essentialcraft.api.WindImbueRecipe;
import essentialcraft.common.block.BlocksCore;
import essentialcraft.common.item.ItemSoulStone;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IIngredientType;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;

public class WindImbue {

	public static final String UID = "essentialcraft:windImbue";

	public static class Wrapper implements IRecipeWrapper {

		private WindImbueRecipe rec;

		public Wrapper(WindImbueRecipe rec) {
			this.rec = rec;
		}

		@Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
			String text = MathHelper.floor(rec.enderEnergy)+" ESPE";
			minecraft.fontRenderer.drawString(text, 45-minecraft.fontRenderer.getStringWidth(text)/2, 20, 0x000000, false);
			if(rec.result.getItem() instanceof ItemSoulStone) {
				text = "+Wind Relations";
				minecraft.fontRenderer.drawString(text, 45-minecraft.fontRenderer.getStringWidth(text)/2, 30, 0x81D17D, true);
			}
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(Arrays.asList(rec.input.getMatchingStacks())));
			ingredients.setOutput(VanillaTypes.ITEM, rec.result);
		}
	}

	public static class Category implements IRecipeCategory<WindImbue.Wrapper> {

		private final IDrawable BG;

		public Category(IGuiHelper gh) {
			BG = gh.createDrawable(new ResourceLocation("essentialcraft:textures/gui/jei/wind_imbue.png"), 0, 0, 90, 40);
		}

		@Override
		public IDrawable getBackground() {
			return BG;
		}

		@Override
		public String getTitle() {
			return I18n.translateToLocal("jei.essentialcraft.recipe.windImbue");
		}

		@Override
		public String getUid() {
			return UID;
		}

		@Override
		public void setRecipe(IRecipeLayout recipeLayout, WindImbue.Wrapper recipeWrapper, IIngredients ingredients) {
			recipeLayout.getItemStacks().init(0, true, 18, 0);
			recipeLayout.getItemStacks().init(1, false, 54, 0);

			recipeLayout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
			recipeLayout.getItemStacks().set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		}

		@Override
		public String getModName() {
			return "essentialcraft";
		}
	}
}
