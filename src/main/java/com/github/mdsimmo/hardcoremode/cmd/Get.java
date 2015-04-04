package com.github.mdsimmo.hardcoremode.cmd;

import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import com.github.mdsimmo.hardcoremode.DeathLog;
import com.github.mdsimmo.hardcoremode.HardcoreMode;
import com.github.mdsimmo.hardcoremode.Permission;
import com.github.mdsimmo.hardcoremode.Settings;

public class Get extends Cmd {

	public Get( Cmd parrent ) {
		super( parrent );
	}

	@Override
	public String name() {
		return "get";
	}

	@Override
	public String description() {
		return "Gets the time that players are dead for";
	}

	@Override
	public String usage() {
		return "/" + path() + " [player]";
	}

	@Override
	public void run( CommandSender sender, List<String> args ) {
		if ( args.size() > 1 ) {
			sender.sendMessage( usage() );
			return;
		}

		if ( args.size() == 1 ) {
			@SuppressWarnings( "deprecation" )
			OfflinePlayer player = plugin.getServer().getOfflinePlayer(
					args.get( 0 ) );
			UUID uuid = player.getUniqueId();
			long time = DeathLog.getBanTime( uuid );
			if ( time == -1 )
				sender.sendMessage( ChatColor.YELLOW + player.getName()
						+ ChatColor.RESET + " is dead " + ChatColor.RED
						+ "forever" );
			else if ( time == 0 )
				sender.sendMessage( ChatColor.YELLOW + player.getName()
						+ ChatColor.RESET + " is not dead" );
			else
				sender.sendMessage( ChatColor.YELLOW + player.getName()
						+ ChatColor.RESET + " is dead for " + ChatColor.AQUA
						+ HardcoreMode.format( time ) );
			return;
		}

		long time = Settings.getDeathTime();
		if ( time == -1 )
			sender.sendMessage( "Death is " + ChatColor.RED + "permanent!" );
		else if ( time == 0 )
			sender.sendMessage( "No death time" );
		else
			sender.sendMessage( "Death time is for " + ChatColor.AQUA
					+ HardcoreMode.format( time ) );
	}

	@Override
	public boolean hasPermission( CommandSender sender ) {
		return sender.hasPermission( Permission.PLAYER );
	}

	@Override
	public List<String> options( CommandSender sender, List<String> args ) {
		if ( args.size() == 1 )
			return playersList();
		else
			return null;
	}

}
