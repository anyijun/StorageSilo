package uk.binarycraft.storagesilo.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import uk.binarycraft.storagesilo.blocks.ModBlocks;

public class ClientProxy extends CommonProxy
{

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
	}


	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		registerItemModels();
	}


	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
	}


	private void registerItemModels()
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(ModBlocks.craftingSilo), 0, new ModelResourceLocation("storagesilo:craftingsilo", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(ModBlocks.storageSilo), 0, new ModelResourceLocation("storagesilo:storagesilo", "inventory"));
	}
}
