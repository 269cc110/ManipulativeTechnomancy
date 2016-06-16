package net.cc110.mtech;

import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraftforge.common.*;
import net.minecraft.item.crafting.*;

public class RecipeCore implements IRecipe
{
	public boolean matches(InventoryCrafting inv, World world)
	{
		ItemStack empty0 = inv.getStackInRowAndColumn(0, 0);
		ItemStack empty1 = inv.getStackInRowAndColumn(2, 0);
		ItemStack empty2 = inv.getStackInRowAndColumn(0, 2);
		ItemStack empty3 = inv.getStackInRowAndColumn(2, 2);
		ItemStack material0 = inv.getStackInRowAndColumn(1, 0);
		ItemStack material1 = inv.getStackInRowAndColumn(0, 1);
		ItemStack material2 = inv.getStackInRowAndColumn(2, 1);
		ItemStack material3 = inv.getStackInRowAndColumn(1, 2);
		ItemStack core = inv.getStackInRowAndColumn(1, 1);
		
		return empty0 == null && empty1 == null && empty2 == null && empty3 == null
				&& material0 != null && material1 != null && material2 != null && material3 != null
				&& ItemStack.areItemStacksEqual(material0, material1)
				&& ItemStack.areItemStacksEqual(material0, material2)
				&& ItemStack.areItemStacksEqual(material0, material3)
				&& PowerRegistry.getFocusIDForMaterial(material0) != null
				&& core != null && core.getItem() == MTech.emptyCore;
	}
	
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		ItemStack empty0 = inv.getStackInRowAndColumn(0, 0);
		ItemStack empty1 = inv.getStackInRowAndColumn(2, 0);
		ItemStack empty2 = inv.getStackInRowAndColumn(0, 2);
		ItemStack empty3 = inv.getStackInRowAndColumn(2, 2);
		ItemStack material0 = inv.getStackInRowAndColumn(1, 0);
		ItemStack material1 = inv.getStackInRowAndColumn(0, 1);
		ItemStack material2 = inv.getStackInRowAndColumn(2, 1);
		ItemStack material3 = inv.getStackInRowAndColumn(1, 2);
		ItemStack core = inv.getStackInRowAndColumn(1, 1);
		
		if(empty0 == null && empty1 == null && empty2 == null && empty3 == null
				&& material0 != null && material1 != null && material2 != null && material3 != null
				&& ItemStack.areItemStacksEqual(material0, material1)
				&& ItemStack.areItemStacksEqual(material0, material2)
				&& ItemStack.areItemStacksEqual(material0, material3)
				&& core != null && core.getItem() == MTech.emptyCore)
		{
			String focusID = PowerRegistry.getFocusIDForMaterial(material0);
			ItemStack result = new ItemStack(MTech.core);
			
			NBTHelper.setStackString(result, "FocusID", focusID);
			
			return result;
		}
		
		return null;
	}
	
	public int getRecipeSize()
	{
		return 9;
	}
	
	public ItemStack getRecipeOutput()
	{
		return null;
	}
	
	public ItemStack[] getRemainingItems(InventoryCrafting inv)
	{
		ItemStack[] contents = new ItemStack[inv.getSizeInventory()];
		
		for (int i = 0; i < contents.length; ++i)
		{
			ItemStack itemstack = inv.getStackInSlot(i);
			contents[i] = ForgeHooks.getContainerItem(itemstack);
		}
		
		return contents;
	}
}
