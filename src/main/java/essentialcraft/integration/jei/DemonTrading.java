package essentialcraft.integration.jei;

import DummyCore.Utils.MiscUtils;
import essentialcraft.api.DemonTrade;
import essentialcraft.common.item.ItemsCore;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class DemonTrading {

	public static final String UID = "essentialcraft:demonTrade";

	public static class Wrapper implements IRecipeWrapper {

		private DemonTrade rec;

		public Wrapper(DemonTrade rec) {
			this.rec = rec;
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ItemStack ret;
			if(rec.entityType != null) {
				ret = new ItemStack(ItemsCore.soul, 1, 0);
				MiscUtils.getStackTag(ret).setString("entity", rec.entityType.getRegistryName().toString());
			}
			else {
				ret = rec.desiredItem;
			}
			ingredients.setInput(VanillaTypes.ITEM, ret);
			ingredients.setOutput(VanillaTypes.ITEM, new ItemStack(ItemsCore.genericItem, 1, 52));
		}
	}

	public static class Handler implements IRecipeHandler<DemonTrading.Wrapper> {

		@Override
		public String getRecipeCategoryUid(DemonTrading.Wrapper arg0) {
			return UID;
		}

		@Override
		public Class<DemonTrading.Wrapper> getRecipeClass() {
			return DemonTrading.Wrapper.class;
		}

		@Override
		public DemonTrading.Wrapper getRecipeWrapper(DemonTrading.Wrapper arg0) {
			return arg0;
		}

		@Override
		public boolean isRecipeValid(DemonTrading.Wrapper recipe) {
			return !recipe.rec.desiredItem.isEmpty();
		}
	}

	public static class Category implements IRecipeCategory<DemonTrading.Wrapper> {

		private final IDrawable BG;

		public Category(IGuiHelper gh) {
			BG = gh.createDrawable(new ResourceLocation("essentialcraft:textures/gui/demon.png"), 0, 0, 176, 76);
		}

		@Override
		public IDrawable getBackground() {
			return BG;
		}

		@Override
		public String getTitle() {
			return I18n.translateToLocal("jei.essentialcraft.recipe.demonTrade");
		}

		@Override
		public String getUid() {
			return UID;
		}

		@Override
		public void setRecipe(IRecipeLayout recipeLayout, DemonTrading.Wrapper recipeWrapper, IIngredients ingredients) {
			recipeLayout.getItemStacks().init(0, true, 79, 29);
			recipeLayout.getItemStacks().init(1, false, 79, 47);

			recipeLayout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
			recipeLayout.getItemStacks().set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		}

		@Override
		public String getModName() {
			return "essentialcraft";
		}
	}
}
