package net.cc110.mtech;

import java.util.*;
import java.util.Map.*;
import net.minecraft.item.*;
import net.minecraft.server.management.PlayerList;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.cc110.mtech.foci.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.util.text.translation.*;
import net.minecraftforge.fml.common.registry.*;

public class PowerRegistry
{
	private static HashMap<String, HashMap<String, ICoreFocus>> coreMap = new HashMap<>();
	private static LinkedList<String> idList = new LinkedList<>();
	private static int totalVisibleFoci = 0;
	private static HashMap<ItemStack, String> craftingMaterials = new HashMap<>();
	
	public static final ICoreFocus MISSING_OR_INVALID = new ICoreFocus()
	{
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
			if(!world.isRemote) player.addChatMessage(new TextComponentString(TextFormatting.RED + "Missing or invalid focus ID"));
			
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
			list.add("Invalid focus ID");
		}
		
		public int getRGBColour()
		{
			return 0x000000;
		}
		
		public ItemStack getCoreCraftingMaterial()
		{
			return null;
		}
	};
	
	public static ICoreFocus registerFocus(String id, ICoreFocus focus)
	{
		return registerFocus(id, focus, false);
	}
	
	public static ICoreFocus registerFocus(String id, ICoreFocus focus, boolean hidden)
	{
		if(id == null || focus == null) throw new NullPointerException();
		
		String activeModID = Loader.instance().activeModContainer().getModId().toLowerCase();
		
		HashMap<String, ICoreFocus> insertInto;
		
		if(coreMap.containsKey(activeModID))
		{
			insertInto = coreMap.get(activeModID);
		}
		else
		{
			insertInto = new HashMap<>();
			
			coreMap.put(activeModID, insertInto);
		}
		
		insertInto.put(id, focus);
		
		String canonicalID = activeModID + ":" + id;
		
		if(!hidden)
		{
			idList.add(canonicalID);
			totalVisibleFoci++;
		}
		
		ItemStack coreMaterial = focus.getCoreCraftingMaterial();
		if(coreMaterial != null) craftingMaterials.put(coreMaterial, canonicalID);
		
		MTech.logger.info("Registered " + (hidden ? "hidden " : "") + "focus with ID " + canonicalID);
		
		return focus;
	}
	
	// Will never return null
	public static ICoreFocus getFocus(String id)
	{
		if(id == null) throw new NullPointerException();
		
		id = id.toLowerCase();
		
		if(id.contains(":"))
		{
			String[] idSplit = id.split(":");
			
			if(idSplit.length == 2 && coreMap.containsKey(idSplit[0]))
			{
				HashMap<String, ICoreFocus> modMap = coreMap.get(idSplit[0]);
				
				if(modMap.containsKey(idSplit[1]))
				{
					return modMap.get(idSplit[1]);
				}
			}
		}
		
		return MISSING_OR_INVALID;
	}
	
	public static int getTotalFoci()
	{
		return totalVisibleFoci;
	}
	
	public static String getLocalFocusName(String id)
	{
		if(id == null) throw new NullPointerException();
		
		if(id.contains(":"))
		{
			String[] idSplit = id.split(":");
			
			return I18n.translateToLocal("focus." + idSplit[0] + "_" + idSplit[1] + ".name");
		}
		
		return I18n.translateToLocal("mtech.invalid_or_missing");
	}
	
	public static Iterator<String> idIterator()
	{
		return idList.iterator();
	}
	
	public static int getColour(String id)
	{
		return getFocus(id).getRGBColour();
	}
	
	public static ItemStack getCoreMaterial(String id)
	{
		return getFocus(id).getCoreCraftingMaterial();
	}
	
	public static boolean doesFocusExist(String id)
	{
		return getFocus(id) != MISSING_OR_INVALID;
	}
	
	public static String getFocusIDForMaterial(ItemStack stack)
	{
		for(Entry<ItemStack, String> entry : craftingMaterials.entrySet())
		{
			if(ItemStack.areItemStacksEqual(stack, entry.getKey())) return entry.getValue();
		}
		
		return null;
	}
	
	public static void reInit(String name)
	{
		MTech.logger.warn("Reinitialisation triggered by " + name);
		
		coreMap.clear();
		idList.clear();
		totalVisibleFoci = 0;
		craftingMaterials.clear();
		
		init();
	}
	
	static void init()
	{
		registerFocus("repulsion", new FocusRepulsion());
		registerFocus("death", new FocusDeath());
		registerFocus("inferno", new FocusInferno());
		registerFocus("bats", new FocusBats());
		registerFocus("life", new FocusLife());
		registerFocus("detonation", new FocusDetonation());
		registerFocus("water", new FocusWater());
		registerFocus("lava", new FocusLava());
		
		registerFocus("debug", new FocusDebug(), true);
	}
}
