package net.cc110.mtech;

import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;

public class NBTHelper
{
	public static void removeStackTag(ItemStack stack, String name)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(name)) stack.getTagCompound().removeTag(name);
		else
		{
			NBTTagCompound tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
	}
	
	public static void setStackInt(ItemStack stack, String name, int value)
	{
		if(stack.hasTagCompound()) { stack.getTagCompound().setInteger(name, value); }
		else
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger(name, value);
			stack.setTagCompound(tag);
		}
	}
	
	public static int getStackInt(ItemStack stack, String name)
	{
		return getStackInt(stack, name, 0, false);
	}
	
	public static int getStackInt(ItemStack stack, String name, int _default, boolean setDefault)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(name, 3)) return stack.getTagCompound().getInteger(name);
		else
		{
			if(setDefault) setStackInt(stack, name, _default);
			return _default;
		}
	}
	
	public static void removeStackInt(ItemStack stack, String name)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(name, 3)) stack.getTagCompound().removeTag(name);
		else
		{
			NBTTagCompound tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
	}
	
	public static void setStackDouble(ItemStack stack, String name, double value)
	{
		if(stack.hasTagCompound()) stack.getTagCompound().setDouble(name, value);
		else
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setDouble(name, value);
			stack.setTagCompound(tag);
		}
	}
	
	public static double getStackDouble(ItemStack stack, String name)
	{
		return getStackDouble(stack, name, 0, false);
	}
	
	public static double getStackDouble(ItemStack stack, String name, double _default, boolean setDefault)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(name, 6)) return stack.getTagCompound().getDouble(name);
		else
		{
			if(setDefault) setStackDouble(stack, name, _default);
			return _default;
		}
	}
	
	public static void removeStackDouble(ItemStack stack, String name)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(name, 6)) stack.getTagCompound().removeTag(name);
		else
		{
			NBTTagCompound tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
	}
	
	public static void setStackCompoundTag(ItemStack stack, String name, NBTTagCompound value)
	{
		if(stack.hasTagCompound()) stack.getTagCompound().setTag(name, value);
		else
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setTag(name, value);
			stack.setTagCompound(tag);
		}
	}
	
	public static NBTTagCompound getStackCompoundTag(ItemStack stack, String name)
	{
		return getStackCompoundTag(stack, name, null, false);
	}
	
	public static NBTTagCompound getStackCompoundTag(ItemStack stack, String name, NBTTagCompound _default, boolean setDefault)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(name, 10)) return (NBTTagCompound)stack.getTagCompound().getTag(name);
		else
		{
			if(setDefault) setStackCompoundTag(stack, name, _default);
			return _default;
		}
	}
	
	public static void setStackFloat(ItemStack stack, String name, float value)
	{
		if(stack.hasTagCompound()) stack.getTagCompound().setFloat(name, value);
		else
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setFloat(name, value);
			stack.setTagCompound(tag);
		}
	}
	
	public static float getStackFloat(ItemStack stack, String name)
	{
		return getStackFloat(stack, name, 0, false);
	}
	
	public static float getStackFloat(ItemStack stack, String name, float _default, boolean setDefault)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(name, 5)) return stack.getTagCompound().getFloat(name);
		else
		{
			if(setDefault) setStackFloat(stack, name, _default);
			return _default;
		}
	}
	
	public static void removeStackFloat(ItemStack stack, String name)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(name, 5)) stack.getTagCompound().removeTag(name);
		else
		{
			NBTTagCompound tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
	}
	
	public static void setStackString(ItemStack stack, String name, String value)
	{
		if(stack.hasTagCompound()) stack.getTagCompound().setString(name, value);
		else
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString(name, value);
			stack.setTagCompound(tag);
		}
	}
	
	public static String getStackString(ItemStack stack, String name)
	{
		return getStackString(stack, name, "", false);
	}
	
	public static String getStackString(ItemStack stack, String name, String _default, boolean setDefault)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(name, 8)) return stack.getTagCompound().getString(name);
		else
		{
			if(setDefault) setStackString(stack, name, _default);
			return _default;
		}
	}
	
	public static void removeStackString(ItemStack stack, String name)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(name, 8)) stack.getTagCompound().removeTag(name);
		else
		{
			NBTTagCompound tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
	}
	
	public static void setStackLong(ItemStack stack, String name, long value)
	{
		if(stack.hasTagCompound()) stack.getTagCompound().setLong(name, value);
		else
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setLong(name, value);
			stack.setTagCompound(tag);
		}
	}
	
	public static long getStackLong(ItemStack stack, String name)
	{
		return getStackLong(stack, name, 0L, false);
	}
	
	public static long getStackLong(ItemStack stack, String name, long _default, boolean setDefault)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(name, 4)) return stack.getTagCompound().getLong(name);
		else
		{
			if(setDefault) setStackLong(stack, name, _default);
			return _default;
		}
	}
	
	public static void removeStackLong(ItemStack stack, String name)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(name, 4)) stack.getTagCompound().removeTag(name);
		else
		{
			NBTTagCompound tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
	}
	
	public static void setStackUUID(ItemStack stack, String name, UUID value)
	{
		String msbName = name + "-msb";
		String lsbName = name + "-lsb";
		
		if(stack.hasTagCompound()) stack.getTagCompound().setLong(msbName, value.getMostSignificantBits());
		else
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setLong(msbName, value.getMostSignificantBits());
			stack.setTagCompound(tag);
		}
		
		stack.getTagCompound().setLong(lsbName, value.getLeastSignificantBits());
	}
	
	public static UUID getStackUUID(ItemStack stack, String name)
	{
		return getStackUUID(stack, name, null, false);
	}
	
	public static UUID getStackUUID(ItemStack stack, String name, UUID _default, boolean setDefault)
	{
		String msbName = name + "-msb";
		String lsbName = name + "-lsb";
		
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(msbName, 4) && stack.getTagCompound().hasKey(lsbName, 4)) return new UUID(stack.getTagCompound().getLong(msbName), stack.getTagCompound().getLong(lsbName));
		else
		{
			if(setDefault) setStackUUID(stack, name, _default);
			return _default;
		}
	}
	
	public static void removeStackUUID(ItemStack stack, String name)
	{
		String msbName = name + "-msb";
		String lsbName = name + "-lsb";
		
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(msbName, 4)) stack.getTagCompound().removeTag(msbName);
		else
		{
			NBTTagCompound tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
		
		if(stack.getTagCompound().hasKey(lsbName, 4)) stack.getTagCompound().removeTag(lsbName);
	}
}
