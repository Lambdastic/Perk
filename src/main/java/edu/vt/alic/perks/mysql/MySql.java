package edu.vt.alic.perks.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.configuration.file.FileConfiguration;

import edu.vt.alic.perks.Perks;

/**
 * Class used to establish the connection to the MySQL server.
 * Creates the table if the table doesn't exist.
 * Gets the database information from the config file.
 * 
 * @author Ali Camlibel (Lambdastic)
 * @since 05/15/16
 * @version 1.0
 */
public class MySql {
	
	private Connection connection;
	private String host, database, username, password;
	private int port;

	/**
	 * All info in the config file.
	 */
	public MySql() {
		FileConfiguration config = Perks.getInstance().getConfig();

		this.host = config.getString("db.host");
		this.port = config.getInt("db.port");
		this.database = config.getString("db.database");
		this.username = config.getString("db.username");
		this.password = config.getString("db.password");
	}

	/**
	 * Opens the connection based on config file information.
	 * @throws SQLException thrown when database is not open or info is wrong.
	 * @throws ClassNotFoundException thrown when the com.mysql.jdbc.Driver class is not found.
	 */
	public void openConnection() throws SQLException, ClassNotFoundException {
		if (connection != null && !connection.isClosed())
			return;

		synchronized (this) {
			if (connection != null && !connection.isClosed())
				return;

			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database
					, this.username, this.password);
		}
	}

	/**
	 * Creates the table if it doesn't exist.
	 * I don't set the player_uuid column as a primary key because we have no other tables.
	 */
	public void createTableIfNecessary() {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(
					"CREATE TABLE IF NOT EXISTS PlayerData ("
							+ "player_uuid VARCHAR(200) NOT NULL, "
							+ "available_points INT DEFAULT 0, "
							+ "hunger_level INT DEFAULT 0, "
							+ "damage_level INT DEFAULT 0, "
							+ "xp_level INT DEFAULT 0, "
							+ "luck_level INT DEFAULT 0, "
							+ "armor_level INT DEFAULT 0, "
							+ "money_level INT DEFAULT 0, "
							+ "detect_level INT DEFAULT 0, "
							+ "speed_level INT DEFAULT 0, "
							+ "salvage_level INT DEFAULT 0)");
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the connection used in other parts of the code to send out statements when the player quits or server shuts.
	 */
	public Connection getConnection() {
		return connection;
	}
}
