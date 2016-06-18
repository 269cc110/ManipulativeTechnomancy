package net.cc110.mtech.foci;

import java.util.*;
import net.cc110.mtech.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.translation.*;

public class FocusInferno implements ICoreFocus
{
	private static final ItemStack CORE_MATERIAL = new ItemStack(Items.MAGMA_CREAM);
	
	public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker)
	{
		victim.setFire(ConfigHolder.infernoBurnTime);
		attacker.setFire(ConfigHolder.infernoBurnTime / 2);
		
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
		
		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(player.posX - ConfigHolder.infernoRange, player.posY - ConfigHolder.infernoRange, player.posZ - ConfigHolder.infernoRange, player.posX + ConfigHolder.infernoRange, player.posY + ConfigHolder.infernoRange, player.posZ + ConfigHolder.infernoRange));
		
		for(Entity toBurn : entities)
		{
			if(!(toBurn instanceof EntityLiving)) continue;
			
			Vec3d playerVec3 = new Vec3d(player.posX, player.posY, player.posZ);
			Vec3d toBurnVec3 = new Vec3d(toBurn.posX, toBurn.posY, toBurn.posZ);
			
			double distance = playerVec3.distanceTo(toBurnVec3);
			
			if(distance > ConfigHolder.deathRange) continue;
			
			toBurn.setFire(ConfigHolder.infernoBurnTime);
		}
		
		player.attackEntityFrom(DamageSource.magic, 10.0F);
		player.setFire(ConfigHolder.infernoBurnTime);
		
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
		return 0xFF6600;
	}
	
	public ItemStack getCoreCraftingMaterial()
	{
		return CORE_MATERIAL;
	}
}
