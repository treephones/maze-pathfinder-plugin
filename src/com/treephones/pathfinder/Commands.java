package com.treephones.pathfinder;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	
	Main plugin;
	Pathfinder pathfinder;
	
	public Commands(Main p) {
		this.plugin = p;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		Player p = (Player)sender;
		if(args[0].equalsIgnoreCase("start")) {
			if(p.getLocation().getBlock().getType() == this.plugin.startBlock) {
				this.pathfinder = new Pathfinder(p.getLocation(), this.plugin.endBlock, sender);
				sender.sendMessage(ChatColor.GREEN + "Started pathfinding...");
				this.pathfinder.run();
			}
			else {
				sender.sendMessage(ChatColor.RED + "You must be standing on the start block!");
			}
		}
		return true;
	}

}
