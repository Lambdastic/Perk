package edu.vt.alic.perks.listeners;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import edu.vt.alic.perks.PlayerData;
import edu.vt.alic.perks.Perks;

/**
 * Listener class once a player joins.
 * Adds that player to the playerData HashMap
 * Inserts the player's uuid into the table if his record doesn't exist.
 * 
 * @author Ali Camlibel (Lambdastic)
 * @since 05/15/16
 * @version 1.0
 */
public class PlayerJoinListener implements Listener {
	
	@EventHandler (ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent e) throws SQLException {
		Player p = e.getPlayer();

		String sql = "SELECT * FROM PlayerData WHERE player_uuid = '"+ p.getUniqueId().toString() + "'";

		Statement statement = Perks.getInstance().getMySql().getConnection().createStatement();

		ResultSet rs = statement.executeQuery(sql);

		Perks.getInstance().getPlayerData().put(p.getUniqueId(), new PlayerData(p));
		
		PlayerData playerData = Perks.getInstance().getPlayerData().get(p.getUniqueId());
		
		setPlayerWalkSpeed(p, playerData); //set walk speed when player joins
		
		if (rs.next()) //does his record exist? if so, don't add another to the table.
			return;

		//default values of the Perks are all 0, so don't need to worry about setting value there.
		sql = "INSERT INTO PlayerData (player_uuid) VALUES ('" + p.getUniqueId().toString() + "')";

		statement.executeUpdate(sql);

		rs.close();
		statement.close();
	}
	
	/**
	 * @param p player to set walk speed once a higher level Speed perk is learned.
	 * @param playerData playerData to check what the Speed level is and increase based on that.
	 */
	private void setPlayerWalkSpeed(Player p, PlayerData playerData) {
		float defaultSpeed = .2f; //Minecraft's default walk speed
		float speed = 0;
		float percentIncrease = 0;
		
		if (playerData.getSpeedLevel() == 1) 
			percentIncrease = .2f; //increase by 20%
		else if (playerData.getSpeedLevel() == 2) 
			percentIncrease = .3f; //increase by 30%
		else if (playerData.getSpeedLevel() == 3) 
			percentIncrease = .4f; //increase by 40%
		
		speed = (defaultSpeed * percentIncrease) + defaultSpeed;
		p.setWalkSpeed(speed);
	}
}
