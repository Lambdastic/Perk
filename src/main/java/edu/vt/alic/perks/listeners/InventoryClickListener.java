package edu.vt.alic.perks.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import edu.vt.alic.perks.Perk;
import edu.vt.alic.perks.Perks;
import edu.vt.alic.perks.PlayerData;
import edu.vt.alic.perks.Util;
import net.md_5.bungee.api.ChatColor;

/**
 * Listener class that checks when a player clicks on the perks GUI.
 * 
 * 
 * @author Ali Camlibel (Lambdastic)
 * @since 05/15/16
 * @version 1.0
 */
public class InventoryClickListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler (ignoreCancelled = true)
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player))
			return;
		
		if (e.getClickedInventory() == null | e.getInventory() == null)
			return;

		Player p = (Player) e.getWhoClicked();
		PlayerData playerData = Perks.getInstance().getPlayerData().get(p.getUniqueId());

		int availablePoints = playerData.getAvailablePoints();
		
		Inventory clickedInv = e.getClickedInventory();
		
		if (clickedInv.getTitle().contains("Perks")) {
			e.setCancelled(true);

			if (availablePoints == 0) {
				p.sendMessage(ChatColor.RED + "You do not have any points to learn that perk.");
				return;
			}

			int invPos = e.getSlot();

			Perk perk = Perk.getPerkByPosition(invPos);
			
			//hard to explain this code, contact me if you want me to explain it to you.
			for (int i = 0; i < 27; i++) {
				if (i == invPos) {
					if (i > 8) {
						if (clickedInv.getItem(i - 9).getData().getData() == (byte) 1) {
							playerData.setPerkValue(perk, playerData.getPerkValue(perk) + 1);
							playerData.setAvailablePoints(playerData.getAvailablePoints() - 1);
							clickedInv.setItem(i, Util.getItem(perk, Perk.getPerkRank(invPos), playerData));
							
							Inventory inv = Perks.getInstance().getPerksGui(playerData);
							inv.setContents(clickedInv.getContents());
							
							p.closeInventory();
							p.openInventory(inv);
							
							if (perk == Perk.SPEED) {
								setPlayerWalkSpeed(p, playerData);
							}
							
							return;
						}
						else {
							p.sendMessage(ChatColor.RED + "You must learn the perks in order.");
							return;
						}
					}
					playerData.setPerkValue(perk, playerData.getPerkValue(perk) + 1);
					playerData.setAvailablePoints(playerData.getAvailablePoints() - 1);
					clickedInv.setItem(i, Util.getItem(perk, Perk.getPerkRank(invPos), playerData));
					
					Inventory inv = Perks.getInstance().getPerksGui(playerData);
					inv.setContents(clickedInv.getContents());
					
					p.closeInventory();
					p.openInventory(inv);
					
					if (perk == Perk.SPEED) {
						setPlayerWalkSpeed(p, playerData);
					}
					
					return;
				}
			}
		}
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
