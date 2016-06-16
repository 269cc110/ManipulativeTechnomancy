package net.cc110.mtech;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.creativetab.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.block.model.*;

public class ItemResource extends Item
{
	//TODO textures for bat wing, fourcore alloy and wolf tailbone
	private String[] names = new String[]{"bat_wing", "lens", "sirium_dust", "sirium_ingot", "fourcore_alloy", "sirium_nugget", "wolf_tailbone", "golden_feather"};
	
	public ItemResource()
	{
		super();
		
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for(int i = 0; i < names.length; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	public String getUnlocalizedName(ItemStack stack)
	{
		return "item.mtech_" + names[MathHelper.clamp_int(stack.getMetadata(), 0, names.length - 1)];
	}
	
	void registerRenderers(ItemModelMesher mesher)
	{
		ModelResourceLocation[] locations = new ModelResourceLocation[names.length];
		
		for(int i = 0; i < names.length; i++)
		{
			mesher.register(this, i, locations[i] = new ModelResourceLocation("mtech:" + names[i], "inventory"));
		}
		
		ModelBakery.registerItemVariants(this, locations);
	}
}
