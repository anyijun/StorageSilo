package uk.binarycraft.storagesilo.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import uk.binarycraft.storagesilo.StorageSilo;

public class SiloBlockBase extends BlockContainer
{

	BlockFlowerPot f;
	protected SiloBlockBase(Material material, String name, float hardness, Class<? extends ItemBlock> itemBlock)
	{
		super(material);
		setUnlocalizedName(name);
		this.setCreativeTab(StorageSilo.storageSiloCreativeTab);
		setHardness(hardness);
		if (itemBlock != null)
		{
			GameRegistry.registerBlock(this, itemBlock, name);
		}
		else
		{
			GameRegistry.registerBlock(this, name);
		}

	}


	@Override
	public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState blockState, float chance, int fortune)
	{
	}


	@Override
	public void breakBlock(World world, BlockPos blockPos, IBlockState blockState)
	{
		NBTTagCompound tag = new NBTTagCompound();
		TileEntity blockTileEntity = world.getTileEntity(blockPos);
		blockTileEntity.writeToNBT(tag);
		ItemStack blockItem = new ItemStack(Item.getItemFromBlock(this), 1, this.damageDropped(blockState));
		EntityItem entityitem = new EntityItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockItem);
		entityitem.getEntityItem().setTagCompound(tag);
		world.spawnEntityInWorld(entityitem);
	}


	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState blockState, EntityLivingBase entityPlayer, ItemStack itemStack)
	{
		super.onBlockPlacedBy(world, pos, blockState, entityPlayer, itemStack);
		TileEntity blockTileEntity = world.getTileEntity(pos);
		if (blockTileEntity != null && blockTileEntity instanceof SiloTileEntity)
		{
			SiloTileEntity siloTileEntity = (SiloTileEntity) blockTileEntity;

			if (itemStack.hasTagCompound())
			{
				NBTTagCompound tag = itemStack.getTagCompound();
				siloTileEntity.readFromNBT(tag);
				siloTileEntity.markDirty();
				world.setTileEntity(pos, siloTileEntity);
			}
		}
	}


	@Override
	public TileEntity createNewTileEntity(World world, int p1)
	{
		return null;
	}

}
