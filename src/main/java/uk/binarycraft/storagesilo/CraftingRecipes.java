package uk.binarycraft.storagesilo;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import uk.binarycraft.storagesilo.blocks.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;


public class CraftingRecipes {

	ItemStack storageSilo = new ItemStack(ModBlocks.storageSilo);
	ItemStack craftingSilo = new ItemStack(ModBlocks.craftingSilo);
	ItemStack enderChest = new ItemStack(Blocks.ender_chest);
	ItemStack chest = new ItemStack(Blocks.chest);
	ItemStack eyeOfEnder = new ItemStack(Items.ender_eye);
	ItemStack diamond = new ItemStack(Items.diamond);
	ItemStack craftingTable = new ItemStack(Blocks.crafting_table);


	public void init() {
		GameRegistry.addRecipe(storageSilo, "ccc", "cec", "ccc", 'c', chest, 'e', enderChest);
		GameRegistry.addRecipe(craftingSilo, "ede", "csc", "ede", 'e', eyeOfEnder, 'd', diamond, 'c', craftingTable, 's', storageSilo);

	}
}
