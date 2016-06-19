package uk.binarycraft.storagesilo.blocks.storagesilo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import uk.binarycraft.storagesilo.blocks.SiloTileEntity;
import uk.binarycraft.storagesilo.inventory.ContainerBase;
import uk.binarycraft.storagesilo.inventory.SlotSearchable;

public class ContainerStorageSilo extends ContainerBase
{

	private int numRows;
	private SiloTileEntity deepStorageChest;


	public ContainerStorageSilo(EntityPlayer player, SiloTileEntity deepStorageChest)
	{
		super(deepStorageChest);
		this.deepStorageChest = deepStorageChest;

		this.numRows = deepStorageChest.getSizeInventory() / 9;

		for (int row = 0; row < numRows; ++row)
		{
			for (int slot = 0; slot < 9; ++slot)
			{
				this.addSlotToContainer(
						new SlotSearchable(this.deepStorageChest, slot + row * 9, 8 + slot * 18, 22 + row * 18));
			}
		}

		addPlayerInventorySlots(player.inventory);
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

}