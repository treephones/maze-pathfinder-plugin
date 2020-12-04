package com.treephones.pathfinder;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;

public class Pathfinder implements Listener {
	
	Main plugin;
	Location start;
	Material end;
	ArrayList<Location> path = new ArrayList<Location>();;
	int indexCache = 0;
	CommandSender sender;
	Player player;
	Location trigger;
	
	public Pathfinder(Main plugin) {
		//init
	}
	
	public Pathfinder(Main plugin, Location startLocation, Material endBlock, CommandSender sender) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
		this.start = this.cleanLocation(startLocation);
		this.end = endBlock; 
		this.sender = sender;
		this.player = (Player)sender;
		this.trigger = new Location(Bukkit.getWorld("world"), 791, 4, 2351);
		try {
			if(this.plugin.started) {
				this.pathfinder(this.start, new ArrayList<Location>());
				this.path = this.reverse(this.path);
				this.sender.sendMessage(ChatColor.GREEN + "Solution to maze found!");
			}
		}
		catch(Exception e) {
			sender.sendMessage(ChatColor.RED + "Something went wrong!");
		}
	}

	@EventHandler
	public void moveTickEvent(BlockPistonExtendEvent e) {
		this.sender.sendMessage(ChatColor.BLUE + "Moved.");
		if(this.plugin.started && this.indexCache < this.path.size()) {
			Location pathPos = this.path.get(this.indexCache);
			Location player_pathpos = new Location(Bukkit.getWorld("world"), pathPos.getX(), pathPos.getY()+1, pathPos.getZ());
			pathPos.getBlock().setType(Material.BLUE_WOOL);
			this.player.teleport(player_pathpos);
			++this.indexCache;
			
			this.trigger.getBlock().setType(Material.REDSTONE_BLOCK);
			this.trigger.getBlock().setType(Material.AIR);
		}
		else {
			this.sender.sendMessage(ChatColor.GREEN + "You are at the end of the maze!");
		}
	}
	
	//dfs pathfinding
	public boolean pathfinder(Location position, ArrayList<Location> explored) {
		if(this.getBlocksAtCoords(this.getSurroundingCoords(position)).contains(this.end)) {
			explored.add(position);
			return true;
		}
		else if(explored.contains(position)) {
			return false;
		}
		explored.add(position);
		for(Location surPosition : this.getSurroundingCoords(position)) {
			if(!explored.contains(surPosition) && this.pathfinder(surPosition, explored)) {
				this.path.add(surPosition);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Location> getSurroundingCoords(Location p) {
		double x = (int)p.getX();
		double y = (int)p.getY();
		double z = (int)p.getZ();
		World world = Bukkit.getWorld("world");
		ArrayList<Location> s = new ArrayList<Location>();
		//defaults
		s.add(new Location(world, x+1, y, z));
		s.add(new Location(world, x-1, y, z));
		s.add(new Location(world, x, y, z+1));
		s.add(new Location(world, x, y, z-1));
		//make sure is a path
		List<Location> remove = new ArrayList<Location>();
		for(Location l : s) {
			if(new Location(world, l.getX(), l.getY()+1, l.getZ()).getBlock().getType() != Material.AIR) {
				remove.add(l);
			}
		}
		s.removeAll(remove);
		return s;
	}
	
	public ArrayList<Material> getBlocksAtCoords(ArrayList<Location> coords) {
		ArrayList<Material> blocks = new ArrayList<Material>();
		for(Location location : coords) {
			blocks.add(location.getBlock().getType());
		}
		return blocks;
	}
	
	public ArrayList<Location> reverse(ArrayList<Location> arr) {
		Location temp;
	    for(int i = 0; i < arr.size()/2; ++i) {
	        temp = arr.get(i);
	        arr.set(i, arr.get(arr.size()-1-i));
	        arr.set(arr.size()-1-i, temp);
	    }
	    return arr;
	}
	
	public Location cleanLocation(Location l) {
		return new Location(Bukkit.getWorld("world"), (int)l.getX(), l.getY(), l.getZ());
	}
	
}
