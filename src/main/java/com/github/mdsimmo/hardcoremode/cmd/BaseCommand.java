package com.github.mdsimmo.hardcoremode.cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import com.github.mdsimmo.hardcoremode.HardcoreMode;
import com.github.mdsimmo.hardcoremode.Permission;

public class BaseCommand extends CmdGroup implements CommandExecutor,
		TabCompleter {

	public BaseCommand() {
		super( null );
		setChildren( new Cmd[] {
				new GlobalSet( this ),
				new Get( this ),
				new BanPlayer( this ),
				new Revive( this ),
		});
		HardcoreMode.instance.getCommand( name() ).setExecutor( this );
		HardcoreMode.instance.getCommand( name() ).setTabCompleter( this );
	}

	@Override
	public String name() {
		return "hardcore";
	}

	@Override
	public String description() {
		return "All Hardcore commands";
	}

	@Override
	public boolean hasPermission( CommandSender sender ) {
		return sender.hasPermission( Permission.PLAYER );
	}

	@Override
	public String path() {
		return name();
	}

	@Override
	public List<String> onTabComplete( CommandSender sender, Command command,
			String alias, String[] args ) {
		List<String> options = options( sender, new ArrayList<String>( Arrays.asList( args ) ) );
		if ( options == null )
			return new ArrayList<String>(1);
		
		// filter options that don't match what was typed
		List<String> viableOptions = new ArrayList<String>();
		for ( String option : options ) {
			if ( StringUtil.startsWithIgnoreCase( option, args[args.length-1] ) )
				viableOptions.add( option );
		}
		return options;
	}

	@Override
	public boolean onCommand( CommandSender sender, Command command,
			String label, String[] args ) {
		run( sender, new ArrayList<String>( Arrays.asList( args ) ) );
		return true;
	}

}
