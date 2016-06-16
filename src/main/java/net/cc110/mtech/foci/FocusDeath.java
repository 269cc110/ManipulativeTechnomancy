package net.cc110.mtech.foci;

import java.util.*;
import net.cc110.mtech.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.translation.*;

public class FocusDeath implements ICoreFocus
{
	private static final ItemStack CORE_MATERIAL = new ItemStack(MTech.resource, 1, 6);
	
	public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker)
	{
		victim.attackEntityFrom(DamageSource.magic, Float.MAX_VALUE);
		attacker.attackEntityFrom(DamageSource.magic, attacker.getHealth() * 0.75F);
		
		return true;
	}
	
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
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
		world.playSound(null, player.posX, player.posY, player.posZ, MTech.repulsionBoom, SoundCategory.MASTER, 1.0F, 1.0F); // TODO unique sound
		
		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(player.posX - ConfigHolder.deathRange, player.posY - ConfigHolder.deathRange, player.posZ - ConfigHolder.deathRange, player.posX + ConfigHolder.deathRange, player.posY + ConfigHolder.deathRange, player.posZ + ConfigHolder.deathRange));
		
		for(Entity toKill : entities)
		{
			if(!(toKill instanceof EntityLiving)) continue;
			
			Vec3d playerVec3 = new Vec3d(player.posX, player.posY, player.posZ);
			Vec3d toKillVec3 = new Vec3d(toKill.posX, toKill.posY, toKill.posZ);
			
			double distance = playerVec3.distanceTo(toKillVec3);
			
			if(distance > ConfigHolder.deathRange) continue;
			
			toKill.attackEntityFrom(DamageSource.magic, Float.MAX_VALUE);
		}
		
		player.attackEntityFrom(DamageSource.magic, player.getHealth());
		
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
		list.add(I18n.translateToLocal("mtech.use_with_caution"));
	}
	
	public int getRGBColour()
	{
		return 0x666666;
	}
	
	public ItemStack getCoreCraftingMaterial()
	{
		return CORE_MATERIAL;
	}
}
