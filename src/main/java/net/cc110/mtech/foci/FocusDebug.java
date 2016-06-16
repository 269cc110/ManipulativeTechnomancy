package net.cc110.mtech.foci;

import java.util.*;
import net.cc110.mtech.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.management.*;

public class FocusDebug implements ICoreFocus
{
	public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker)
	{
		return false;
	}
	
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return EnumActionResult.FAIL;
	}
	
	public int maxDamage(ItemStack stack)
	{
		return 0;
	}
	
	public boolean crackedTick(ItemStack stack, World world, Entity entity, int i, boolean b)
	{
		return false;
	}
	
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		if(!player.capabilities.isCreativeMode) return ActionResult.newResult(EnumActionResult.FAIL, stack);
		
		String playerName = player.getName();
		
		if(!world.isRemote)
		{
			PlayerList server = player.getServer().getPlayerList();

			server.sendChatMsg(new TextComponentString(TextFormatting.RED + "Manipulative Technomancy reinitialisation triggered by " + TextFormatting.RED + playerName));
			server.sendChatMsg(new TextComponentString(TextFormatting.RED + "This is generally a very bad idea"));
		}
		
		PowerRegistry.reInit(playerName);
		
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	public boolean isUnstable(ItemStack stack, World world)
	{
		return false;
	}
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b)
	{
		
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b)
	{
		list.add("DO NOT USE");
	}
	
	public int getRGBColour()
	{
		return 0xFFFFFF;
	}
	
	public ItemStack getCoreCraftingMaterial()
	{
		return null;
	}
}
