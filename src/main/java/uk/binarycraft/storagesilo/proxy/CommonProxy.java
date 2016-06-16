package uk.binarycraft.storagesilo.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import uk.binarycraft.storagesilo.StorageSilo;
import uk.binarycraft.storagesilo.blocks.SiloTileEntityBase;
import uk.binarycraft.storagesilo.gui.GuiHandler;

import static uk.binarycraft.storagesilo.StorageSilo.craftingSiloEnabled;
import static uk.binarycraft.storagesilo.StorageSilo.storageSiloEnabled;

public class CommonProxy
{

	public void preInit(FMLPreInitializationEvent event)
	{
	}


	public void init(FMLInitializationEvent event)
	{
		registerTileEntities();
		NetworkRegistry.INSTANCE.registerGuiHandler(StorageSilo.instance, new GuiHandler());
	}


	public void postInit(FMLPostInitializationEvent event)
	{
	}

	private void registerTileEntities()
	{
		if (storageSiloEnabled)
			GameRegistry.registerTileEntity(SiloTileEntityBase.class, "tileEntityStorageSilo");

		if (craftingSiloEnabled)
			GameRegistry.registerTileEntity(SiloTileEntityBase.class, "tileEntityCraftingSilo");
	}
}
