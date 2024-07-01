package net.cytonic.cytonicbedwars.managers;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.sideboard.Sideboard;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static net.cytonic.cytosis.utils.MiniMessageTemplate.MM;

public class ScoreboardManager {
    private final GameManager gameManager;
    private final CytonicBedWars plugin;
    public int taskID;
    private final HashMap<UUID, Sideboard> boards = new HashMap<>();

    public ScoreboardManager(GameManager gameManager, CytonicBedWars plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
    }

    public void init() {
        Cytosis.getOnlinePlayers().forEach(player -> {
            Sideboard board = new Sideboard(player);
            board.updateTitle(MM."<yellow><bold>Cytonic BEDWARS");
            board.updateLines(Component.text(""));
            boards.put(player.getUuid(), board);
        });

        taskID = MinecraftServer.getSchedulerManager().buildTask(this::updateBoards).delay(Duration.ofSeconds(1)).schedule().id();
    }

    public void addScoreboard(Player player) {
        Sideboard board = new Sideboard(player);
        board.updateTitle(MM."<yellow><bold>Cytonic BEDWARS");
        board.updateLines(Component.text(""));
        boards.put(player.getUuid(), board);
    }

    public void removeScoreboard(UUID player) {
        boards.remove(player);
    }

    public void updateBoards() {
        Cytosis.getOnlinePlayers().forEach(player -> {
            if (!boards.containsKey(player.getUuid())) {
                Sideboard board = new Sideboard(player);
                board.updateTitle(MM."<yellow><bold>Cytonic BEDWARS");
                board.updateLines(Component.text(""));
                boards.put(player.getUuid(), board);
            }
        });
        switch (gameManager.getGameState()) {
            case WAITING -> boards.forEach((_, board) -> board.updateLines(
                    MM."<gray>\{CytonicBedWars.version}",
                    MM."",
                    MM."Map: <green>map",
                    MM."Players: <green>(\{Cytosis.getOnlinePlayers().size()}/maxplayers)",
                    MM."",
                    MM."Waiting...",
                    MM."Mode: <green> mode",
                    MM."",
                    MM."<yellow>cytonic.net"

            ));
            case FROZEN -> boards.forEach((_, board) -> board.updateLines(
                    MM."<grey\{CytonicBedWars.version}>",
                    MM."",
                    MM."Map: <green>map",
                    MM."",
                    MM."<aqua><bold>FROZEN",
                    MM."Mode: <green>mode",
                    MM."",
                    MM."<yellow>cytonic.net"

            ));
            case PLAY -> boards.forEach((uuid, board) -> {
                List<Component> scoreboardArgs = new ArrayList<>();
                scoreboardArgs.add(MM."<gray>\{CytonicBedWars.version}");
                scoreboardArgs.add(MM."");
                scoreboardArgs.add(MM."Time: %TIME%");
                scoreboardArgs.add(MM."Map: <green>map");
                scoreboardArgs.add(MM."");
                plugin.getGameManager().getTeamlist().forEach(team -> {
                    // todo: check if team is eliminated, or has final kills
                    String s = "";
                    s += STR."\{team.prefix()}<reset>\{team.displayName()}";
                    if (plugin.getGameManager().getBeds().get(team)) s += "<green>✔";
                    else s += "<red>✘";
                    if (plugin.getGameManager().getPlayerTeam(uuid) == team) s += "<gray> YOU";
                    scoreboardArgs.add(MM."\{s}");
                });
                scoreboardArgs.add(MM."");
                scoreboardArgs.add(MM."<yellow>cytonic.net");
                board.updateLines(scoreboardArgs);
            });
        }
    }
}
