package org.robotykhl.minigamesVoting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class ConfigTab implements TabCompleter {

  MinigamesVoting main;

  public ConfigTab(MinigamesVoting main) {
    this.main = main;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
    List<String> results = new ArrayList<>();

    if (args.length == 1) {
      results.add("add");
      results.add("delete");
    } else if (args.length == 2) {
      if (args[0].equals("add")) {
        results.add("Type What To Add");
      } else if (args[0].equals("delete")) {
        for (String current : main.getConfig().getStringList("Minigames")) {
          results.add(current);
        }
      }
    }

    return results;
  }
}
