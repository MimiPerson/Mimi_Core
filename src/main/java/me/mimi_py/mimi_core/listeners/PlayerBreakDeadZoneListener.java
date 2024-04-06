package me.mimi_py.mimi_core.listeners;

import me.mimi_py.mimi_core.Mimi_Core;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Objects;
import java.util.Set;

public class PlayerBreakDeadZoneListener implements Listener {

	private final Mimi_Core plugin;

	public PlayerBreakDeadZoneListener (Mimi_Core plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEvent (BlockBreakEvent event) {


		Location location = plugin.config.getLocation("mineA.location");
		String deadZoneRadius = plugin.config.getString("mineA.radius");

		Block block = event.getBlock();

		Player player = event.getPlayer();

		if (isInDeadZone(block.getLocation(), player)) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "You can't mine here!");

		}


	}

	private boolean isInDeadZone (Location blockLocation, Player player) {
		ConfigurationSection section = plugin.config.getConfigurationSection("");
		assert section != null;
		Set<String> mineNames = section.getKeys(false);


		for (String mineName : mineNames) {
			Location mineLocation = plugin.config.getLocation(mineName + ".location");
			int radius = Integer.parseInt(Objects.requireNonNull(plugin.config.getString(mineName + ".radius")));
			assert mineLocation != null;
			if (mineLocation.distanceSquared(blockLocation) <= radius * radius && !player.hasPermission("mimi." + mineName)) {
				return true;
			}

		}


		return false;

	}

}

