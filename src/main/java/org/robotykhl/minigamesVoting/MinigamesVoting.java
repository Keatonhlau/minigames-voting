package org.robotykhl.minigamesVoting;

import org.bukkit.plugin.java.JavaPlugin;

public final class MinigamesVoting extends JavaPlugin {

  @Override
  public void onEnable() {
    getConfig().options().copyDefaults();
    saveDefaultConfig();

    getCommand("startvoting").setExecutor(new CommandExec(this));
    getCommand("votingconfig").setExecutor(new ConfigExec(this));

    getCommand("startvoting").setTabCompleter(new CommandTab());
    getCommand("votingconfig").setTabCompleter(new ConfigTab(this));
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
