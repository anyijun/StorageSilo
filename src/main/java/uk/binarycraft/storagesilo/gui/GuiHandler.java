package uk.binarycraft.storagesilo.gui;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import uk.binarycraft.storagesilo.blocks.SiloTileEntity;
import uk.binarycraft.storagesilo.blocks.craftingsilo.BlockCraftingSilo;
import uk.binarycraft.storagesilo.blocks.craftingsilo.ContainerCraftingSilo;
import uk.binarycraft.storagesilo.blocks.craftingsilo.GuiCraftingSilo;
import uk.binarycraft.storagesilo.blocks.storagesilo.BlockStorageSilo;
import uk.binarycraft.storagesilo.blocks.storagesilo.ContainerStorageSilo;
import uk.binarycraft.storagesilo.blocks.storagesilo.GuiStorageSilo;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos blockPos = new BlockPos(x, y, z);
		Block block = world.getBlockState(blockPos).getBlock();
		SiloTileEntity tile = (SiloTileEntity) world.getTileEntity(blockPos);
		if (block instanceof BlockStorageSilo)
		{
			return new ContainerStorageSilo(player, tile);
		}

		if (block instanceof BlockCraftingSilo)
		{
			return new ContainerCraftingSilo(player, tile);
		}
		return null;

	}


	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos blockPos = new BlockPos(x, y, z);
		Block block = world.getBlockState(blockPos).getBlock();
		SiloTileEntity tile = (SiloTileEntity) world.getTileEntity(blockPos);
		if (block instanceof BlockStorageSilo)
		{
			return new GuiStorageSilo(player, tile);
		}

		if (block instanceof BlockCraftingSilo)
		{
			return new GuiCraftingSilo(player, tile);
		}

		return null;
	}


	public enum GUI
	{

		STORAGESILO(0),
		CRAFTINGSILO(1);

		private static GUI[] allValues = values();
		public int ordinal;


		private GUI(int id)
		{
			this.ordinal = id;
		}


		public static GUI fromOrdinal(int n)
		{
			return allValues[n];
		}
	}

}
