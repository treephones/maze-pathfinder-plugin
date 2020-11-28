package com.treephones.pathfinder;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class Pathfinder extends BukkitRunnable {
	
	Location start;
	Material end;
	ArrayList<Location> path;
	CommandSender sender;
	
	public Pathfinder(Location startLocation, Material endBlock, CommandSender sender) {
		this.start = this.cleanLocation(startLocation);
		this.end = endBlock;
		this.path = new ArrayList<Location>();
		this.sender = sender;
	}
	
	@Override
	public void run() {
		try {
			this.pathfinder(this.start, new ArrayList<Location>());
			for(Location pathPos : this.reverse(this.path)) {
				pathPos.getBlock().setType(Material.BLUE_WOOL);
			}
			this.sender.sendMessage(ChatColor.GREEN + "Solution to maze found!");
		}
		catch(Exception e) {
			sender.sendMessage(ChatColor.RED + "Something went wrong!");
		}
	}
	
	//dfs pathfinding
	public boolean pathfinder(Location position, ArrayList<Location> explored) {
		if(this.getBlocksAtCoords(this.getSurroundingCoords(position)).contains(Material.EMERALD_BLOCK)) {
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
		for(Location l : s) {
			if(new Location(world, l.getX(), l.getY()+1, l.getZ()).getBlock().getType() != Material.AIR) {
				s.remove(l);
			}
		}
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
