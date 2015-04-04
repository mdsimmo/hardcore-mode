package com.github.mdsimmo.hardcoremode;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.mdsimmo.hardcoremode.cmd.BaseCommand;

public class HardcoreMode extends JavaPlugin {

	public static HardcoreMode instance;

	public HardcoreMode() {
		instance = this;
	}

	@Override
	public void onEnable() {
		super.onEnable();
		new PlayerBanner();
		new BaseCommand();
	}

	public static String format( long seconds ) {
		int hours = (int)( seconds / ( 60 * 60 ) );
		int mins = (int)( seconds / 60 - hours * 60 );
		int secs = (int)( seconds % 60 );
		return  hours + "h " + mins + "m " + secs + "s" ;
	}
}
