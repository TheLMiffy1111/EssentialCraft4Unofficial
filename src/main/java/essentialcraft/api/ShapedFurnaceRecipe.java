package essentialcraft.api;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ShapedFurnaceRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	public ItemStack smelted = ItemStack.EMPTY;
	public ItemStack result = ItemStack.EMPTY;

	public ShapedFurnaceRecipe(ItemStack smelted, ItemStack result) {
		this.smelted = smelted;
		this.result = result;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return inv.getStackInSlot(0).isItemEqual(smelted) && inv.getStackInSlot(1).isItemEqual(result);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return result;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width*height >= 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return result;
	}
}
