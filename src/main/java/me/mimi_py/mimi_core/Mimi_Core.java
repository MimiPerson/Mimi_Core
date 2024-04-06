package me.mimi_py.mimi_core;

import me.mimi_py.mimi_core.commands.DeadzoneCommand;
import me.mimi_py.mimi_core.listeners.PlayerBreakDeadZoneListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public final class Mimi_Core extends JavaPlugin {

	public FileConfiguration config;
	private File configFile;

	@Override
	public void onEnable () {

		// Create config file if it doesn't exist
		configFile = new File(getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			configFile.getParentFile().mkdirs();
			saveResource("config.yml", false);
		}

		config = YamlConfiguration.loadConfiguration(configFile);

		System.out.println("Mimi Plugin Running");

		getServer().getPluginManager().registerEvents(new PlayerBreakDeadZoneListener(this), this);

		getCommand("Deadzone").setExecutor(new DeadzoneCommand(this));
		getCommand("Deadzone").setTabCompleter(new TabCompleter(this));

	}

	public void onDisable () {
		saveConfig();
		System.out.println("saving Mimi");
	}

	public void saveConfig () {
		try {
			config.save(configFile);
			System.out.println("Saving Mimi's Config to " + configFile.getName());
		} catch (IOException e) {
			getLogger().warning("Could not save config to " + configFile.getName());
		}
	}

}

