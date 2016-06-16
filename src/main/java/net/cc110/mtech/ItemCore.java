package net.cc110.mtech;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;

public class ItemCore extends Item
{
	public ItemCore()
	{
		super();
		setHasSubtypes(true);
		setMaxStackSize(1);
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b)
	{
		list.add(PowerRegistry.getLocalFocusName(NBTHelper.getStackString(stack, "FocusID")));
	}
	
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		Iterator<String> iterator = PowerRegistry.idIterator();
		
		while(iterator.hasNext())
		{
			String focusID = iterator.next();
			
			ItemStack stack = new ItemStack(item, 1, 0);
			
			NBTHelper.setStackString(stack, "FocusID", focusID);
			
			list.add(stack);
		}
	}
}
