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

public class FocusLava implements ICoreFocus
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
		RayTraceResult traceResult = RayTrace.rayTrace(world, player, false, ConfigHolder.lavaRange);
		
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
						if(!world.isRemote && (!isSolid || isTargetReplaceable) && !targetMaterial.isLiquid())
						{
							world.destroyBlock(targetPosition, true);
						}
						
						world.playSound(player, targetPosition, SoundEvents.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.BLOCKS, 1.0F, 1.0F);
						world.setBlockState(targetPosition, Blocks.LAVA.getDefaultState(), 11);
						
						Blocks.LAVA.neighborChanged(Blocks.LAVA.getDefaultState(), world, targetPosition, Blocks.LAVA);
						
						player.attackEntityFrom(DamageSource.magic, 7.0F);
						
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
		list.add(I18n.translateToLocal("mtech.use_with_caution"));
	}
	
	public int getRGBColour()
	{
		return 0xDC8638;
	}
	
	public ItemStack getCoreCraftingMaterial()
	{
		return CORE_MATERIAL;
	}
}
