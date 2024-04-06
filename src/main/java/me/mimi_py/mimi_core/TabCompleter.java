package me.mimi_py.mimi_core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TabCompleter implements org.bukkit.command.TabCompleter {

	private final Mimi_Core plugin;

	public TabCompleter (Mimi_Core plugin) {
		this.plugin = plugin;
	}

	@Override
	public List<String> onTabComplete (CommandSender sender, Command command, String alias, String[] args) {


		List<String> completions = new ArrayList<>();

		if (args.length == 1) {
			completions.add("set");
			completions.add("del");
			completions.add("list");

		}
		if (args.length == 2 && args[0].equalsIgnoreCase("del")) {
			Set<String> mineNames = plugin.config.getConfigurationSection("").getKeys(false);

			for (String mineName : mineNames) {
				String character = mineName.replaceAll("mine([A-Za-z])", "$1");
				completions.add(character.toUpperCase());

			}
		}
		if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
			for (char letter = 'A'; letter <= 'Z'; letter++) {
				Set<String> mineNames = plugin.config.getConfigurationSection("").getKeys(false);
				String curLetter = String.valueOf(letter);

				for (String mineName : mineNames) {
					String character = mineName.replaceAll("mine([A-Za-z])", "$1");
					if (character.equals(curLetter)) {
						curLetter = null;
					}
				}
				if (curLetter == null) continue;

				completions.add(curLetter);
			}

		}
		

		return completions;
	}
}
