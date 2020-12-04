package com.treephones.pathfinder;

import org.bukkit.Bukkit;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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
			Location ploc = p.getLocation();
			Location location = new Location(Bukkit.getWorld("world"), ploc.getX(), ploc.getY()-1, ploc.getZ());
			if(location.getBlock().getType() == this.plugin.startBlock) {
				this.plugin.started = true;
				sender.sendMessage(ChatColor.GREEN + "Started pathfinding...");
				this.pathfinder = new Pathfinder(this.plugin, location, this.plugin.endBlock, sender);
				this.pathfinder.moveTickEvent(null);
			}
			else {
				sender.sendMessage(ChatColor.RED + "You must be standing on the start block!");
				sender.sendMessage(p.getLocation().getBlock().getType().toString());
			}
		}
		return true;
	}

}
