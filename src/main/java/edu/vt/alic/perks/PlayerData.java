package edu.vt.alic.perks;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

/**
 * Class used to change and work with the player's perk data found in the table.
 * The data in this class is then saved to the database table when the player quits and the server shuts down.
 * 
 * @author Ali Camlibel (Lambdastic)
 * @since 05/15/16
 * @version 1.0
 */
public class PlayerData {
	
	private Player p;

	private int availablePoints;

	private int hungerLevel, damageLevel, xpLevel, luckLevel, armorLevel, moneyLevel, detectLevel, speedLevel, salvageLevel;

	/**
	 * @param p Player object used to retrieve data from the MySQL table
	 */
	public PlayerData(Player p) {
		this.p = p;

		ResultSet rs = null;

		String sql = "SELECT * FROM PlayerData WHERE player_uuid = '" + p.getUniqueId().toString() + "'";

		try {
			rs = Perks.getInstance().getMySql().getConnection().createStatement().executeQuery(sql);

			while (rs.next()) {
				availablePoints = rs.getInt(2);
				hungerLevel = rs.getInt(3);
				damageLevel = rs.getInt(4);
				xpLevel = rs.getInt(5);
				luckLevel = rs.getInt(6);
				armorLevel = rs.getInt(7);
				moneyLevel = rs.getInt(8);
				detectLevel = rs.getInt(9);
				speedLevel = rs.getInt(10);
				salvageLevel = rs.getInt(11);
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Player getPlayer() {
		return p;
	}

	public int getAvailablePoints() {
		return availablePoints;
	}

	public void setAvailablePoints(int availablePoints) {
		this.availablePoints = availablePoints;
	}

	public int getHungerLevel() {
		return hungerLevel;
	}

	public void setHungerLevel(int hungerLevel) {
		this.hungerLevel = hungerLevel;
	}

	public int getDamageLevel() {
		return damageLevel;
	}

	public void setDamageLevel(int damageLevel) {
		this.damageLevel = damageLevel;
	}

	public int getXpLevel() {
		return xpLevel;
	}

	public void setXpLevel(int xpLevel) {
		this.xpLevel = xpLevel;
	}

	public int getLuckLevel() {
		return luckLevel;
	}

	public void setLuckLevel(int luckLevel) {
		this.luckLevel = luckLevel;
	}

	public int getArmorLevel() {
		return armorLevel;
	}

	public void setArmorLevel(int armorLevel) {
		this.armorLevel = armorLevel;
	}

	public int getMoneyLevel() {
		return moneyLevel;
	}

	public void setMoneyLevel(int moneyLevel) {
		this.moneyLevel = moneyLevel;
	}

	public int getDetectLevel() {
		return detectLevel;
	}

	public void setDetectLevel(int detectLevel) {
		this.detectLevel = detectLevel;
	}

	public int getSpeedLevel() {
		return speedLevel;
	}

	public void setSpeedLevel(int speedLevel) {
		this.speedLevel = speedLevel;
	}

	public int getSalvageLevel() {
		return salvageLevel;
	}

	public void setSalvageLevel(int salvageLevel) {
		this.salvageLevel = salvageLevel;
	}

	/**
	 * @param perk Perk param used to get data
	 * @return a Perk level based on the perk enum
	 */
	public int getPerkValue(Perk perk) {
		switch (perk) {
		case HUNGER: return getHungerLevel();
		case DAMAGE: return getDamageLevel();
		case XP: return getXpLevel();
		case LUCK: return getLuckLevel();
		case ARMOR: return getArmorLevel();
		case MONEY: return getMoneyLevel();
		case DETECT: return getDetectLevel();
		case SPEED: return getSpeedLevel();
		case SALVAGE: return getSalvageLevel();
		}

		return getHungerLevel();
	}

	/**
	 * @param perk perk to set level of
	 * @param the level to set the perk of
	 */
	public void setPerkValue(Perk perk, int value) {
		switch (perk) {
		case HUNGER: setHungerLevel(value);
		case DAMAGE: setDamageLevel(value);
		case XP: setXpLevel(value);
		case LUCK: setLuckLevel(value);
		case ARMOR: setArmorLevel(value);
		case MONEY: setMoneyLevel(value);
		case DETECT: setDetectLevel(value);
		case SPEED: setSpeedLevel(value);
		case SALVAGE: setSalvageLevel(value);
		}
	}

	/**
	 * Ran when player quits and server shuts down.
	 * @return SQL statement that updates the players record given all data in the class.
	 */
	public String getSqlStatement() {
		return 	"UPDATE PlayerData SET available_points = " + getAvailablePoints() + ", "
				+ "hunger_level = " + getHungerLevel() + ", "
				+ "damage_level = " + getDamageLevel() + ", "
				+ "xp_level = " + getXpLevel() + ", "
				+ "luck_level = " + getLuckLevel() + ", "
				+ "armor_level = " + getArmorLevel() + ", "
				+ "money_level = " + getMoneyLevel() + ", "
				+ "detect_level = " + getDetectLevel() + ", "
				+ "speed_level = " + getSpeedLevel() + ", "
				+ "salvage_level = " + getSalvageLevel() + " "
				+ "WHERE player_uuid = '" + getPlayer().getUniqueId().toString() + "'";
	}
}