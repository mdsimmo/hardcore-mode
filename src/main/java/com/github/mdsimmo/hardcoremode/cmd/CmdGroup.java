package com.github.mdsimmo.hardcoremode.cmd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

public abstract class CmdGroup extends Cmd {

	private Cmd[] children = new Cmd[0];
	
	public CmdGroup( Cmd parrent ) {
		super( parrent );
	}
	
	protected void setChildren( Cmd[] childern ) {
		this.children = childern;
	}

	@Override
	public String usage() {
		return "/"+path() + " <sub commands>" ;
	}
	
	@Override
	public boolean hasPermission( CommandSender sender ) {
		for ( Cmd cmd : children ) {
			if ( cmd.hasPermission( sender ))
				return true;
		}
		return false;
	}
	
	@Override
	public void run( CommandSender sender, List<String> args ) {
		if ( args.size() == 0 ) {
			sender.sendMessage( "This command contains:" );
			for ( Cmd cmd : children ) {
				if ( cmd.hasPermission( sender ))
					sender.sendMessage( "  " + cmd.name() );
			}
		} else {
			for ( Cmd cmd : children ) {
				if ( cmd.name().equalsIgnoreCase( args.get( 0 ) )) {
					if ( !cmd.hasPermission( sender ) ) {
						sender.sendMessage( usage() );
						return;
					}
					args.remove( 0 );
					cmd.run( sender, args );
					return;
				}
			}
			sender.sendMessage( usage() );
		}
	}
	
	@Override
	public List<String> options( CommandSender sender, List<String> args ) {
		Cmd cmd = getCommand( sender, args );
		if ( cmd != this )
			return cmd.options( sender, args );

		String arg = args.size() == 0 ? "" : args.get( 0 );
		List<String> options = new ArrayList<String>();
		for ( Cmd c : children ) {
			if ( StringUtil.startsWithIgnoreCase( c.name(), arg )
					&& c.hasPermission( sender ))
				options.add( c.name() );
		}
		return options;
	}
	
	private Cmd getCommand( CommandSender sender, List<String> args ) {
		if ( args.size() == 0 )
			return this;
		for ( Cmd cmd : children ) {
			if ( cmd.name().equalsIgnoreCase( args.get( 0 ) )) {
				if ( cmd.hasPermission( sender ) ) {
					args.remove( 0 );
					if ( cmd instanceof CmdGroup )
						return ( (CmdGroup)cmd ).getCommand( sender, args );
					return cmd;
				}
				return this;
			}
		}
		return this;
	}
}
