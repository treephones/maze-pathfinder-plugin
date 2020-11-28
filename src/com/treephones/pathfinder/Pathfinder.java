package com.treephones.pathfinder;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class Pathfinder extends BukkitRunnable {
	
	Location start;
	Material end;
	ArrayList<Location> paths;
	
	public Pathfinder(Location startLocation, Material endBlock) {
		this.start = startLocation;
		this.end = endBlock;
		this.paths = new ArrayList<Location>();
	}
	
	@Override
	public void run() {
		
	}
	
	//dfs
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
		s.add(new Location(world, x+1, y, z));
		s.add(new Location(world, x-1, y, z));
		s.add(new Location(world, x, y, z+1));
		s.add(new Location(world, x, y, z-1));
		return s;
	}
	
	public ArrayList<Material> getBlocksAtCoords(ArrayList<Location> coords) {
		ArrayList<Material> blocks = new ArrayList<Material>();
		for(Location location : coords) {
			blocks.add(location.getBlock().getType());
		}
		return blocks;
	}
	
}
