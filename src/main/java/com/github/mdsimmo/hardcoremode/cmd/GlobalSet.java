package com.github.mdsimmo.hardcoremode.cmd;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.github.mdsimmo.hardcoremode.HardcoreMode;
import com.github.mdsimmo.hardcoremode.Permission;
import com.github.mdsimmo.hardcoremode.Settings;

public class GlobalSet extends Cmd {

	public GlobalSet( Cmd parent ) {
		super( parent );
	}

	@Override
	public String name() {
		return "set";
	}

	@Override
	public String description() {
		return "Sets the time that players are dead for";
	}

	@Override
	public String usage() {
		return "/" + path() + " <time>";
	}

	@Override
	public void run( CommandSender sender, List<String> args ) {

		if ( args.size() != 1 ) {
			sender.sendMessage( usage() );
			return;
		}

		long time = 0;
		try {
			time = Long.valueOf( args.get( args.size() - 1 ) );
		} catch ( Exception e ) {
			sender.sendMessage( usage() );
		}

		Settings.setDeathTime( time );
		sender.sendMessage( getTimeMessage( time ) );
	}

	private String getTimeMessage( long time ) {
		if ( time == -1 )
			return ChatColor.RED + "Permadeath!";
		else if ( time == 0 )
			return "No death time";
		else
			return "Death time set to " + ChatColor.AQUA + HardcoreMode.format( time );
	}

	@Override
	public boolean hasPermission( CommandSender sender ) {
		return sender.hasPermission( Permission.OP );
	}

	@Override
	public List<String> options( CommandSender sender, List<String> args ) {
		return null;
	}

}
