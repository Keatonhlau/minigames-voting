package io.robotykhl.minigames

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class getMiniGame(private val main: Minigames) : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val currentGame = main.config.getString("currentGame")
        if (currentGame == null || currentGame == "") {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>There are no current minigames!</red>"))
        } else {
            sender.sendMessage("Current game: $currentGame")
        }
        return true;
    }
}