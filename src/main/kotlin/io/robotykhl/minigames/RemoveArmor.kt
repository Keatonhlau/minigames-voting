package io.robotykhl.minigames

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender

class RemoveArmor : CommandExecutor {
    override fun onCommand(
        commandSender: CommandSender,
        command: Command,
        s: String,
        strings: Array<String>
    ): Boolean {
        if (commandSender is ConsoleCommandSender) {
            val slot = strings[0]
            val player = Bukkit.getPlayer(strings[1])
            if (player == null) commandSender.sendMessage(MiniMessage.miniMessage().deserialize("<red>That player is offline!</red>"))
            when (slot){
                "helmet" -> {
                    player?.inventory?.setHelmet(null)
                }
                "chestplate" -> {
                    player?.inventory?.setChestplate(null)
                }
                "leggings" -> {
                    player?.inventory?.setLeggings(null)
                }
                "boots" -> {
                    player?.inventory?.setBoots(null)
                }
                else -> {
                    commandSender.sendMessage("<red>Usage: /removearmor [helmet/chestplate/leggings/boots] [player]")
                }
            }
        } else {
            commandSender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Sorry, this command must be ran as console or command block!</red>"))
        }
        return true
    }
}