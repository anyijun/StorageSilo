package uk.binarycraft.storagesilo.blocks.craftingsilo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import uk.binarycraft.storagesilo.StorageSilo;

public class TileEntityCraftingSilo extends TileEntity implements IInventory
{

	public boolean isDirty;
	protected ItemStack[] inventory;


	public TileEntityCraftingSilo()
	{
		inventory = new ItemStack[getSizeInventory()];
	}


	@Override
	public int getSizeInventory()
	{
		return StorageSilo.siloCapacity;// + 10;
	}


	@Override
	public ItemStack getStackInSlot(int slot)
	{
		if (inventory.length > slot)
		{
			return inventory[slot];
		}
		return null;
	}


	@Override
	public ItemStack decrStackSize(int slot, int size)
	{
		if (inventory.length > slot)
		{
			if (inventory[slot] != null)
			{
				ItemStack itemstack;
				if (inventory[slot].stackSize <= size)
				{
					itemstack = inventory[slot];
					inventory[slot] = null;
					markDirty();
					return itemstack;
				} else
				{
					itemstack = inventory[slot].splitStack(size);
					if (inventory[slot].stackSize == 0)
						inventory[slot] = null;
					markDirty();
					return itemstack;
				}
			}
		}
		return null;
	}


	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return null;
	}


	@Override
	public void setInventorySlotContents(int slot, ItemStack item)
	{
		if (inventory.length > slot)
		{
			inventory[slot] = item;
		}
	}


	//	@Override
	//	public String getInventoryName()
	//	{
	//		return "Storehouse";
	//	}


	//	@Override
	//	public boolean hasCustomInventoryName()
	//	{
	//		return false;
	//	}


	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}


	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return true;
	}


	@Override
	public void openInventory(EntityPlayer player)
	{

	}


	@Override
	public void closeInventory(EntityPlayer player)
	{

	}


	//	@Override
	//	public void openInventory()
	//	{
	//	}


	//	@Override
	//	public void closeInventory()
	//	{
	//	}


	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item)
	{
		return true;
	}


	@Override
	public int getField(int id)
	{
		return 0;
	}


	@Override
	public void setField(int id, int value)
	{

	}


	@Override
	public int getFieldCount()
	{
		return 0;
	}


	@Override
	public void clear()
	{

	}


	@Override
	public void readFromNBT(NBTTagCompound nbtTag)
	{
		super.readFromNBT(nbtTag);

		int invSize = getSizeInventory();
		this.inventory = new ItemStack[invSize];

		NBTTagList nbttaglist = nbtTag.getTagList("Items", 10);

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j;
			if (nbttagcompound1.hasKey("Slot")) // Compatibility check
				j = nbttagcompound1.getByte("Slot") & 255;
			else
				j = nbttagcompound1.getInteger("SlotID");

			if (j >= 0 && j < this.inventory.length)
			{
				this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}


	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTag)
	{
		nbtTag = super.writeToNBT(nbtTag);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.inventory.length; ++i)
		{
			if (this.inventory[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setInteger("SlotID", i);
				this.inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbtTag.setTag("Items", nbttaglist);
		return nbtTag;
	}


	@Override
	public void markDirty()
	{
		isDirty = true;
		super.markDirty();
	}



	@Override
	public String getName()
	{
		return null;
	}


	@Override
	public boolean hasCustomName()
	{
		return false;
	}


	@Override
	public ITextComponent getDisplayName()
	{
		return null;
	}


}
