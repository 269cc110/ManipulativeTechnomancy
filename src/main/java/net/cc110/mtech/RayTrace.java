package net.cc110.mtech;

import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;

public class RayTrace
{
	// ripped from Item
	public static RayTraceResult rayTrace(World world, EntityPlayer player, boolean useLiquids, double distance)
	{
		float f = player.rotationPitch;
		float f1 = player.rotationYaw;
		
		Vec3d start = new Vec3d(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);
		
		float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
		float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
		float f4 = -MathHelper.cos(-f * 0.017453292F);
		float f5 = MathHelper.sin(-f * 0.017453292F);
		
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		
		if(distance == 0.0)
		{
			distance = player instanceof EntityPlayerMP ? ((EntityPlayerMP)player).interactionManager.getBlockReachDistance() : 5.0;
		}
		
		Vec3d end = start.addVector((double)f6 * distance, (double)f5 * distance, (double)f7 * distance);
		
		return world.rayTraceBlocks(start, end, useLiquids, !useLiquids, false);
	}
}
