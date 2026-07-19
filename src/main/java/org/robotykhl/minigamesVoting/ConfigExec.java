package org.robotykhl.minigamesVoting;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.minimessage.MiniMessage;

public class ConfigExec implements CommandExecutor {
  private MinigamesVoting main;

  public ConfigExec(MinigamesVoting main) {
    this.main = main;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
    if (sender instanceof Player) {
      if (sender.hasPermission("minigamevoting.config")) {
        if (args.length == 2) {
          List<String> minigames = main.getConfig().getStringList("Minigames");
          switch (args[0]) {
            case "add":
              minigames.add(args[1]);
              sender.sendMessage(MiniMessage.miniMessage().deserialize("Minigame '" + args[1] + "' added!"));
              break;
            case "delete":
              if (minigames.contains(args[1])) {
                minigames.remove(args[1]);
                sender.sendMessage(MiniMessage.miniMessage().deserialize("Minigame '" + args[1] + "' removed!"));
              } else {
                sender.sendMessage(MiniMessage.miniMessage().deserialize("Minigame '" + args[1] + "' doesn't exsist!"));
                return true;
              }
              break;
            default:
              sender.sendMessage(MiniMessage.miniMessage()
                  .deserialize("<red>Incorrect Usage!</red> Usage: /votingconfig add/remove optionname"));
              break;
          }
          main.getConfig().set("Minigames", minigames);
          main.saveConfig();
        } else {
          sender.sendMessage(MiniMessage.miniMessage()
              .deserialize("<red>Incorrect Usage!</red> Usage: /votingconfig add/remove optionname"));
          return true;
        }
      } else {
        sender.sendMessage(MiniMessage.miniMessage()
            .deserialize("<red>Sorry, you don't have the permission minigamevoting.config.</red>"));
      }
    } else {
      sender.sendPlainMessage("This must be sent by a player.");
    }
    return true;
  }
}
