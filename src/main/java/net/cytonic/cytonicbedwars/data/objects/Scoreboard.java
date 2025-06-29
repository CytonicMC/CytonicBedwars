package net.cytonic.cytonicbedwars.data.objects;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.Config;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.runnables.GameRunnable;
import net.cytonic.cytonicbedwars.runnables.WaitingRunnable;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.sideboard.Sideboard;
import net.cytonic.cytosis.sideboard.SideboardCreator;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.text.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class Scoreboard implements SideboardCreator {

    @Override
    public Sideboard sideboard(CytosisPlayer player) {

        Sideboard sideboard = new Sideboard(player);
        sideboard.updateLines(lines(player));
        return sideboard;
    }

    @Override
    public List<Component> lines(CytosisPlayer p) {
        if (!(p instanceof BedwarsPlayer player)) return List.of();
        List<Component> list = new ArrayList<>();
        try {
            switch (CytonicBedWars.getGameManager().getGameState()) {
                case WAITING -> list = List.of(
                        topLine(),
                        Msg.mm(""),
                        Msg.mm("Map: <green>" + Config.mapName),
                        Msg.mm("Players: <green>" + Cytosis.getOnlinePlayers().size() + "/" + Config.maxPlayers),
                        Msg.mm(""),
                        Msg.mm("Waiting..."),
                        Msg.mm(""),
                        Msg.mm("Mode: <green>" + Config.mode),
                        Msg.mm("Version: <gray>" + CytonicBedWars.version),
                        Msg.mm(""),
                        Msg.yellow("www.cytonic.net")
                );

                case STARTING -> list = List.of(
                        topLine(),
                        Msg.mm(""),
                        Msg.mm("Map: <green>" + Config.mapName),
                        Msg.mm("Players: <green>" + Cytosis.getOnlinePlayers().size() + "/" + Config.maxPlayers),
                        Msg.mm(""),
                        Msg.mm("Starting in <green>%ds", WaitingRunnable.getTimeLeft()),
                        Msg.mm(""),
                        Msg.mm("Mode: <green>" + Config.mode),
                        Msg.mm("Version: <gray>" + CytonicBedWars.version),
                        Msg.mm(""),
                        Msg.yellow("www.cytonic.net")
                );

                case FROZEN -> list = List.of(
                        topLine(),
                        Msg.mm(""),
                        Msg.mm("Map: <green>" + Config.mapName),
                        Msg.mm(""),
                        Msg.aqua("<bold>FROZEN"),
                        Msg.mm("Mode: <green>" + Config.mode),
                        Msg.mm("Version: <gray>" + CytonicBedWars.version),
                        Msg.mm(""),
                        Msg.yellow("www.cytonic.net")
                );
                case PLAY, DIAMOND_2, EMERALD_2, DIAMOND_3, EMERALD_3, BED_DESTRUCTION, SUDDEN_DEATH -> {
                    List<Component> scoreboardArgs = new ArrayList<>();
                    scoreboardArgs.add(topLine());
                    scoreboardArgs.add(Msg.mm(""));
                    scoreboardArgs.add(Msg.mm("%s in: <green>%s", CytonicBedWars.getGameManager().getGameState().getNext().getDisplayName(), GameRunnable.getFormattedTimeLeft()));
                    scoreboardArgs.add(Msg.mm(""));
                    GameManager gameManager = CytonicBedWars.getGameManager();
                    Optional<Team> playerTeam = gameManager.getPlayerTeam(player);
                    Config.teams.values().forEach(team -> {
                        String s = team.getPrefix() + "<reset>" + team.getDisplayName();
                        if (gameManager.getTeamFromColor(team.getColor()).isPresent() && gameManager.getTeamFromColor(team.getColor()).get().isAlive()) {
                            if (playerTeam.isPresent() && playerTeam.get().equals(team)) {
                                scoreboardArgs.add(Msg.mm(s + " <gray>YOU"));
                                return;
                            }
                            if (team.hasBed()) {
                                scoreboardArgs.add(Msg.mm(s + " <green>✔"));
                            } else {
                                scoreboardArgs.add(Msg.mm(s + " <grey>" + team.getAlivePlayers().size()));
                            }
                        } else {
                            scoreboardArgs.add(Msg.mm(s + " <red>✘"));
                        }
                    });
                    scoreboardArgs.add(Msg.mm(""));
                    scoreboardArgs.add(Msg.yellow("www.cytonic.net"));
                    list = scoreboardArgs;
                }
                case ENDED -> list = List.of(
                        topLine(),
                        Msg.mm(""),
                        Msg.mm("Map: <green>" + Config.mapName),
                        Msg.mm(""),
                        Msg.mm("The game has ended!"),
                        Msg.mm(""),
                        Msg.mm("Mode: <green>" + Config.mode),
                        Msg.mm("Version: <gray>" + CytonicBedWars.version),
                        Msg.mm(""),
                        Msg.yellow("www.cytonic.net")
                );
            }
        } catch (Exception e) {
            Logger.error("error", e);
        }
        return list;
    }

    @Override
    public Component title(CytosisPlayer player) {
        return Msg.yellow("<bold>Bedwars");
    }

    private Component topLine() {
        return Msg.grey("%s <dark_gray>%s", new SimpleDateFormat("M/d/yy").format(Calendar.getInstance().getTime()), Cytosis.getRawID());
    }
}