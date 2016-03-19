package uk.binarycraft.storagesilo.blocks.storagesilo;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import uk.binarycraft.storagesilo.StorageSilo;
import uk.binarycraft.storagesilo.blocks.BlockContainerBase;
import uk.binarycraft.storagesilo.gui.GuiHandler.GUI;


public class BlockStorageSilo extends BlockContainerBase
{

	public BlockStorageSilo()
	{
		super(Material.iron, "storagesilo", 2.5f, null);
		//TODO: setStepSound(soundTypeMetal);
		isBlockContainer = true;
	}


	@Override
	public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, EnumHand side, ItemStack itemStack, EnumFacing enumFacing, float p7, float p8, float p9)
	{
		player.openGui(StorageSilo.instance, GUI.STORAGESILO.ordinal, world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
		return true;
	}


	@Override
	public TileEntity createNewTileEntity(World world, int p1)
	{
		return new TileEntityStorageSilo();
	}


	@Override
	public void breakBlock(World world, BlockPos blockPos, IBlockState blockState)
	{
		TileEntityStorageSilo tileentity = (TileEntityStorageSilo) world.getTileEntity(blockPos);

		dropInventory(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockState.getBlock(), tileentity);

		super.breakBlock(world, blockPos, blockState);
	}


	@Override
	public EnumBlockRenderType getRenderType(IBlockState blockState)
	{
		return EnumBlockRenderType.MODEL;
	}
}
