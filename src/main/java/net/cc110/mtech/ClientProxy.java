package net.cc110.mtech;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.color.*;
import net.minecraft.client.renderer.block.model.*;

public class ClientProxy implements IProxy
{
	public void registerRenderers()
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		
		minecraft.getItemColors().registerItemColorHandler(new MTechItemColourHandler(), MTech.core, MTech.sceptre);
		
		ItemModelMesher mesher = minecraft.getRenderItem().getItemModelMesher();

		MTech.resource.registerRenderers(mesher);
		
		mesher.register(MTech.emptyCore, 0, new ModelResourceLocation("mtech:empty_core", "inventory"));
		mesher.register(MTech.core, 0, new ModelResourceLocation("mtech:core", "inventory"));
		mesher.register(MTech.sceptre, 0, new ModelResourceLocation("mtech:sceptre", "inventory"));
	}
}
