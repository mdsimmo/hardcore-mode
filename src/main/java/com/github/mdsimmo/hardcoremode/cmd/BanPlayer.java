package com.github.mdsimmo.hardcoremode.cmd;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.mdsimmo.hardcoremode.DeathLog;
import com.github.mdsimmo.hardcoremode.HardcoreMode;
import com.github.mdsimmo.hardcoremode.Permission;
import com.github.mdsimmo.hardcoremode.PlayerBanner;

public class BanPlayer extends Cmd {

	public BanPlayer( Cmd parrent ) {
		super( parrent );
	}

	@Override
	public String name() {
		return "ban";
	}

	@Override
	public String description() {
		return "Bans a player for an amount of time";
	}

	@Override
	public String usage() {
		return "/" + path() + " <player> <seconds>";
	}

	@Override
	public void run( CommandSender sender, List<String> args ) {
		if ( args.size() != 2 ) {
			sender.sendMessage( usage() );
			return;
		}

		long time;
		try {
			time = Long.valueOf( args.get( 1 ) );
		} catch ( Exception e ) {
			String arg = args.get( 1 );
			if ( arg.equalsIgnoreCase( "permadeath" ) )
				time = -1;
			else if ( arg.equalsIgnoreCase( "off" ) )
				time = 0;
			else {
				sender.sendMessage( usage() );
				return;
			}
		}

		@SuppressWarnings( "deprecation" )
		OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(
				args.get( 0 ) );
		UUID uuid = offlinePlayer.getUniqueId();
		DeathLog.addBan( uuid, Long.valueOf( args.get( 1 ) ) );
		if ( DeathLog.isBanned( uuid ) ) {
			Player player = plugin.getServer().getPlayer( uuid );
			if ( player != null )
				player.kickPlayer( PlayerBanner.deathMessage( player ) );
		}

		if ( time == -1 )
			sender.sendMessage( ChatColor.YELLOW + offlinePlayer.getName()
					+ ChatColor.RESET + " is banned " + ChatColor.RED
					+ "forever" );
		else if ( time == 0 )
			sender.sendMessage( ChatColor.YELLOW + offlinePlayer.getName()
					+ ChatColor.RESET + " is revived" );
		else
			sender.sendMessage( ChatColor.YELLOW + offlinePlayer.getName()
					+ ChatColor.RESET + " is banned for " + ChatColor.AQUA
					+ HardcoreMode.format( time ) );
	}

	@Override
	public boolean hasPermission( CommandSender sender ) {
		return sender.hasPermission( Permission.OP );
	}

	@Override
	public List<String> options( CommandSender sender, List<String> args ) {
		if ( args.size() == 1 )
			return playersList();
		else if ( args.size() == 2 )
			return Arrays.asList( new String[] { "permadeath", "off" } );
		else
			return null;
	}

}
