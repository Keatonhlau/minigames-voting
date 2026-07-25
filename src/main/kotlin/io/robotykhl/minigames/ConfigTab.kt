package io.robotykhl.minigames

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class ConfigTab(private val main: Minigames) : TabCompleter {

    override fun onTabComplete(
        sender: CommandSender,
        cmd: Command,
        s: String,
        args: Array<String>
    ): List<String> {
        val results = mutableListOf<String>()
        if (args.size == 1) {
            results.add("add")
            results.add("delete")
            results.add("title")
            results.add("defaulttime")
        } else if (args.size == 2) {
            when (args[0]) {
                "add" -> results.add("Type What To Add")
                "delete" -> results.addAll(main.config.getStringList("Minigames"))
                "title" -> results.add("Type new title")
                "defaulttime" -> results.add("Type new default time")
            }
        }
        return results
    }
}