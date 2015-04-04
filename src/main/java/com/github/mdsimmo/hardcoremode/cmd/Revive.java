package com.github.mdsimmo.hardcoremode.cmd;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import com.github.mdsimmo.hardcoremode.DeathLog;
import com.github.mdsimmo.hardcoremode.Permission;

public class Revive extends Cmd {

	public Revive( Cmd parrent ) {
		super( parrent );
	}

	@Override
	public String name() {
		return "revive";
	}

	@Override
	public String description() {
		return "brings a player back from the death";
	}

	@Override
	public String usage() {
		return "/" + path() + " <player>";
	}

	@Override
	public void run( CommandSender sender, List<String> args ) {
		if ( args.size() != 1 ) {
			sender.sendMessage( usage() );
			return;
		}
		
		@SuppressWarnings( "deprecation" )
		OfflinePlayer player = plugin.getServer().getOfflinePlayer( args.get( 0 ) );
		if ( !DeathLog.isBanned( player.getUniqueId() )) {
			sender.sendMessage( player.getName() + " was not dead" );
		} else {
			DeathLog.removeBan( player.getUniqueId() );
			sender.sendMessage( player.getName() + " revived" );
			
		}
		
	}

	@Override
	public boolean hasPermission( CommandSender sender ) {
		return sender.hasPermission( Permission.OP );
	}

	@Override
	public List<String> options( CommandSender sender, List<String> args ) {
		if ( args.size() == 1 )
			return playersList();
		else
			return null;
	}

}
