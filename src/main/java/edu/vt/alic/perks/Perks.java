package edu.vt.alic.perks;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import edu.vt.alic.perks.commands.PerksCommand;
import edu.vt.alic.perks.listeners.InventoryClickListener;
import edu.vt.alic.perks.listeners.PlayerJoinListener;
import edu.vt.alic.perks.listeners.PlayerLevelUpListener;
import edu.vt.alic.perks.listeners.PlayerQuitListener;
import edu.vt.alic.perks.listeners.ZombieDeathListener;
import edu.vt.alic.perks.mysql.MySql;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

/**
 * Spigot plugin sets up a MySQL connection and stores levels for each available perk.
 * Works off of memory rather than constantly sending data to the database server.
 * 
 * @author Ali Camlibel (Lambdastic)
 * @since 05/15/16
 * @version 1.0
 */
public class Perks extends JavaPlugin {

	private static Perks plugin;
	
	private MySql mySql;
	private HashMap<UUID, PlayerData> playerData;
	private Economy econ;


	/**
	 * @return instance of the main class.
	 */
	public static Perks getInstance() {
		return plugin;
	}

	/**
	 * Called on plugin startup.
	 * Sets up listeners, commands, the MySQL connection, the config file, and the economy using private helper methods.
	 * Disables plugin if Vault is not present.
	 * Instantiates a HashMap object with the type arguments <UUID, PlayerData> to work with player data on memory
	 * rather than constantly sending out Statements to the MySQL connection.
	 */
	@Override
	public void onEnable() {
		plugin = this;
		playerData = new HashMap<UUID, PlayerData>();

		setupConfig();
		setupMySql();
		setupListeners();
		setupCommands();
		
		if (!setupEconomy() ) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
	}

	/**
	 * Called on plugin shutdown.
	 * Updates all players' data in the MySQL table given their data in their corresponding PlayerData object
	 * in the HashMap.
	 */
	@Override
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			PlayerData data = playerData.get(p.getUniqueId());

			try {
				Statement statement = getMySql().getConnection().createStatement();
				statement.executeUpdate(data.getSqlStatement());
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		playerData.clear();
	}
	
	/**
	 * Vault's premade method on their GitHub to setup the economy bridge.
	 * @return true/false based on if Vault is installed and loaded.
	 */
	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) 
			return false;
		
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) 
			return false;
		
		econ = rsp.getProvider();
		return econ != null;
	}

	/**
	 * The method createTableIfNecessary is for when the table is not created.
	 * Method won't do anything after the first time the plugin runs because the table will have been created
	 * after the first time.
	 */
	private void setupMySql() {
		mySql = new MySql();

		try {
			mySql.openConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		mySql.createTableIfNecessary();
	}

	/**
	 * Register all listeners
	 */
	private void setupListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerJoinListener(), this);
		pm.registerEvents(new PlayerLevelUpListener(), this);
		pm.registerEvents(new PlayerQuitListener(), this);
		pm.registerEvents(new InventoryClickListener(), this);
		pm.registerEvents(new ZombieDeathListener(), this);
	}
	
	/**
	 * Register the /perks command.
	 */
	private void setupCommands() {
		getCommand("perks").setExecutor(new PerksCommand());
	}

	/**
	 * Sets up the config file.
	 */
	private void setupConfig() {
		getConfig().getDefaults();
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	/**
	 * @return MySql object used to send Statements out to the connection. Used in many parts of the code.
	 */
	public MySql getMySql() {
		return mySql;
	}
	
	/**
	 * @return Vault's economy object used to deposit money.
	 */
	public Economy getEconomy() {
		return econ;
	}

	/**
	 * @return all playerData
	 */
	public HashMap<UUID, PlayerData> getPlayerData() {
		return playerData;
	}

	/**
	 * @param playerData used to change the inventory so that available points can be unique.
	 * @return Inventory object with a custom name that changes based on the player's available points.
	 */
	public Inventory getPerksGui(PlayerData playerData) {
		return Bukkit.createInventory(null, 27, ChatColor.DARK_RED + "Perks - Available Points: "
				+ playerData.getAvailablePoints());
	}
}
