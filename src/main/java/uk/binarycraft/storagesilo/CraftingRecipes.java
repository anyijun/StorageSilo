package uk.binarycraft.storagesilo;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import uk.binarycraft.storagesilo.blocks.ModBlocks;


public class CraftingRecipes
{

	ItemStack storageSilo = new ItemStack(ModBlocks.storageSilo);
	ItemStack craftingSilo = new ItemStack(ModBlocks.craftingSilo);
	ItemStack enderChest = new ItemStack(Blocks.ENDER_CHEST);
	ItemStack chest = new ItemStack(Blocks.CHEST);
	ItemStack eyeOfEnder = new ItemStack(Items.ENDER_EYE);
	ItemStack diamond = new ItemStack(Items.DIAMOND);
	ItemStack craftingTable = new ItemStack(Blocks.CRAFTING_TABLE);


	public void init()
	{
		if (StorageSilo.storageSiloEnabled)
			GameRegistry.addRecipe(storageSilo, "ccc", "cec", "ccc", 'c', chest, 'e', enderChest);

		if (StorageSilo.craftingSiloEnabled)
			GameRegistry.addRecipe(craftingSilo, "ede", "csc", "ede", 'e', eyeOfEnder, 'd', diamond, 'c', craftingTable, 's', storageSilo);

	}
}
