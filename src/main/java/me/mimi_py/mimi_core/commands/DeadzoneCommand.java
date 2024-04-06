package me.mimi_py.mimi_core.commands;

import me.mimi_py.mimi_core.Mimi_Core;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Set;

public class DeadzoneCommand implements CommandExecutor {

	private final Mimi_Core plugin;

	public DeadzoneCommand (Mimi_Core plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand (CommandSender sender, Command command, String s, String[] args) {

		if (!(sender instanceof Player)) return true;

		Player player = ((Player) sender).getPlayer();


		assert player != null;

		if (args.length < 1) {
			player.sendMessage(ChatColor.YELLOW + "/Deadzone " + ChatColor.RED + "Del " + ChatColor.YELLOW + ChatColor.UNDERLINE +
					"OR" + ChatColor.RESET + ChatColor.GREEN +
					" Set A-Z " +
					"Radius");
			return true;
		}

		if (args.length < 2 && args[0].equals("del")) {

			player.sendMessage(ChatColor.YELLOW + "/Deadzone Del A-Z");
			return true;
		}
		if (args[0].equals("list")) {

			Set<String> mineNames = Objects.requireNonNull(plugin.config.getConfigurationSection("")).getKeys(false);
			if (mineNames.size() < 1) {
				player.sendMessage(ChatColor.GRAY + "No Deadzone Found");
			}
			for (String mineName : mineNames) {
				player.sendMessage(mineName);
			}

			return true;
		}
		if (args[0].equalsIgnoreCase("del")) {
			if (plugin.config.get("mine" + args[1].toUpperCase()) == null) {
				player.sendMessage(ChatColor.RED + String.format("mine%S", args[1]) + " Does Not Exist " + ChatColor.YELLOW + ChatColor.UNDERLINE +
						"/Deadzone List" + ChatColor.RESET + ChatColor.RED +
						" To See All Available Zones");
				return true;
			}
			plugin.config.set("mine" + args[1].toUpperCase(), null);

			plugin.saveConfig();
			player.sendMessage(ChatColor.GREEN + String.format("mine%S", args[1]) +
					" Successfully " +
					"Removed!");
			return true;
		}

		if (args.length < 3) {

			player.sendMessage(ChatColor.YELLOW + "/Deadzone Set A-Z Radius");
			return true;
		}


		if (!args[1].matches("[a-zA-Z]") || args[1].length() > 1) {

			player.sendMessage(ChatColor.YELLOW + "/Deadzone Set A-Z Radius");
			return true;
		}

		if (!args[2].matches("\\d+")) {

			player.sendMessage(ChatColor.YELLOW + "/Deadzone Set A-Z Radius");
			return true;
		}

		if (args[0].equalsIgnoreCase("set")) {


			Location location = player.getLocation();


			// Setting the dead zone location and radius in the config
			plugin.config.set("mine" + args[1].toUpperCase() + ".location", location);
			plugin.config.set("mine" + args[1].toUpperCase() + ".radius", args[2]);
			plugin.config.set("mine" + args[1].toUpperCase() + ".permissions", "mimi.Mine" + args[1]);

			// Saving the config
			plugin.saveConfig();

			player.sendMessage(ChatColor.AQUA + "Deadzone Set To " +
					ChatColor.RED + ChatColor.UNDERLINE + "X:" + ChatColor.RESET + ChatColor.RED + ChatColor.UNDERLINE + ChatColor.BOLD + Math.floor(location.getX()) +
					ChatColor.AQUA + " " +
					ChatColor.GREEN + ChatColor.UNDERLINE + "Y:" + ChatColor.RESET + ChatColor.GREEN + ChatColor.UNDERLINE + ChatColor.BOLD + Math.floor(location.getY()) +
					ChatColor.AQUA + " " +
					ChatColor.BLUE + ChatColor.UNDERLINE + "Z:" + ChatColor.RESET + ChatColor.BLUE + ChatColor.UNDERLINE + ChatColor.BOLD + Math.floor(location.getZ()));


			return true;
		}
		player.sendMessage(ChatColor.YELLOW + "/Deadzone " + ChatColor.RED + "Del " + ChatColor.YELLOW + ChatColor.UNDERLINE +
				"OR" + ChatColor.RESET + ChatColor.GREEN +
				" Set A-Z " +
				"Radius");

		return true;
	}
}
