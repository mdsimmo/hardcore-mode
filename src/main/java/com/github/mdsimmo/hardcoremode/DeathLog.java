package com.github.mdsimmo.hardcoremode;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DeathLog {

	private static final Plugin plugin = HardcoreMode.instance;
	private static final DeathLog instance = new DeathLog(); 

	private File save;
	private JSONObject data;
	
	private DeathLog() {
		save = new File( plugin.getDataFolder(), "data.txt" );
		
		// set up data file if non existent
		if ( !save.exists() ) {
			plugin.getDataFolder().mkdirs();
			JSONObject baseData = new JSONObject();
			try {
				FileWriter out = new FileWriter( save );
				baseData.writeJSONString( out );
				out.close();
			} catch( IOException e ) {
				plugin.getLogger().warning( "Unable to set up data file. Program will probably crash..." );;
			}
		}
		
		// read data
		try {
			FileReader reader = new FileReader( save );
			data = (JSONObject)new JSONParser().parse( reader );
			reader.close();
		} catch ( Exception e ) {
			plugin.getLogger().severe( "Unable to read data. Program will probably crash..." );
			e.printStackTrace();
		}
	}
	
	public void saveData() {
		try { 
			FileWriter out = new FileWriter( save );
			data.writeJSONString( out );
			out.close();
		} catch ( Exception e ) {
			plugin.getLogger().warning( "Unable to save data. Gliches may occur" );
		}
	}
	
	/**
	 * Gets the time left before the player can join again (in seconds).
	 * -1 to say banned forever; 0 to say no ban.
	 */
	public long getPlayerBanTime( UUID uuid ) {
		Object value = data.get( uuid.toString() );
		long reviveTime;
		if ( value == null ) {
			return 0;
		} else if ( value instanceof Number ) {
			reviveTime = (Long)value;
		} else {
			reviveTime = Long.valueOf( value.toString() );
		}
		
		long currentTime = Calendar.getInstance().getTimeInMillis() / 1000;
		if ( reviveTime == -1 )
			return -1;
		if ( reviveTime <= currentTime ) {
			removePlayerBan( uuid );
			return 0;
		}
		
		return reviveTime - currentTime;
	}
	
	public static long getBanTime( Player player ) {
		return instance.getPlayerBanTime( player.getUniqueId() );
	}
	
	public static long getBanTime( UUID uuid ) {
		return instance.getPlayerBanTime( uuid );
	}
	
	/**
	 * Returns true if the player is banned. False otherwise
	 */
	public boolean isPlayerBanned( UUID uuid ) {
		return getPlayerBanTime( uuid ) != 0;
	}
	
	public static boolean isBanned( Player player ) {
		return instance.isPlayerBanned( player.getUniqueId() );
	}
	
	public static boolean isBanned( UUID uuid ) {
		return instance.isPlayerBanned( uuid );
	}
	
	/**
	 * Removes a ban on a player
	 */
	public void removePlayerBan( UUID uuid ) {
		data.remove( uuid.toString() );
		saveData();
	}
	
	public static void removeBan( UUID uuid ) {
		instance.removePlayerBan( uuid );
	}
	
	public static void removeBan( Player player ) {
		instance.removePlayerBan( player.getUniqueId() );
	}
	
	/**
	 * Sets the players ban to be <code>time</code> seconds from now
	 */
	@SuppressWarnings( "unchecked" )
	public void addPlayerBan( UUID uuid, long banTime ) { 
		long reviveTime;
		if ( banTime == -1 )
			reviveTime = -1;
		else
			reviveTime = Calendar.getInstance().getTimeInMillis()/1000 + banTime;
		data.put( uuid.toString(), reviveTime );
		saveData();
	}
	
	/**
	 * Sets the ban on the player for the default amount of time
	 */
	public void addPlayerBan( UUID uuid ) {
		addPlayerBan( uuid, Settings.getDeathTime() );
	}
	
	public static void addBan( Player player ) {
		instance.addPlayerBan( player.getUniqueId() );
	}
	
	public static void addBan( UUID uuid, long time ) {
		instance.addPlayerBan( uuid, time );
	}
	
}
