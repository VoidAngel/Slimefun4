package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.machines;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;

public abstract class ElectrifiedComposter extends AContainer {

	public ElectrifiedComposter(Category category, ItemStack item, String name, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, name, recipeType, recipe);
	}
	
	@Override
	public void registerDefaultRecipes() {
		registerRecipe(10, new ItemStack[] {new ItemStack(Material.LEAVES, 8)}, new ItemStack[]{new ItemStack(Material.DIRT)});
		registerRecipe(10, new ItemStack[] {new ItemStack(Material.LEAVES_2, 8)}, new ItemStack[]{new ItemStack(Material.DIRT)});
		registerRecipe(10, new ItemStack[] {new ItemStack(Material.SAPLING, 8)}, new ItemStack[]{new ItemStack(Material.DIRT)});
		registerRecipe(10, new ItemStack[] {new ItemStack(Material.ROTTEN_FLESH, 8)}, new ItemStack[]{new ItemStack(Material.DIRT)});
		registerRecipe(10, new ItemStack[] {new ItemStack(Material.STONE, 4)}, new ItemStack[]{new ItemStack(Material.NETHERRACK)});
		registerRecipe(10, new ItemStack[] {new ItemStack(Material.NETHER_STALK, 2)}, new ItemStack[]{new ItemStack(Material.NETHERRACK)});
		registerRecipe(10, new ItemStack[] {new ItemStack(Material.SAND, 2)}, new ItemStack[]{new ItemStack(Material.SOUL_SAND)});
		registerRecipe(10, new ItemStack[] {new ItemStack(Material.WHEAT, 4)}, new ItemStack[]{new ItemStack(Material.NETHER_STALK)});
	}
	
	@Override
	public String getMachineIdentifier() {
		return "ELECTRIFIED_COMPOSTER";
	}

	@Override
	public ItemStack getProgressBar() {
		return new ItemStack(Material.DIAMOND_HOE);
	}
	
	@Override
	public String getInventoryTitle() {
		return "&2Electrified Composter";
	}
}
