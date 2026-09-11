package net.cytonic.bedwars.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dev.minestomunited.common.config.ConfigRegistry;
import io.github.togar2.pvp.MinestomPvP;
import io.github.togar2.pvp.feature.CombatFeatureSet;
import io.github.togar2.pvp.feature.CombatFeatures;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.hollowcube.schem.Schematic;
import net.hollowcube.schem.reader.SchematicReader;
import net.kyori.adventure.key.Key;
import net.minestom.server.MinecraftServer;
import org.jetbrains.annotations.UnknownNullability;

import net.cytonic.bedwars.FullbrightDimensionType;
import net.cytonic.bedwars.blockHandlers.BellBlockHandler;
import net.cytonic.bedwars.blockHandlers.ChestBlockHandler;
import net.cytonic.bedwars.blockHandlers.EnderChestBlockHandler;
import net.cytonic.bedwars.config.BedwarsConfig;
import net.cytonic.bedwars.config.BedwarsMap;
import net.cytonic.bedwars.config.BedwarsMode;
import net.cytonic.bedwars.events.BedwarsListeners;
import net.cytonic.bedwars.game.Game;
import net.cytonic.bedwars.player.BedwarsPlayer;
import net.cytonic.bedwars.server.chat.ChatServiceImpl;
import net.cytonic.bedwars.server.playerList.PlayerListServiceImpl;
import net.cytonic.bedwars.server.sideboard.SideboardServiceImpl;
import net.cytonic.bedwars.utils.BuildInfo;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.server.AbstractCytosisServer;
import net.cytonic.cytosis.server.actionBar.ActionBarService;
import net.cytonic.cytosis.server.chat.ChatService;
import net.cytonic.cytosis.server.playerList.PlayerListService;
import net.cytonic.cytosis.server.sideboard.SideboardService;
import net.cytonic.cytosis.utils.Msg;

@Getter
@Accessors(fluent = true)
public class BedwarsServer extends AbstractCytosisServer<BedwarsPlayer> {

    @UnknownNullability
    public static Schematic SPAWN_PLATFORM;
    private final BedwarsConfig config;
    @Getter
    private final Map<UUID, Game> games = new HashMap<>();
    private final ChatService<BedwarsPlayer> chatService;
    private final PlayerListService<BedwarsPlayer> playerListService;
    private final SideboardService<BedwarsPlayer> sideboardService;
    private final ActionBarService<BedwarsPlayer> actionBarService;

    public BedwarsServer(ConfigRegistry registry) {
        super(registry, BedwarsPlayer::new);

        config = super.getConfigOrThrow(BedwarsConfig.class);
        SPAWN_PLATFORM = loadSpawnPlatformSchematic();
        chatService = new ChatServiceImpl();
        playerListService = new PlayerListServiceImpl();
        sideboardService = new SideboardServiceImpl();
        actionBarService = new ActionBarService.Noop<>();
    }

    @Override
    public Key serverType() {
        return Key.key("bedwars", config.mode().name().toLowerCase());
    }

    @Override
    public String version() {
        return BuildInfo.BUILD_VERSION;
    }

    @Override
    public void onShutdown() {
    }

    public void afterSetup() {
        Cytosis.init(this);

        MinestomPvP.init(false, true);

        CombatFeatureSet modernVanilla = CombatFeatures.modernVanilla();
        Cytosis.CONTEXT.registerComponent(modernVanilla);
        MinecraftServer.getGlobalEventHandler().addChild(modernVanilla.createNode());

        FullbrightDimensionType.init();

        MinecraftServer.getBlockManager().registerHandler("minecraft:ender_chest", EnderChestBlockHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:chest", ChestBlockHandler::new);
        MinecraftServer.getBlockManager().registerHandler("minecraft:bell", BellBlockHandler::new);

        BedwarsMode mode = config.mode();
        List<BedwarsMap> maps = mode.getMaps();
        for (BedwarsMap map : maps) {
            Game game = new Game(map, mode);
            games.put(game.getId(), game);
        }

        BedwarsListeners.init(this);
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

    public Game getGame(UUID uuid) {
        if (!games.containsKey(uuid)) {
            throw new IllegalStateException("Game with id " + uuid + " does not exist");
        }
        return games.get(uuid);
    }

    public void swapGame(BedwarsPlayer player, Game newGame) {
        if (player.getGame().equals(newGame)) {
            player.sendMessage(Msg.whoops("You tried to swap to the game you are already in!"));
            return;
        }
        Game currentGame = player.getGame();

        player.sendMessage(Msg.success("Moved you to game '%s'!", newGame.getId()));
    }
}
