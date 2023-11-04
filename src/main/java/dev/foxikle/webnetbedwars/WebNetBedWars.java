package dev.foxikle.webnetbedwars;

import dev.foxikle.webnetbedwars.listeners.DamageListener;
import dev.foxikle.webnetbedwars.managers.GameManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class WebNetBedWars extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        File file = new File("plugins/WebNetBedWars/config.yml");
        if(!file.exists())
            this.saveResource("config.yml", false);

        gameManager = new GameManager(this);
        registerCommands();
        registerListeners();
        gameManager.setup();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new DamageListener(this), this);
    }

    private void registerCommands(){
        getCommand("debug").setExecutor(new DebugCommand(this));
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
