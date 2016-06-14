package uk.binarycraft.storagesilo;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import uk.binarycraft.storagesilo.blocks.ModBlocks;
import uk.binarycraft.storagesilo.proxy.CommonProxy;

@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.MODNAME)
public class StorageSilo
{

	public static int siloCapacity;
	public static boolean storageSiloEnabled;
	public static boolean craftingSiloEnabled;
	public static boolean versionCheckEnabled;
	public static String MODVERSION = "1.2.3";
	@Instance(Reference.MODID)
	public static StorageSilo instance;
	@SidedProxy(clientSide = "uk.binarycraft.storagesilo.proxy.ClientProxy", serverSide = "uk.binarycraft.storagesilo.proxy.CommonProxy")
	public static CommonProxy proxy;
	public static boolean haveNotifiedVersionOutOfDate = false;
	public static CreativeTabs storageSiloCreativeTab = new CreativeTabs("StorageSilo")
	{
		@Override
		public Item getTabIconItem()
		{
			if (storageSiloEnabled)
				return new ItemStack(ModBlocks.storageSilo).getItem();
			else
				return new ItemStack(ModBlocks.craftingSilo).getItem();
		}
	};



	private void getConfiguration(FMLPreInitializationEvent event) throws Exception
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		siloCapacity = config.getInt("StorageSiloCapacity", Configuration.CATEGORY_GENERAL, 999, 54, 999, "The number of available slots in each StorageSilo");
		storageSiloEnabled = config.getBoolean("StorageSiloEnabled", Configuration.CATEGORY_GENERAL, true, "Enable or disable the StorageSilo block");
		craftingSiloEnabled = config.getBoolean("CraftingSiloEnabled", Configuration.CATEGORY_GENERAL, true, "Enable or disable the CraftingSilo block");
		versionCheckEnabled = config.getBoolean("VersionCheckEnabled", Configuration.CATEGORY_GENERAL, true, "Mods shouldn't use chat to inform players of new versions, so you can disable that here");
		config.save();

		if (!storageSiloEnabled && !craftingSiloEnabled)
			throw new Exception("The configuration for the StorageSilo mod is invalid, as it completely disables the mod with both blocks disabled.");
	}


	@EventHandler
	public void preInit(FMLPreInitializationEvent event) throws Exception
	{
		proxy.preInit(event);
		getConfiguration(event);
		ModBlocks.preInit();
	}


	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}


	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		new CraftingRecipes().init();
		proxy.postInit(event);
	}


	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event)
	{
	}


	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
	}

}
