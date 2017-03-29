package edu.vt.alic.perks.listeners;

import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import edu.vt.alic.perks.Perks;
import edu.vt.alic.perks.PlayerData;

/**
 * Listener class used for when a zombie dies.
 * 
 * @author Ali Camlibel (Lambdastic)
 * @since 05/15/16
 * @version 1.0
 */
public class ZombieDeathListener implements Listener {

	@EventHandler (ignoreCancelled = true)
	public void onZombieDeath(EntityDeathEvent e) {
		if (e.getEntity().getKiller() instanceof Player) {
			Player p = (Player) e.getEntity().getKiller();
			PlayerData playerData = Perks.getInstance().getPlayerData().get(p.getUniqueId());
			
			if (e.getEntity() instanceof Zombie) {
				e.setDroppedExp(playerData.getXpLevel() + 1); //level 0 = 1 xp, level 1 = 2 xp....
				Perks.getInstance().getEconomy().depositPlayer(p, playerData.getXpLevel() + 1); //same with economy
			}
		}
	}
}
