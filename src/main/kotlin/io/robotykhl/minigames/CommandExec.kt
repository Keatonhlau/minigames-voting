@file:Suppress("UnstableApiUsage")

package io.robotykhl.minigames

import io.papermc.paper.dialog.Dialog
import io.papermc.paper.registry.data.dialog.ActionButton
import io.papermc.paper.registry.data.dialog.DialogBase
import io.papermc.paper.registry.data.dialog.action.DialogAction
import io.papermc.paper.registry.data.dialog.body.DialogBody
import io.papermc.paper.registry.data.dialog.type.DialogType
import net.kyori.adventure.text.event.ClickCallback
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@Suppress("unused")
class CommandExec(private val main: Minigames) : CommandExecutor {

    override fun onCommand(
        commandSender: CommandSender,
        command: Command,
        s: String,
        strings: Array<String>
    ): Boolean {
        val votes = mutableMapOf<String, Int>()

        if (commandSender.hasPermission("minigamevoting.callvote")) {
            val secondstowait: Int = if (strings.size == 1) {
                strings[0].toIntOrNull() ?: main.config.getInt("Default_Time")
            } else {
                main.config.getInt("Default_Time")
            }

            for (player in Bukkit.getOnlinePlayers()) {
                val buttons = mutableListOf<ActionButton>()

                buttons.add(
                    ActionButton.create(
                        MiniMessage.miniMessage().deserialize("Skip"),
                        MiniMessage.miniMessage().deserialize("Vote to skip this round"),
                        150,
                        DialogAction.customClick({ _, _ ->
                            player.closeDialog()
                        }, ClickCallback.Options.builder().build())
                    )
                )

                for (string in main.config.getStringList("Minigames")) {
                    buttons.add(
                        ActionButton.create(
                            MiniMessage.miniMessage().deserialize(string),
                            MiniMessage.miniMessage().deserialize("Click to Vote"),
                            150,
                            DialogAction.customClick({ _, _ ->
                                votes[string] = (votes[string] ?: 0) + 1
                                player.closeDialog()
                            }, ClickCallback.Options.builder().build())
                        )
                    )
                }

                val dialog = Dialog.create { builder ->
                    builder.empty()
                        .base(
                            DialogBase.builder(
                                MiniMessage.miniMessage().deserialize(main.config.getString("Title") ?: "")
                            )
                                .body(
                                    listOf(
                                        DialogBody.plainMessage(
                                            MiniMessage.miniMessage().deserialize(
                                                "$secondstowait seconds to vote from the time the ui opens."
                                            )
                                        )
                                    )
                                )
                                .build()
                        )
                        .type(DialogType.multiAction(buttons, null, 2))
                }
                player.showDialog(dialog)
            }

            val scheduler = main.server.scheduler
            scheduler.runTaskLater(main, Runnable {
                for (player in Bukkit.getOnlinePlayers()) {
                    player.closeDialog()
                }
                Bukkit.broadcast(
                    MiniMessage.miniMessage().deserialize("[" + main.config.getString("Title") + "] Results:")
                )
                var highestGame = ""
                var highestValue = 0
                for ((key, value) in votes) {
                    if (value > highestValue) {
                        highestValue = value
                        highestGame = key
                    }
                    Bukkit.broadcast(
                        MiniMessage.miniMessage()
                            .deserialize("[" + main.config.getString("Title") + "] $key: $value")
                    )
                }
                Bukkit.broadcast(
                    MiniMessage.miniMessage().deserialize(
                        "[" + main.config.getString("Title") + "] <rainbow>Highest Game:</rainbow> $highestGame"
                    )
                )
            }, (20 * secondstowait).toLong())
        } else if (commandSender is Player) {
            commandSender.sendMessage(
                MiniMessage.miniMessage()
                    .deserialize("<red>Sorry, you don't have the permission minigamevoting.callvote.</red>")
            )
        } else {
            commandSender.sendMessage("Sorry, you don't have the permission minigamevoting.callvote.")
        }
        return true
    }
}
