package net.cc110.mtech.foci;

import java.util.*;
import net.cc110.mtech.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.util.text.translation.*;

public class FocusWater implements ICoreFocus
{
	private static final ItemStack CORE_MATERIAL = new ItemStack(Blocks.TNT);
	
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
		RayTraceResult traceResult = RayTrace.rayTrace(world, player, false, ConfigHolder.waterRange);
		
		if(traceResult == null)
		{
			return new ActionResult(EnumActionResult.PASS, stack);
		}
		else if(traceResult.typeOfHit != RayTraceResult.Type.BLOCK)
		{
			return new ActionResult(EnumActionResult.PASS, stack);
		}
		else
		{
			BlockPos hitPosition = traceResult.getBlockPos();
			
			if (!world.isBlockModifiable(player, hitPosition))
			{
				return new ActionResult(EnumActionResult.FAIL, stack);
			}
			else
			{
				boolean isHitReplaceable = world.getBlockState(hitPosition).getBlock().isReplaceable(world, hitPosition);
				
				BlockPos targetPosition = (isHitReplaceable && traceResult.sideHit == EnumFacing.UP) ? hitPosition : hitPosition.offset(traceResult.sideHit);
				
				if(!player.canPlayerEdit(targetPosition, traceResult.sideHit, stack))
				{
					return new ActionResult(EnumActionResult.FAIL, stack);
				}
				else
				{
					IBlockState targetState = world.getBlockState(targetPosition);
					Material targetMaterial = targetState.getMaterial();
					
					boolean isSolid = targetMaterial.isSolid();
					boolean isTargetReplaceable = targetState.getBlock().isReplaceable(world, targetPosition);
					
					if(!world.isAirBlock(targetPosition) && isSolid && !isTargetReplaceable)
					{
						return new ActionResult(EnumActionResult.FAIL, stack);
					}
					else
					{
						if(world.provider.doesWaterVaporize())
						{
							world.playSound(player, targetPosition, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
							
							for(int k = 0; k < 8; ++k)
							{
								world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)targetPosition.getX() + Math.random(), (double)targetPosition.getY() + Math.random(), (double)targetPosition.getZ() + Math.random(), 0.0, 0.0, 0.0);
							}
						}
						else
						{
							if(!world.isRemote && (!isSolid || isTargetReplaceable) && !targetMaterial.isLiquid())
							{
								world.destroyBlock(targetPosition, true);
							}
							
							world.playSound(player, targetPosition, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
							world.setBlockState(targetPosition, Blocks.WATER.getDefaultState(), 11);
							
							Blocks.WATER.neighborChanged(Blocks.WATER.getDefaultState(), world, targetPosition, Blocks.WATER);
							
							player.attackEntityFrom(DamageSource.magic, 3.0F);
						}
						
						return new ActionResult(EnumActionResult.SUCCESS, stack);
					}
				}
			}
		}
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
		return 0x345FDA;
	}
	
	public ItemStack getCoreCraftingMaterial()
	{
		return CORE_MATERIAL;
	}
}
