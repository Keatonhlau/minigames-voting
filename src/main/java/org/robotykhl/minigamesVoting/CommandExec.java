package org.robotykhl.minigamesVoting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class CommandExec implements CommandExecutor {

  private final MinigamesVoting main;

  public CommandExec(MinigamesVoting main) {
    this.main = main;
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
    Map<String, Integer> votes = new HashMap<>();
    if (commandSender.hasPermission("minigamevoting.callvote")) {
      int secconds;
      try {
        secconds = Integer.parseInt(strings[0]);
      } catch (NumberFormatException e) {
        secconds = main.getConfig().getInt("Default_Time");
      }
      final int seccondstowait = secconds;
      for (Player player : Bukkit.getOnlinePlayers()) {
        List<ActionButton> buttons = new ArrayList<>();

        buttons.add(ActionButton.create(
            MiniMessage.miniMessage().deserialize("Skip"),
            MiniMessage.miniMessage().deserialize("Vote to skip this round"),
            150,
            DialogAction.customClick((view, audience) -> {
              player.closeDialog();
            }, ClickCallback.Options.builder().build())));

        for (String string : main.getConfig().getStringList("Minigames")) {
          buttons.add(ActionButton.create(MiniMessage.miniMessage().deserialize(string),
              MiniMessage.miniMessage().deserialize("Click to Vote"), 150,
              DialogAction.customClick((view, audience) -> {
                if (votes.containsKey(string)) {
                  votes.put(string, votes.get(string) + 1);
                } else {
                  votes.put(string, 1);
                }
                player.closeDialog();
              }, ClickCallback.Options.builder().build())));
        }
        Dialog dialog = Dialog.create(builder -> builder.empty()
            .base(DialogBase.builder(
                MiniMessage.miniMessage().deserialize("<rainbow>Minigame</rainbow> Voting"))
                .body(List.of(
                    DialogBody.plainMessage(
                        MiniMessage.miniMessage().deserialize(
                            seccondstowait + " secconds to vote from the time the ui opens."))))
                .build())

            .type(DialogType.multiAction(buttons, null, 2)));
        player.showDialog(dialog);

      }
      BukkitScheduler scheduler = main.getServer().getScheduler();
      scheduler.runTaskLater(main, () -> {
        for (Player player : Bukkit.getOnlinePlayers()) {
          player.closeDialog();
        }
        Bukkit.broadcast(MiniMessage.miniMessage().deserialize("[<rainbow>Minigame Vote</rainbow>] Results:"));
        String highest_game = "";
        Integer highest_value = 0;
        for (Map.Entry<String, Integer> entry : votes.entrySet()) {
          String key = entry.getKey();
          Integer value = entry.getValue();
          if (value > highest_value) {
            highest_value = value;
            highest_game = key;
          }
          Bukkit.broadcast(MiniMessage.miniMessage().deserialize(key + ": " + value));
        }
        Bukkit.broadcast(MiniMessage.miniMessage().deserialize("<rainbow>Highest Game:</rainbow> " + highest_game));

      }, (20 * seccondstowait));
    } else if (commandSender instanceof Player) {
      commandSender.sendMessage(
          MiniMessage.miniMessage()
              .deserialize("<red>Sorry, you don't have the permission minigamevoting.callvote.</red>"));
    } else {
      commandSender.sendMessage("Sorry, you don't have the permission minigamevoting.callvote.");
    }
    return true;
  }
}
