package edu.vt.alic.perks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Util class that is used to return an ItemStack with a custom name/lore based on
 * perk name and perk rank.
 * 
 * @author Ali Camlibel (Lambdastic)
 * @since 05/15/16
 * @version 1.0
 */
public class Util {

	private Util() {}

	/**
	 * @param perk used on the name and to check whether the player has a level in that perk
	 * @param perkRank the level of the perk (1, 2, 3)
	 * @param playerData the player's data
	 * @return a Coal (unlearned) or Charcoal (learned) with a name and lore specifying the name of the perk and level.
	 */
	public static ItemStack getItem(Perk perk, int perkRank, PlayerData playerData) {
		ItemStack i = null;
		ItemMeta im = null;

		if (playerData.getPerkValue(perk) >= perkRank) 
			i = new ItemStack(Material.COAL, 1, (byte) 1); //this is a Charcoal, a coal with a data of 1.
		else 
			i = new ItemStack(Material.COAL);

		im = i.getItemMeta();

		//Capitalizes the perk name because perk.toString() returns the perk name in all caps.
		String perkName = perk.toString().substring(0, 1).toUpperCase() + perk.toString().substring(1).toLowerCase();

		im.setDisplayName(perkName + " - Rank " + perkRank);

		List<String> lore = new ArrayList<String>();

		lore.add("Adds: +" + perk.getAdds());
		lore.add("Total: +" + (perk.getAdds() * perkRank));

		im.setLore(lore);
		
		i.setItemMeta(im);
		
		return i;
	}
}
