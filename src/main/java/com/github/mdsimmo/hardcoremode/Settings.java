package com.github.mdsimmo.hardcoremode;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Settings {

	private static Plugin plugin = HardcoreMode.instance;
	private static final String DEATH_TIME = "death-time";
	private static final String BAN_OPS = "ban-ops";
	
	static {
		setDefaults();
	}
	
	public static long getDeathTime() {
		return plugin.getConfig().getLong( DEATH_TIME );
	}
	
	public static void setDeathTime( long value) {
		plugin.getConfig().set( DEATH_TIME, value );
		plugin.saveConfig();
	}
	
	public static boolean getBanOps() {
		return plugin.getConfig().getBoolean( BAN_OPS );
	}
	
	public static void setBanOps( boolean doBan) {
		plugin.getConfig().set( BAN_OPS, doBan );
		plugin.saveConfig();
	}
	
	public static void setDefaults() {
		FileConfiguration config = plugin.getConfig();
		config.addDefault( DEATH_TIME, 600 );
		config.addDefault( BAN_OPS, true );
		config.options().copyDefaults( true );
		plugin.saveConfig();
	}
	
}
