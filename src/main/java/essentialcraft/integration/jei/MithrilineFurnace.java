package essentialcraft.integration.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import essentialcraft.api.MithrilineFurnaceRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;

public class MithrilineFurnace {

	public static final String UID = "essentialcraft:mithrilineFurnace";

	public static class Wrapper implements IRecipeWrapper {

		private MithrilineFurnaceRecipe rec;

		public Wrapper(MithrilineFurnaceRecipe rec) {
			this.rec = rec;
		}

		@Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
			String text = MathHelper.floor(rec.energy)+" ESPE";
			minecraft.fontRenderer.drawString(text, 45-minecraft.fontRenderer.getStringWidth(text)/2, 20, 0x000000, false);
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			List<ItemStack> ret = new ArrayList<>();
			for(ItemStack stk : rec.input.getMatchingStacks()) {
				ret.add(stk.copy());
			}
			for(ItemStack stk : ret) {
				stk.setCount(rec.stackSize);
			}
			ingredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(ret));
			ingredients.setOutput(VanillaTypes.ITEM, rec.result.copy());
		}
	}

	public static class Category implements IRecipeCategory<MithrilineFurnace.Wrapper> {

		private final IDrawable BG;

		public Category(IGuiHelper gh) {
			BG = gh.createDrawable(new ResourceLocation("essentialcraft:textures/gui/jei/mithriline_furnace.png"), 0, 0, 90, 30);
		}

		@Override
		public IDrawable getBackground() {
			return BG;
		}

		@Override
		public String getTitle() {
			return I18n.translateToLocal("jei.essentialcraft.recipe.mithrilineFurnace");
		}

		@Override
		public String getUid() {
			return UID;
		}

		@Override
		public void setRecipe(IRecipeLayout recipeLayout, MithrilineFurnace.Wrapper recipeWrapper, IIngredients ingredients) {
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
