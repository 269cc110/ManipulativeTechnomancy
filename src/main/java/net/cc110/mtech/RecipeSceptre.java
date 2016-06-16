package net.cc110.mtech;

import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraftforge.common.*;
import net.minecraft.item.crafting.*;

public class RecipeSceptre implements IRecipe
{
	public boolean matches(InventoryCrafting inv, World world)
	{
		ItemStack empty0 = inv.getStackInRowAndColumn(0, 0);
		ItemStack empty1 = inv.getStackInRowAndColumn(0, 1);
		ItemStack empty2 = inv.getStackInRowAndColumn(1, 2);
		ItemStack empty3 = inv.getStackInRowAndColumn(2, 2);
		ItemStack handle = inv.getStackInRowAndColumn(0, 2);
		ItemStack pearl0 = inv.getStackInRowAndColumn(1, 0);
		ItemStack pearl1 = inv.getStackInRowAndColumn(1, 1);
		ItemStack pearl2 = inv.getStackInRowAndColumn(2, 1);
		ItemStack core = inv.getStackInRowAndColumn(2, 0);
		
		return empty0 == null && empty1 == null && empty2 == null && empty3 == null
				&& handle != null && handle.getItem() == Items.BLAZE_ROD
				&& pearl0 != null && pearl0.getItem() == Items.ENDER_PEARL
				&& pearl1 != null && pearl1.getItem() == Items.ENDER_PEARL
				&& pearl2 != null && pearl2.getItem() == Items.ENDER_PEARL
				&& core != null && core.getItem() == MTech.core;
	}
	
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		ItemStack empty0 = inv.getStackInRowAndColumn(0, 0);
		ItemStack empty1 = inv.getStackInRowAndColumn(0, 1);
		ItemStack empty2 = inv.getStackInRowAndColumn(1, 2);
		ItemStack empty3 = inv.getStackInRowAndColumn(2, 2);
		ItemStack handle = inv.getStackInRowAndColumn(0, 2);
		ItemStack pearl0 = inv.getStackInRowAndColumn(1, 0);
		ItemStack pearl1 = inv.getStackInRowAndColumn(1, 1);
		ItemStack pearl2 = inv.getStackInRowAndColumn(2, 1);
		ItemStack core = inv.getStackInRowAndColumn(2, 0);
		
		if(empty0 == null && empty1 == null && empty2 == null && empty3 == null
				&& handle != null && handle.getItem() == Items.BLAZE_ROD
				&& pearl0 != null && pearl0.getItem() == Items.ENDER_PEARL
				&& pearl1 != null && pearl1.getItem() == Items.ENDER_PEARL
				&& pearl2 != null && pearl2.getItem() == Items.ENDER_PEARL
				&& core != null && core.getItem() == MTech.core)
		{
			
			String focusID = NBTHelper.getStackString(core, "FocusID");
			
			if(PowerRegistry.doesFocusExist(focusID))
			{
				ItemStack result = new ItemStack(MTech.sceptre);
				
				NBTHelper.setStackString(result, "FocusID", focusID);
				
				return result;
			}
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
