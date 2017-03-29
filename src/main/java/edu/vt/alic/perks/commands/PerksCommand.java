package edu.vt.alic.perks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import edu.vt.alic.perks.Perk;
import edu.vt.alic.perks.Perks;
import edu.vt.alic.perks.PlayerData;
import edu.vt.alic.perks.Util;
import net.md_5.bungee.api.ChatColor;

/**
 * Class used for the /perks command.
 * 
 * @author Ali Camlibel (Lambdastic)
 * @since 05/15/16
 * @version 1.0
 */
public class PerksCommand implements CommandExecutor {
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("perks")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
				return true;
			}
			
			if (args.length != 0) {
				sender.sendMessage(ChatColor.RED + "Incorrect Syntax: /perks");
				return true;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("perks.perks")) {
				sender.sendMessage(ChatColor.RED + "You do not have the permission to perform that command.");
				return true;
			}
			
			PlayerData playerData = Perks.getInstance().getPlayerData().get(p.getUniqueId());
			
			p.openInventory(perksGui(playerData)); //open inventory based on player data
		}
		
		return false;
	}
	
	/**
	 * @param playerData the player's data
	 * @return the Inventory object consisting of all coal & charcoal objects based on player data.
	 */
	private Inventory perksGui(PlayerData playerData) {
		Inventory gui = Perks.getInstance().getPerksGui(playerData);
		
		for (int i = 0; i < 9; i++) { //the columns in the 27 sized GUI
			Perk perk = Perk.getPerkByColumn(i);
			
			gui.setItem(i, Util.getItem(perk, 1, playerData)); //the item in the top row
			gui.setItem(i + 9, Util.getItem(perk, 2, playerData)); //same column, 2nd row
			gui.setItem(i + 18, Util.getItem(perk, 3, playerData)); //same column, 3rd row
		}
		
		return gui;
	}
}
