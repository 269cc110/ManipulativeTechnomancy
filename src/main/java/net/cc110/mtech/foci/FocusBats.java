package net.cc110.mtech.foci;

import java.util.*;
import net.cc110.mtech.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.text.translation.*;

public class FocusBats implements ICoreFocus
{
	private static final ItemStack CORE_MATERIAL = new ItemStack(MTech.resource, 1, 0);
	
	public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker)
	{
		victim.attackEntityFrom(DamageSource.magic, victim.getHealth());
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
		
		for(int i = 0; i < ConfigHolder.batCount; i++)
		{
			EntityBat bat = new EntityBat(world);
			
			bat.setLocationAndAngles(player.posX, player.posY + 1, player.posZ, player.rotationPitch, player.rotationYaw);
			
			world.spawnEntityInWorld(bat);
		}
		
		player.attackEntityFrom(DamageSource.magic, 4.0F);
		
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
		list.add(I18n.translateToLocal("mtech.useless"));
	}
	
	public int getRGBColour()
	{
		return 0x38200B;
	}
	
	public ItemStack getCoreCraftingMaterial()
	{
		return CORE_MATERIAL;
	}
}
