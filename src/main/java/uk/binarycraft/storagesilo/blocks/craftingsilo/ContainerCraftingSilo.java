package uk.binarycraft.storagesilo.blocks.craftingsilo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;
import uk.binarycraft.storagesilo.blocks.SiloTileEntity;
import uk.binarycraft.storagesilo.inventory.ContainerBase;
import uk.binarycraft.storagesilo.inventory.SlotSearchable;

public class ContainerCraftingSilo extends ContainerBase
{

	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	private int numRows;
	private SiloTileEntity tileEntityCraftingSilo;
	private IInventory craftResult;
	private World world;


	public ContainerCraftingSilo(EntityPlayer player, SiloTileEntity tileEntityCraftingSilo)
	{
		super(tileEntityCraftingSilo);
		this.tileEntityCraftingSilo = tileEntityCraftingSilo;
		this.craftResult = new InventoryCraftResult();
		this.world = player.worldObj;

		this.numRows = tileEntityCraftingSilo.getSizeInventory() / 9;
		for (int row = 0; row < numRows; ++row)
			for (int slot = 0; slot < 9; ++slot)
				this.addSlotToContainer(new SlotSearchable(this.tileEntityCraftingSilo, slot + row * 9, 8 + slot * 18, 22 + row * 18));


		// Add crafting output slot
		this.addSlotToContainer(new SlotCrafting(player, craftMatrix, craftResult, 9, 130, 97));
		addCraftingGrid(craftMatrix, 0, 31, 80, 3, 3); //67->17

		addPlayerInventorySlots(player.inventory);
	}


	public void onCraftMatrixChanged(IInventory inventory)
	{
		craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrix, world));
	}


	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);

		if (this.world.isRemote)
			return;

		for (int craftGridLoop = 0; craftGridLoop < 9; craftGridLoop++)
		{
			ItemStack itemStack = this.craftMatrix.getStackInSlot(craftGridLoop); // was getStackInSlotOnClosing in 1.8.8
			if (itemStack != null)
				player.dropItem(itemStack, true, false);
		}
	}


	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int i)
	{
		ItemStack result = transferStackInSlotHelper(entityPlayer, i);

		if (result == null)
			return null;

		if (i != 999)
			return result;

		for (int loop = 0; loop < 9; loop++)
		{
			ItemStack currentSlot = craftMatrix.getStackInSlot(loop);

			if (currentSlot == null)
				continue;

			if (currentSlot.stackSize > 0)
				craftMatrix.decrStackSize(loop, 1);
		}
		craftMatrix.markDirty();

		return result;
	}


	@Override
	protected void addPlayerInventorySlots(InventoryPlayer inventory)
	{
		// player inventory
		for (int row = 0; row < 3; ++row)
		{
			for (int slot = 0; slot < 9; ++slot)
			{
				this.addSlotToContainer(new Slot(inventory, slot + row * 9 + 9, 8 + slot * 18, 139 + row * 18));
			}
		}

		// player hotbar
		for (int row = 0; row < 9; ++row)
		{
			this.addSlotToContainer(new Slot(inventory, row, 8 + row * 18, 196 + 1));

		}
	}


	private void addCraftingGrid(IInventory inventory, int startSlot, int x, int y, int width, int height)
	{
		int i = 0;
		for (int h = 0; h < height; h++)
		{
			for (int w = 0; w < width; w++)
			{
				this.addSlotToContainer(new Slot(inventory, startSlot + i++, x + (w * 18), y + (h * 18)));
			}
		}
	}
}