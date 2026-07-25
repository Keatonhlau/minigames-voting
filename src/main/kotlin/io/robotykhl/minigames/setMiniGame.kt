package io.robotykhl.minigames

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class setMiniGame(private val main: Minigames) : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.size !== 2){
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Usage: /setMiniGame [clear/set] [name]"))
            return true
        } else {
            val mode = args[0].lowercase()
            val name = args[1]
            when (mode) {
                "clear" -> {
                    main.config.set("currentGame", "")
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>Cleared the current minigame!</red>"))
                }
                "set" -> {
                    main.config.set("currentGame", name)
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>Current game has been set!</green>"))
                }
                else -> {
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Usage: /setMiniGame [clear/set] [name]"))
                    return true
                }
            }
        }
        return true
    }
}