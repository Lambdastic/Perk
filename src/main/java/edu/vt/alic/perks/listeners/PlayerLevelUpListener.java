package edu.vt.alic.perks.listeners;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import edu.vt.alic.perks.Perks;
import edu.vt.alic.perks.PlayerData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Listener class called when player levels up to a level of a multiple of 10.
 * 
 * @author Ali Camlibel (Lambdastic)
 * @since 05/15/16
 * @version 1.0
 */
public class PlayerLevelUpListener implements Listener {
	
	@EventHandler (ignoreCancelled = true)
	public void onPlayerLevelUp(PlayerLevelChangeEvent e) throws SQLException {
		Player p = e.getPlayer();
		int newLevel = e.getNewLevel();
		
		if (newLevel != 0 && newLevel % 10 == 0) { //multiple of 10, so that levels could be 10, 20, 30...
			PlayerData playerData = Perks.getInstance().getPlayerData().get(p.getUniqueId());
			playerData.setAvailablePoints(playerData.getAvailablePoints() + 1);
						
			p.sendMessage(ChatColor.GREEN + "You have gained a point to spend on a perk.");
			p.sendMessage(ChatColor.GREEN + "Total Points: " + playerData.getAvailablePoints());
			p.sendMessage("");
			
			//This is a special message so that when you click it, the command /perks automatically executes.
			TextComponent message = new TextComponent(ChatColor.GREEN + "Click " + ChatColor.AQUA + "here"
					+ ChatColor.GREEN + " to spend your available points, or type /perks");
			message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/perks"));
			
			p.spigot().sendMessage(message);
		}
	}
}
