package uk.binarycraft.storagesilo.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import uk.binarycraft.storagesilo.StorageSilo;

public class SiloBlockBase extends BlockContainer
{

	protected SiloBlockBase(Material material, String name, float hardness, Class<? extends ItemBlock> itemBlock)
	{
		super(material);
		setUnlocalizedName(name);
		this.setCreativeTab(StorageSilo.storageSiloCreativeTab);
		setHardness(hardness);
		if (itemBlock != null)
		{
			GameRegistry.registerBlock(this, itemBlock, name);
		} else
		{
			GameRegistry.registerBlock(this, name);
		}

	}


	protected SiloBlockBase(Material material, String name, float hardness, String harvestTool, int harvestLevel, Class<? extends ItemBlock> itemBlock)
	{
		this(material, name, hardness, itemBlock);
		setHarvestLevel(harvestTool, harvestLevel);
	}


	protected String getTexture(String name)
	{
		return "storagesilo:" + name;
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
		TileEntity blockTileEntity = world.getTileEntity(pos);
		if (blockTileEntity != null && blockTileEntity instanceof SiloTileEntityBase)
		{
			SiloTileEntityBase siloTileEntity = (SiloTileEntityBase)blockTileEntity;

			if(itemStack.hasTagCompound())
			{
				NBTTagCompound tag = itemStack.getTagCompound();
				siloTileEntity.readFromNBT(tag);
				siloTileEntity.markDirty();
			}
		}
		super.onBlockPlacedBy(world, pos, blockState, entityPlayer, itemStack);
	}


	protected int determineOrientation(World world, int x, int y, int z, EntityLivingBase entity)
	{
		if (MathHelper.abs((float) entity.posX - (float) x) < 2.0F && MathHelper.abs((float) entity.posZ - (float) z) < 2.0F)
		{
			double d0 = entity.posY + 1.82D - (double) entity.getYOffset();

			if (d0 - (double) y > 2.0D)
			{
				return 1;
			}

			if ((double) y - d0 > 0.0D)
			{
				return 0;
			}
		}

		int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));

	}


	protected int getOrientation(int meta, boolean allowUpDown)
	{
		int value = meta & 7;

		if (!allowUpDown)
		{
			if (value == 0 || value == 1)
			{
				// todo:determine which direction the player was facing
				value = 3;
			}
		}
		return value;
	}


	@Override
	public TileEntity createNewTileEntity(World world, int p1)
	{
		return null;
	}





	public void dropInventory(World world, int x, int y, int z, Block block, IInventory tileentity)
	{
		if (tileentity != null)
		{
			for (int i = 0; i < tileentity.getSizeInventory(); ++i)
			{
				ItemStack itemstack = tileentity.getStackInSlot(i);

				if (itemstack != null)
				{
					float f = world.rand.nextFloat() * 0.6F + 0.1F;
					float f1 = world.rand.nextFloat() * 0.6F + 0.1F;
					float f2 = world.rand.nextFloat() * 0.6F + 0.1F;

					while (itemstack.stackSize > 0)
					{
						int j = world.rand.nextInt(21) + 10;

						if (j > itemstack.stackSize)
						{
							j = itemstack.stackSize;
						}

						itemstack.stackSize -= j;
						EntityItem entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1),
								(double) ((float) z + f2), new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

						if (itemstack.hasTagCompound())
						{
							entityitem.getEntityItem().setTagCompound(((NBTTagCompound) itemstack.getTagCompound().copy()));
						}

						float f3 = 0.025F;
						entityitem.motionX = (double) ((float) world.rand.nextGaussian() * f3);
						entityitem.motionY = (double) ((float) world.rand.nextGaussian() * f3 + 0.1F);
						entityitem.motionZ = (double) ((float) world.rand.nextGaussian() * f3);
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
			//TODO: WTF is this method call. Google finds lots of usages, but nothing that documents what it might be.
			//world.func_147453_f(x, y, z, block);
		}
	}

}
