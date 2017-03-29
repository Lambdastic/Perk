package edu.vt.alic.perks.listeners;

import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import edu.vt.alic.perks.Perks;
import edu.vt.alic.perks.PlayerData;

/**
 * Listener class called when player quits.
 * Saves all data in the player's playerData object to the MySQL table.
 * 
 * @author Ali Camlibel (Lambdastic)
 * @since 05/15/16
 * @version 1.0
 */
public class PlayerQuitListener implements Listener {
	
	@EventHandler (ignoreCancelled = true)
	public void onPlayerQuit(PlayerQuitEvent e) throws SQLException {
		Player p = e.getPlayer();
		PlayerData playerData = Perks.getInstance().getPlayerData().get(p.getUniqueId());
				
		Statement statement = Perks.getInstance().getMySql().getConnection().createStatement();
		statement.executeUpdate(playerData.getSqlStatement());
		statement.close();
	}
}
