package net.cc110.mtech;

import net.minecraft.item.*;
import net.minecraft.client.renderer.color.*;

public class MTechItemColourHandler implements IItemColor
{
	public int getColorFromItemstack(ItemStack stack, int tintIndex)
	{
		return tintIndex == 0 ? PowerRegistry.getColour(NBTHelper.getStackString(stack, "FocusID")) : 0xFFFFFF;
	}
}
