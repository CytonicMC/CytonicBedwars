package net.cytonic.cytonicbedwars;

import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;

import net.cytonic.cytonicbedwars.blockHandlers.ChestBlockHandler;
import net.cytonic.cytonicbedwars.blockHandlers.EnderChestBlockHandler;
import net.cytonic.cytonicbedwars.commands.DebugCommand;
import net.cytonic.cytonicbedwars.commands.ItemCommand;
import net.cytonic.cytonicbedwars.managers.DatabaseManager;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.managers.GeneratorManager;
import net.cytonic.cytonicbedwars.managers.StatsManager;
import net.cytonic.cytonicbedwars.managers.WorldManager;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.managers.InstanceManager;
import net.cytonic.cytosis.plugins.CytosisPlugin;

@Getter
public final class CytonicBedWars implements CytosisPlugin {

    public static final String version = "0.1";

    @Override
    public void initialize() {
        Logger.info("Initializing CytonicBedWars");
        String worldName = System.getenv("WORLD_NAME");
        String worldType = System.getenv("WORLD_TYPE");
        String gameType = System.getenv("GAME_TYPE");
        if (worldName == null || worldType == null || gameType == null) {
            Logger.error("World name or type or game type is not set!");
            System.exit(1);
            return;
        }
//        Cytosis.CONTEXT.setServerGroup(new ServerGroup("bedwars", gameType));
        MinecraftServer.getConnectionManager().setPlayerProvider(BedwarsPlayer::new);
        MinecraftServer.getBlockManager()
            .registerHandler(Key.key("minecraft:ender_chest"), EnderChestBlockHandler::new);
        MinecraftServer.getBlockManager().registerHandler(Key.key("minecraft:chest"), ChestBlockHandler::new);
        Cytosis.CONTEXT.getComponent(InstanceManager.class).getExtraData(worldName, worldType)
            .whenComplete((extraData, throwable) -> {
                if (throwable != null) {
                    Logger.error("error", throwable);
                    return;
                }
                Config.importConfig(extraData);
                Logger.info("Registering commands");
                registerCommands();
                Cytosis.CONTEXT.registerComponent(new WorldManager());
                Cytosis.CONTEXT.registerComponent(new GameManager());
                Cytosis.CONTEXT.registerComponent(new DatabaseManager());
                Cytosis.CONTEXT.registerComponent(new GeneratorManager());
                Cytosis.CONTEXT.registerComponent(new StatsManager());
                Cytosis.CONTEXT.registerComponent(new ItemAbilityDispatcher());
            });
    }

    @Override
    public void shutdown() {
    }

    private void registerCommands() {
        Cytosis.CONTEXT.getComponent(CommandManager.class).register(new DebugCommand());
        Cytosis.CONTEXT.getComponent(CommandManager.class).register(new ItemCommand());
    }
}
