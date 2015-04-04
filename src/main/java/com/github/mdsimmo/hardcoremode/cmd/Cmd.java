package com.github.mdsimmo.hardcoremode.cmd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import com.github.mdsimmo.hardcoremode.HardcoreMode;

public abstract class Cmd {

	static final Plugin plugin = HardcoreMode.instance;
	protected final Cmd parent;
	
	public Cmd( Cmd parrent ) {
		this.parent = parrent;
	}
	
	/**
	 * The name of this command
	 */
	public abstract String name();
	
	/**
	 * A brief description of this command
	 * @return this commands description
	 */
	public abstract String description();
	
	/**
	 * A String to show the user when they type a command wrongly
	 * @return the commands usage. Generally looks like "/cmd a b &lt;c&gt;"
	 */
	public abstract String usage();
	
	/**
	 * Runs this command.
	 * @param sender The CommandSender that has run the command
	 * @param args The arguments that the commandSender typed. All parent command arguments will
	 * 		have been removed
	 */
	public abstract void run( CommandSender sender, List<String> args );
	
	/**
	 * Tests if <code>sender</code> has permission to use this command
	 * @param sender The CommandSender asking for permission
	 * @return true the sender can use the command. False otherwise
	 */
	public abstract boolean hasPermission( CommandSender sender );
	
	/**
	 * The options to give to <code>sender</code> when he has typed <code>args</code>
	 * @param sender the ComandSender writing the command
	 * @param the arguments he has typed
	 * @return a List of valid strings he can type
	 */
	public abstract List<String> options( CommandSender sender, List<String> args );
	
	/**
	 * The path of this command. Includes this command's name
	 * @return a string that looks like "basecommand a b c thiscommand"
	 */
	public String path() {
		return parent.path() + ' ' + name();
	}
	
	public static List<String> playersList() {
		List<String> list = new ArrayList<String>();
		for ( OfflinePlayer player : plugin.getServer().getOfflinePlayers()) {
			if ( player.getName() != null )
				list.add( player.getName() );
		}
		return list;
	}
	
}
