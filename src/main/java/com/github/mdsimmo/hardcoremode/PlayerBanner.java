package com.github.mdsimmo.hardcoremode;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

public class PlayerBanner implements Listener {

	private static final Plugin plugin = HardcoreMode.instance;
	
	public PlayerBanner() {
		plugin.getServer().getPluginManager().registerEvents( this, plugin );
	}
	
	@EventHandler
	public void onPlayerDeath( PlayerDeathEvent e ) {
		Player player = e.getEntity();
		
		// don't ban ops
		if ( player.hasPermission( Permission.OP ) && !Settings.getBanOps() )
			return;
		
		DeathLog.addBan( player );
		// kicking is done when they leave or on re-spawn
		
	}
	
	@EventHandler
	public void onPlayerRespawn( PlayerRespawnEvent e ) {
		Player player = e.getPlayer();
		if ( DeathLog.isBanned( player ))
			player.kickPlayer( deathMessage( player ) );
	}

	@EventHandler
	public void onPlayerLogin( PlayerLoginEvent e ) {
		Player player = e.getPlayer();
		if ( DeathLog.isBanned( player ))
			e.disallow( Result.KICK_BANNED, deathMessage( player ) );
	}
	
	public static String deathMessage( Player player ) {
		long time = DeathLog.getBanTime( player );
		if ( time == -1 )
			return "You're dead FOREVER!";
		else
			return "You're dead!\n"
				+ "Respawn in " + HardcoreMode.format( time );
		
	}
	
}
