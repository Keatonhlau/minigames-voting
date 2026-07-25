package io.robotykhl.minigames

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class CommandTab : TabCompleter {

    override fun onTabComplete(
        sender: CommandSender,
        cmd: Command,
        s: String,
        args: Array<String>
    ): List<String> {
        return if (args.size == 1) {
            listOf("Secconds To Wait")
        } else {
            emptyList()
        }
    }
}