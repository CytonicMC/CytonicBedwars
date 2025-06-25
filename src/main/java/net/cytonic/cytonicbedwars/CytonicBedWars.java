package net.cytonic.cytonicbedwars;

import lombok.Getter;
import net.cytonic.cytonicbedwars.commands.DebugCommand;
import net.cytonic.cytonicbedwars.commands.ItemCommand;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.data.objects.ServerGroup;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.plugins.CytosisPlugin;
import net.minestom.server.MinecraftServer;

@Getter
public final class CytonicBedWars implements CytosisPlugin {

    public static final String version = "0.1";
    @Getter
    private static GameManager gameManager;

    @Override
    public void initialize() {
        Logger.info("Initializing CytonicBedWars");
        String worldName = System.getenv("WORLD_NAME");
        String worldType = System.getenv("WORLD_TYPE");
        String gameType = System.getenv("GAME_TYPE");
        if (worldName == null || worldType == null || gameType == null) {
            Logger.error("World name or type or game type is not set!");
            MinecraftServer.stopCleanly();
            return;
        }
        Cytosis.setServerGroup(new ServerGroup("bedwars", gameType, false));
        MinecraftServer.getConnectionManager().setPlayerProvider(BedwarsPlayer::new);
        Cytosis.getInstanceManager().getExtraData(worldName, worldType).whenComplete((extraData, throwable) -> {
            if (throwable != null) {
                Logger.error("error", throwable);
                return;
            }
            Config.importConfig(extraData);
            Logger.info("Loading game manager");
            gameManager = new GameManager();
            gameManager.setup();
            Logger.info("Registering commands");
            registerCommands();
            Logger.info("Registering listeners");
        });
    }

    @Override
    public void shutdown() {
        gameManager.cleanup();
    }

    private void registerCommands() {
        Cytosis.getCommandManager().register(new DebugCommand());
        Cytosis.getCommandManager().register(new ItemCommand());
    }
}
