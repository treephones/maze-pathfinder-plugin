package com.treephones.pathfinder;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
	
	Main plugin;
	
	public Commands(Main p) {
		this.plugin = p;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if(args[0].equalsIgnoreCase("start")) {
			
		}
		return true;
	}

}
