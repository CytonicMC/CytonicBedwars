package net.cytonic.cytonicbedwars.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dev.minestomunited.entrypoint.config.ConfigRegistry;
import lombok.Getter;
import net.hollowcube.schem.Schematic;
import net.hollowcube.schem.reader.SchematicReader;
import net.minestom.server.MinecraftServer;
import org.jetbrains.annotations.UnknownNullability;

import net.cytonic.cytonicbedwars.BedwarsConfig;
import net.cytonic.cytonicbedwars.FullbrightDimensionType;
import net.cytonic.cytonicbedwars.blockHandlers.BellBlockHandler;
import net.cytonic.cytonicbedwars.blockHandlers.ChestBlockHandler;
import net.cytonic.cytonicbedwars.blockHandlers.EnderChestBlockHandler;
import net.cytonic.cytonicbedwars.commands.DebugCommand;
import net.cytonic.cytonicbedwars.commands.ItemCommand;
import net.cytonic.cytonicbedwars.commands.MapBuilderCommand;
import net.cytonic.cytonicbedwars.config.BedwarsMap;
import net.cytonic.cytonicbedwars.config.BedwarsMode;
import net.cytonic.cytonicbedwars.config.TeamColor;
import net.cytonic.cytonicbedwars.game.Game;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.server.chat.ChatServiceImpl;
import net.cytonic.cytonicbedwars.server.playerList.PlayerListServiceImpl;
import net.cytonic.cytonicbedwars.server.sideboard.SideboardServiceImpl;
import net.cytonic.cytonicbedwars.utils.Events;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.server.AbstractCytosisServer;
import net.cytonic.cytosis.server.chat.ChatService;
import net.cytonic.cytosis.server.playerList.PlayerListService;
import net.cytonic.cytosis.server.sideboard.SideboardService;

public class BedwarsServer extends AbstractCytosisServer<BedwarsPlayer> {

    @UnknownNullability
    public static Schematic SPAWN_PLATFORM;
    @Getter
    private final Map<UUID, Game> games = new HashMap<>();
    private final ChatService<BedwarsPlayer> chatService;
    private final PlayerListService<BedwarsPlayer> playerListService;
    private final SideboardService<BedwarsPlayer> sideboardService;

    public BedwarsServer(ConfigRegistry registry) {
        super(registry, BedwarsPlayer::new);

        SPAWN_PLATFORM = loadSpawnPlatformSchematic();
        chatService = new ChatServiceImpl();
        playerListService = new PlayerListServiceImpl();
        sideboardService = new SideboardServiceImpl();
    }

    public Game getGame(UUID uuid) {
        return games.get(uuid);
    }

    @Override
    public ChatService<BedwarsPlayer> chatService() {
        return chatService;
    }

    @Override
    public PlayerListService<BedwarsPlayer> playerListService() {
        return playerListService;
    }

    @Override
    public SideboardService<BedwarsPlayer> sideboardService() {
        return sideboardService;
    }

    @Override
    public void onShutdown() {
    }

    private Schematic loadSpawnPlatformSchematic() {
        try (InputStream stream = getClass().getResourceAsStream("/schematics/spawn_platform.schem")) {
            if (stream == null) {
                throw new IllegalStateException("Spawn platform schematic file not found");
            }
            return SchematicReader.sponge().read(stream.readAllBytes());
        } catch (IOException e) {
            throw new IllegalStateException("An error occurred whilst trying to load spawn platform schematic");
        }
    }


    public void afterSetup() {
        Cytosis.init(this);

        FullbrightDimensionType.init();

        MinecraftServer.getBlockManager().registerHandler("minecraft:ender_chest", EnderChestBlockHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:chest", ChestBlockHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:bell", BellBlockHandler::new);

        registerCommands();

        BedwarsConfig config = getConfigOrThrow(BedwarsConfig.class);
        BedwarsMode mode = config.mode();
        List<BedwarsMap> maps = mode.getMaps();
        for (BedwarsMap map : maps) {
            Game game = new Game(map, mode);
            games.put(game.getId(), game);
        }

        Events.onPlayerGameModeRequest(event -> event.getPlayer().setGameMode(event.getRequestedGameMode()));
        Events.onAsyncPlayerConfiguration(
            event -> {
                event.getPlayer().setPermissionLevel(4);
                Game game = new ArrayList<>(games.values()).getFirst();
                event.setSpawningInstance(game.getWorld());
                ((BedwarsPlayer) event.getPlayer()).UNSAFE_joinGame(game.getId(), TeamColor.RED);
            });
    }

    private void registerCommands() {
        MinecraftServer.getCommandManager().register(new MapBuilderCommand());
        MinecraftServer.getCommandManager().register(new DebugCommand());
        MinecraftServer.getCommandManager().register(new ItemCommand());
    }
}
