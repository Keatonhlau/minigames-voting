package io.robotykhl.minigames

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ConfigExec(private val main: Minigames) : CommandExecutor {

    override fun onCommand(
        sender: CommandSender,
        cmd: Command,
        s: String,
        args: Array<String>
    ): Boolean {
        if (sender is Player) {
            if (sender.hasPermission("minigamevoting.config")) {
                if (args.size == 2) {
                    val minigames = main.config.getStringList("Minigames")
                    when (args[0]) {
                        "add" -> {
                            minigames.add(args[1])
                            sender.sendMessage(
                                MiniMessage.miniMessage().deserialize("Minigame '${args[1]}' added!")
                            )
                        }

                        "delete" -> {
                            if (minigames.contains(args[1])) {
                                minigames.remove(args[1])
                                sender.sendMessage(
                                    MiniMessage.miniMessage().deserialize("Minigame '${args[1]}' removed!")
                                )
                            } else {
                                sender.sendMessage(
                                    MiniMessage.miniMessage()
                                        .deserialize("Minigame '${args[1]}' doesn't exist!")
                                )
                                return true
                            }
                        }

                        "title" -> {
                            main.config.set("Title", args[1])
                        }

                        "defaulttime" -> {
                            main.config.set("Default_Time", args[1].toInt())
                        }

                        else -> {
                            sender.sendMessage(
                                MiniMessage.miniMessage()
                                    .deserialize("<red>Incorrect Usage!</red> Usage: /votingconfig add/remove optionname")
                            )
                        }
                    }
                    main.config.set("Minigames", minigames)
                    main.saveConfig()
                } else {
                    sender.sendMessage(
                        MiniMessage.miniMessage()
                            .deserialize("<red>Incorrect Usage!</red> Usage: /votingconfig add/remove optionname")
                    )
                    return true
                }
            } else {
                sender.sendMessage(
                    MiniMessage.miniMessage()
                        .deserialize("<red>Sorry, you don't have the permission minigamevoting.config.</red>")
                )
            }
        } else {
            sender.sendPlainMessage("This must be sent by a player.")
        }
        return true
    }
}