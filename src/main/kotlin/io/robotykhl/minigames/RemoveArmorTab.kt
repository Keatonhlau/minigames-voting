package io.robotykhl.minigames

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class RemoveArmorTab : TabCompleter {

    override fun onTabComplete(
        sender: CommandSender,
        cmd: Command,
        s: String,
        args: Array<String>
    ): List<String> {
        when (args.size) {
            1 -> {return listOf("helmet","chestplate", "leggings", "boots")}
            2 -> {
                var list = mutableListOf<String>()
                for (player in Bukkit.getOnlinePlayers()) {
                    list.add(player.name)
                }
                return list
            }
            else -> {return listOf()}
        }
    }

}