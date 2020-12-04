package com.treephones.pathfinder;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public Material startBlock = Material.EMERALD_BLOCK;
	public Material endBlock = Material.REDSTONE_BLOCK;
	public boolean started = false;
	
	@Override
	public void onEnable() {
		new Pathfinder(this);
		this.getCommand("pathfinder").setExecutor(new Commands(this));
	}
	
	@Override
	public void onDisable() {}
}
