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

public class FocusLife implements ICoreFocus
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
		
		float maxHeal = 0.0F;
		
		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(player.posX - ConfigHolder.lifeRange, player.posY - ConfigHolder.lifeRange, player.posZ - ConfigHolder.lifeRange, player.posX + ConfigHolder.lifeRange, player.posY + ConfigHolder.lifeRange, player.posZ + ConfigHolder.lifeRange));
		
		for(Entity toHeal : entities)
		{
			if(!(toHeal instanceof EntityLiving)) continue;
			
			EntityLiving living = (EntityLiving)toHeal;
			
			Vec3d playerVec3 = new Vec3d(player.posX, player.posY, player.posZ);
			Vec3d toHealVec3 = new Vec3d(living.posX, living.posY, living.posZ);
			
			double distance = playerVec3.distanceTo(toHealVec3);
			
			if(distance > ConfigHolder.lifeRange) continue;
			
			float entityHealth = living.getHealth();
			float healAmount = MathHelper.clamp_float(entityHealth / 2.0F, 0, living.getMaxHealth() - entityHealth);
			
			if(healAmount > maxHeal) maxHeal = healAmount;
			
			living.heal(healAmount);
		}
		
		player.attackEntityFrom(DamageSource.magic, maxHeal);
		
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
		
	}
	
	public int getRGBColour()
	{
		return 0xFF6666;
	}
	
	public ItemStack getCoreCraftingMaterial()
	{
		return CORE_MATERIAL;
	}
}
