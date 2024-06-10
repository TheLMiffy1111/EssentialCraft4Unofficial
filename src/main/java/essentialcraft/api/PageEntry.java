package essentialcraft.api;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class PageEntry {

	public String pageTitle;
	public String pageText;
	public ResourceLocation pageImgLink;
	public String pageID;
	public IRecipe pageRecipe;
	public ItemStack[] displayedItems = {};

	public PageEntry setTitle(String title) {
		pageTitle = title;
		return this;
	}

	public PageEntry setText(String text) {
		pageText = text;
		return this;
	}

	public PageEntry setImg(ResourceLocation img) {
		pageImgLink = img;
		return this;
	}

	public PageEntry setRecipe(IRecipe recipe) {
		pageRecipe = recipe;
		return this;
	}

	public PageEntry setDisplayStacks(ItemStack... stacks) {
		displayedItems = stacks;
		return this;
	}

	public PageEntry(String id) {
		pageID = id;
	}
}
