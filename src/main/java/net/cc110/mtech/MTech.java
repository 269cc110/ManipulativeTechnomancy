package net.cc110.mtech;

import java.io.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.creativetab.*;
import net.minecraftforge.common.*;
import net.minecraftforge.oredict.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.common.config.*;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.*;

@Mod(modid = "mtech", name = "Manipulative Technomancy", version = "0.1")
public class MTech
{
	public static final Random MTECH_RANDOM = new Random();
	
	@Instance(value = "mtech")
	public static MTech instance;
	
	public static final Logger logger = LogManager.getLogger("Manipulative Techomancy");
	
	@SidedProxy(clientSide = "net.cc110.mtech.ClientProxy", serverSide = "net.cc110.mtech.ServerProxy")
	public static IProxy proxy;
	
	public static final CreativeTabs tab = new CreativeTabs("mtech")
	{
		public Item getTabIconItem()
		{
			return sceptre;
		}
	};
	
	public static ItemResource resource = (ItemResource)new ItemResource().setCreativeTab(tab);
	
	public static Item emptyCore = new Item().setUnlocalizedName("mtech_empty_core").setCreativeTab(tab);
	
	public static ItemCore core = (ItemCore)new ItemCore().setUnlocalizedName("mtech_focus_core").setCreativeTab(tab).setMaxStackSize(1);
	public static ItemSceptre sceptre = (ItemSceptre)new ItemSceptre().setUnlocalizedName("mtech_focus_sceptre").setCreativeTab(tab);
	
	public static SoundEvent repulsionBoom;
	public static SoundEvent repulsionActivate;
	public static SoundEvent repulsionDeactivate;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(new File(event.getSuggestedConfigurationFile().getParentFile(), "manipulative_technomancy.cfg"));
		
		config.load();
		
		ConfigHolder.repulsion1Divisor = config.get("foci", "RepulsionMode1Divisor", 1.5, "Divisor to apply to motion calculation in repulsion mode 1").getDouble();
		ConfigHolder.repulsion1Range = config.get("foci", "RepulsionMode1Range", 5.0, "Range to repel mobs in repulsion mode 1").getDouble();
		
		ConfigHolder.repulsion2Divisor = config.get("foci", "RepulsionMode2Divisor", 0.05, "Divisor to apply to motion calculation in repulsion mode 2").getDouble();
		ConfigHolder.repulsion2Range = config.get("foci", "RepulsionMode2Range", 20.0, "Range to repel mobs in repulsion mode 2").getDouble();
		
		ConfigHolder.deathRange = config.get("foci", "DeathRange", 20.0, "Range to kill mobs with the death sceptre").getDouble();
		
		ConfigHolder.infernoBurnTime = config.get("foci", "InfernoBurnTime", 20, "Time in seconds to burn entites for with the inferno sceptre").getInt();
		ConfigHolder.infernoRange = config.get("foci", "InfernoRange", 20.0, "Range to burn mobs with the inferno sceptre").getDouble();
		
		ConfigHolder.batCount = config.get("foci", "BatSpawnCount", 20, "Number of bats to spawn with the bat sceptre").getInt();
		
		ConfigHolder.lifeRange = config.get("foci", "LifeRange", 20.0, "Range to heal mobs with the life sceptre").getDouble();
		
		ConfigHolder.waterRange = config.get("foci", "WaterRange", 0.0, "Range to place water with the water sceptre (0 for player reach distance)").getDouble();
		
		ConfigHolder.lavaRange = config.get("foci", "LavaRange", 0.0, "Range to place lava with the lava sceptre (0 for player reach distance)").getDouble();
		
		if(config.hasChanged()) config.save();
		
		GameRegistry.register(resource.setRegistryName("resource"));
		GameRegistry.register(emptyCore.setRegistryName("empty_core"));
		GameRegistry.register(core.setRegistryName("core"));
		GameRegistry.register(sceptre.setRegistryName("sceptre"));
		
		ResourceLocation repulsionBoomLocation = new ResourceLocation("mtech", "repulsion_boom");
		ResourceLocation repulsionActivateLocation = new ResourceLocation("mtech", "repulsion_activate");
		ResourceLocation repulsionDeactivateLocation = new ResourceLocation("mtech", "repulsion_deactivate");
		
		repulsionBoom = new SoundEvent(repulsionBoomLocation);
		repulsionActivate = new SoundEvent(repulsionActivateLocation);
		repulsionDeactivate = new SoundEvent(repulsionDeactivateLocation);
		
		GameRegistry.register(repulsionBoom, repulsionBoomLocation);
		GameRegistry.register(repulsionActivate, repulsionActivateLocation);
		GameRegistry.register(repulsionDeactivate, repulsionDeactivateLocation);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		PowerRegistry.init();
		
		proxy.registerRenderers();
		
		MinecraftForge.EVENT_BUS.register(new MTechEventHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		GameRegistry.addRecipe(new ItemStack(resource, 1, 3), "@@@", "@@@", "@@@", '@', new ItemStack(resource, 1, 5));
		GameRegistry.addShapelessRecipe(new ItemStack(resource, 9, 5), new ItemStack(resource, 1, 3));
		
		GameRegistry.addRecipe(new ItemStack(resource, 1, 2), "@@@", "@#@", "@@@", '@', Items.REDSTONE, '#', Items.GLOWSTONE_DUST);
		
		GameRegistry.addRecipe(new ItemStack(resource, 1, 4), "@#+", "#=#", "+#@", '@', Items.GOLD_INGOT, '#', new ItemStack(resource, 1, 2), '+', Items.COAL, '=', Items.IRON_INGOT);
		
		GameRegistry.addRecipe(new ItemStack(resource, 1, 1), "@#@", "#+#", "@#@", '@', new ItemStack(resource, 1, 2), '#', Items.GOLD_INGOT, '+', Items.DIAMOND);
		
		GameRegistry.addSmelting(new ItemStack(resource, 1, 4), new ItemStack(resource, 1, 3), 4.0F);
		
		GameRegistry.addRecipe(new ItemStack(emptyCore), "@#@", "#+#", "@#@", '@', Items.DIAMOND, '#', Items.ENDER_PEARL, '+', new ItemStack(resource, 1, 1));
		
		RecipeSorter.register("mtech_core", RecipeCore.class, RecipeSorter.Category.SHAPED, "");
		GameRegistry.addRecipe(new RecipeCore());
		
		RecipeSorter.register("mtech_sceptre", RecipeSceptre.class, RecipeSorter.Category.SHAPED, "after:mtech_core");
		GameRegistry.addRecipe(new RecipeSceptre());
	}
}
