package net.cytonic.cytonicbedwars;

import lombok.Getter;
import net.cytonic.cytonicbedwars.blockHandlers.ChestBlockHandler;
import net.cytonic.cytonicbedwars.blockHandlers.EnderChestBlockHandler;
import net.cytonic.cytonicbedwars.commands.DebugCommand;
import net.cytonic.cytonicbedwars.commands.ItemCommand;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.data.objects.ServerGroup;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.managers.InstanceManager;
import net.cytonic.cytosis.plugins.CytosisPlugin;
import net.kyori.adventure.key.Key;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;

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
            MinecraftServer.stopCleanly();
            return;
        }
        Cytosis.CONTEXT.setServerGroup(new ServerGroup("bedwars", gameType));
        MinecraftServer.getConnectionManager().setPlayerProvider(BedwarsPlayer::new);
        MinecraftServer.getBlockManager().registerHandler(Key.key("minecraft:ender_chest"), EnderChestBlockHandler::new);
        MinecraftServer.getBlockManager().registerHandler(Key.key("minecraft:chest"), ChestBlockHandler::new);
        Cytosis.CONTEXT.getComponent(InstanceManager.class).getExtraData(worldName, worldType).whenComplete((extraData, throwable) -> {
            if (throwable != null) {
                Logger.error("error", throwable);
                return;
            }
            Config.importConfig(extraData);
            Logger.info("Loading game manager");
            Cytosis.CONTEXT.registerComponent(new GameManager());
            Cytosis.CONTEXT.getComponent(GameManager.class).setup();
            Logger.info("Registering commands");
            registerCommands();
            Logger.info("Registering listeners");
        });
    }

    @Override
    public void shutdown() {
        Cytosis.CONTEXT.getComponent(GameManager.class).cleanup();
    }

    private void registerCommands() {
        Cytosis.CONTEXT.getComponent(CommandManager.class).register(new DebugCommand());
        Cytosis.CONTEXT.getComponent(CommandManager.class).register(new ItemCommand());
    }
}
