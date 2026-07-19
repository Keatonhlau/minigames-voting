package org.robotykhl.minigamesVoting;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CommandTab implements TabCompleter {

  @Override
  public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
    if (args.length == 1) {
      return List.of("Secconds To Wait");
    } else {
      return List.of();
    }
  }
}
