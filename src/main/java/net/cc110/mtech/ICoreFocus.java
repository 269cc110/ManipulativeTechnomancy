package net.cc110.mtech;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.relauncher.*;

public interface ICoreFocus
{
	boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker);
	
	EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ);
	
	int maxDamage(ItemStack stack);
	
	boolean crackedTick(ItemStack stack, World world, Entity entity, int i, boolean b);
	
	ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand);
	
	boolean isUnstable(ItemStack stack, World world);
	
	void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b);
	
	void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b);
	
	@SideOnly(Side.CLIENT)
	int getRGBColour();
	
	ItemStack getCoreCraftingMaterial();
}
