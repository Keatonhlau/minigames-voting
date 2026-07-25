package io.robotykhl.minigames

import org.bukkit.plugin.java.JavaPlugin

class Minigames : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic

        getCommand("startvoting")!!.setExecutor(CommandExec(this))
        getCommand("startvoting")!!.tabCompleter = CommandTab()

        getCommand("votingconfig")!!.setExecutor(ConfigExec(this))
        getCommand("votingconfig")!!.tabCompleter = ConfigTab(this)

        getCommand("removearmor")!!.setExecutor(RemoveArmor())
        getCommand("removearmor")!!.tabCompleter = RemoveArmorTab()

        getCommand("getMiniGame")!!.setExecutor(getMiniGame(this))

        getCommand("setMiniGame")!!.setExecutor(setMiniGame(this))
        getCommand("setMiniGame")!!.tabCompleter = setMiniGameTab()

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
