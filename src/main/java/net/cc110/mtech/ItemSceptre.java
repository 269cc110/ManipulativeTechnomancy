package net.cc110.mtech;

import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.*;

public class ItemSceptre extends Item
{
	public ItemSceptre()
	{
		super();
		setHasSubtypes(true);
		setMaxStackSize(1);
	}
	
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		switch(stack.getMetadata())
		{
			case 0:
				return PowerRegistry.getFocus(stack.getTagCompound().getString("FocusID")).onItemUse(stack, player, world, pos, hand, facing, hitX, hitY, hitZ);
			case 1:
				//return ResonanceRegistry.getResonance(stack.getTagCompound().getInteger("ResonanceID")).onItemUse(stack, player, world, x, y, z, i, f, g, h);
			default:
				return EnumActionResult.FAIL;
		}
	}
	
	public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker)
	{
		switch(stack.getMetadata())
		{
			case 0:
				return PowerRegistry.getFocus(NBTHelper.getStackString(stack, "FocusID")).hitEntity(stack, victim, attacker);
			case 1:
				//return ResonanceRegistry.getResonance(NBTHelper.getStackString(stack, "ResonanceID")).hitEntity(stack, victim, attacker);
			default:
				return false;
		}
	}
	
	public boolean hasEffect(ItemStack stack)
	{
		return false;
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b)
	{
		switch(stack.getMetadata())
		{
			case 0:
				String focusID = NBTHelper.getStackString(stack, "FocusID");
				list.add(PowerRegistry.getLocalFocusName(focusID));
				PowerRegistry.getFocus(focusID).addInformation(stack, player, list, b);
				break;
			case 1:
				//int resonanceID = NBTHelper.getStackInt(stack, "ResonanceID");
				//list.add(ResonanceRegistry.getLocalResonanceName(resonanceID));
				break;
		}
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
	
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		switch(stack.getMetadata())
		{
			case 0:
				return PowerRegistry.getFocus(NBTHelper.getStackString(stack, "FocusID")).onItemRightClick(stack, world, player, hand);
			case 1:
				//return ResonanceRegistry.getResonance(NBTHelper.getStackString(stack, "ResonanceID")).onItemRightClick(stack, world, player, hand);
			default:
				return ActionResult.newResult(EnumActionResult.FAIL, stack);
		}
	}
	
	public EnumRarity getRarity(ItemStack stack)
	{
		switch(stack.getMetadata())
		{
			default:
			case 0:
				return EnumRarity.UNCOMMON;
			case 1:
				return EnumRarity.RARE;
		}
	}
	
	public String getUnlocalizedName(ItemStack stack)
	{
		switch(stack.getMetadata())
		{
			default:
			case 0:
				return "item.mtech_focus_sceptre";
			case 1:
				return "item.mtech_resonance_sceptre";
		}
	}
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean held)
	{
		switch(stack.getMetadata())
		{
			case 0:
				PowerRegistry.getFocus(NBTHelper.getStackString(stack, "FocusID")).onUpdate(stack, world, entity, slot, held);
				break;
			case 1:
				//ResonanceRegistry.getResonance(NBTHelper.getStackInt(stack, "ResonanceID")).onUpdate(stack, world, entity, slot, held);
				break;
		}
	}
}
