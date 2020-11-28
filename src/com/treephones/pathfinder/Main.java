package com.treephones.pathfinder;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public Material startBlock = Material.EMERALD_BLOCK;
	public Material endBlock = Material.REDSTONE_BLOCK;
	
	@Override
	public void onEnable() {
		this.getCommand("pathfinder").setExecutor(new Commands(this));
	}
	
	@Override
	public void onDisable() {}
}
