package net.cc110.mtech.foci;

import java.util.*;
import net.cc110.mtech.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;

public class FocusRepulsion implements ICoreFocus
{
	private static final ItemStack CORE_MATERIAL = new ItemStack(MTech.resource, 1, 7);
	
	public String getFocusName()
	{
		return "repulsion";
	}
	
	public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker)
	{
		return false;
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
		if(world.isRemote) return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		
		NBTTagCompound tag = NBTHelper.getStackCompoundTag(stack, "RepulsionData", new NBTTagCompound(), true);
		
		if(player.isSneaking())
		{
			int mode = (tag.getInteger("Mode") + 1) % 2;
			
			tag.setInteger("Mode", mode);
			
			player.addChatMessage(new TextComponentString("Switched to mode " + (mode + 1)));//new ChatComponentTranslation("message.focus.repulsion.mode", tag.getInteger("Mode") + 1));
		}
		else
		{
			switch(tag.getInteger("Mode"))
			{
				case 0:
					boolean active = tag.getBoolean("Active");
					
					world.playSound(null, player.posX, player.posY, player.posZ, active ? MTech.repulsionDeactivate : MTech.repulsionActivate, SoundCategory.MASTER, 1.0F, 1.0F);
					
					tag.setBoolean("Active", !active);
					
					player.addChatMessage(new TextComponentString("Now " + (!active ? "active" : "inactive")));
					
					break;
				case 1:
					world.playSound(null, player.posX, player.posY, player.posZ, MTech.repulsionBoom, SoundCategory.MASTER, 1.0F, 1.0F);
					
					List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(player.posX - ConfigHolder.repulsion2Range, player.posY - ConfigHolder.repulsion2Range, player.posZ - ConfigHolder.repulsion2Range, player.posX + ConfigHolder.repulsion2Range, player.posY + ConfigHolder.repulsion2Range, player.posZ + ConfigHolder.repulsion2Range));
					
					for(Entity toRepel : entities)
					{
						if(!(toRepel instanceof EntityLiving)) continue;
						
						Vec3d playerVec3 = new Vec3d(player.posX, player.posY, player.posZ);
						Vec3d toRepelVec3 = new Vec3d(toRepel.posX, toRepel.posY, toRepel.posZ);
						
						double distance = playerVec3.distanceTo(toRepelVec3);
						
						if(distance > ConfigHolder.repulsion2Range) continue;
						
						Vec3d vec3 = new Vec3d(toRepelVec3.xCoord - playerVec3.xCoord, toRepelVec3.yCoord - playerVec3.yCoord, toRepelVec3.zCoord - playerVec3.zCoord);
						
						toRepel.motionX += vec3.xCoord / ConfigHolder.repulsion2Divisor / distance;
						toRepel.motionY += vec3.yCoord / ConfigHolder.repulsion2Divisor / distance;
						toRepel.motionZ += vec3.zCoord / ConfigHolder.repulsion2Divisor / distance;
					}
					
					player.attackEntityFrom(DamageSource.magic, 19.0F);
					
					break;
			}
		}
		
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	public boolean isUnstable(ItemStack stack, World world)
	{
		return false;
	}
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeld)
	{
		if(world.isRemote || !(entity instanceof EntityPlayer)) return;
		
		EntityPlayer player = (EntityPlayer)entity;
		
		NBTTagCompound tag = NBTHelper.getStackCompoundTag(stack, "RepulsionData", new NBTTagCompound(), true);
		
		if(slot > 8)
		{
			//tag.setBoolean("Active", false);
			return;
		}
		
		boolean active = tag.getBoolean("Active");
		
		if(active)
		{
			List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(player.posX - ConfigHolder.repulsion1Range, player.posY - ConfigHolder.repulsion1Range, player.posZ - ConfigHolder.repulsion1Range, player.posX + ConfigHolder.repulsion1Range, player.posY + ConfigHolder.repulsion1Range, player.posZ + ConfigHolder.repulsion1Range));
			
			switch(tag.getInteger("Mode"))
			{
				default:
					//TODO more modes
				case 0:
					for(Entity toRepel : entities)
					{
						if(!(toRepel instanceof EntityLiving)) continue;
						
						Vec3d playerVec3 = new Vec3d(player.posX, player.posY, player.posZ);
						Vec3d toRepelVec3 = new Vec3d(toRepel.posX, toRepel.posY, toRepel.posZ);
						
						double distance = playerVec3.distanceTo(toRepelVec3);
						
						if(distance > ConfigHolder.repulsion1Range) continue;
						
						Vec3d vec3 = new Vec3d(toRepelVec3.xCoord - playerVec3.xCoord, toRepelVec3.yCoord - playerVec3.yCoord, toRepelVec3.zCoord - playerVec3.zCoord);
						
						toRepel.motionX += vec3.xCoord / ConfigHolder.repulsion1Divisor / distance;
						toRepel.motionY += vec3.yCoord / ConfigHolder.repulsion1Divisor / distance;
						toRepel.motionZ += vec3.zCoord / ConfigHolder.repulsion1Divisor / distance;
					}
			}
			
			int damageTimer = tag.getInteger("DamageTimer");
			
			if(damageTimer == 39) player.attackEntityFrom(DamageSource.magic, 1.0F);
			
			tag.setInteger("DamageTimer", (damageTimer + 1) % 40);
		}
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b)
	{
		list.add(NBTHelper.getStackCompoundTag(stack, "RepulsionData", new NBTTagCompound(), true).getBoolean("Active") ? "Active" : "Inactive");
	}
	
	public int getRGBColour()
	{
		return 0x40E0D0;
	}
	
	public ItemStack getCoreCraftingMaterial()
	{
		return CORE_MATERIAL;
	}
}
