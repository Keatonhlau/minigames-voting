package io.robotykhl.minigames

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class setMiniGameTab : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String?>? {
        when (args.size) {
            1 -> {
                return listOf("clear", "set")
            }
            2 -> {
                when (args[0]) {
                    "clear" -> {
                        return listOf()
                    }
                    "set" -> {
                        return listOf("Type what to set to!")
                    }
                    else -> {
                        return listOf()
                    }
                }
            }
            else -> {
                return listOf()
            }
        }
    }
}