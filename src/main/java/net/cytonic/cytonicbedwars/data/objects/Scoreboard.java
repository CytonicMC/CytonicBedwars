package net.cytonic.cytonicbedwars.data.objects;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.Config;
import net.cytonic.cytonicbedwars.runnables.WaitingRunnable;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.sideboard.Sideboard;
import net.cytonic.cytosis.sideboard.SideboardCreator;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.text.Component;
import org.jooq.tools.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@NoArgsConstructor
public class Scoreboard implements SideboardCreator {

    @Override
    public Sideboard sideboard(CytosisPlayer player) {

        Sideboard sideboard = new Sideboard(player);
        sideboard.updateLines(lines(player));
        return sideboard;
    }

    @Override
    public List<Component> lines(CytosisPlayer player) {
        List<Component> list = new ArrayList<>();
        try {
            switch (CytonicBedWars.getGameManager().getGameState()) {
                case WAITING -> list = List.of(
                        topLine(),
                        Msg.mm(""),
                        Msg.mm("Map: <green>" + StringUtils.toUC(Config.mapName)),
                        Msg.mm("Players: <green>" + Cytosis.getOnlinePlayers().size() + "/" + Config.maxPlayers),
                        Msg.mm(""),
                        Msg.mm("Waiting..."),
                        Msg.mm(""),
                        Msg.mm("Mode: <green>" + StringUtils.toUC(Config.mode)),
                        Msg.mm("Version: <gray>" + CytonicBedWars.version),
                        Msg.mm(""),
                        Msg.yellow("www.cytonic.net")
                );

                case STARTING -> list = List.of(
                        topLine(),
                        Msg.mm(""),
                        Msg.mm("Map: <green>" + StringUtils.toUC(Config.mapName)),
                        Msg.mm("Players: <green>" + Cytosis.getOnlinePlayers().size() + "/" + Config.maxPlayers),
                        Msg.mm(""),
                        Msg.mm("Starting in <green>%ds", WaitingRunnable.getTimeLeft()),
                        Msg.mm(""),
                        Msg.mm("Mode: <green>" + StringUtils.toUC(Config.mode)),
                        Msg.mm("Version: <gray>" + CytonicBedWars.version),
                        Msg.mm(""),
                        Msg.yellow("www.cytonic.net")
                );

                case FROZEN -> list = List.of(
                        topLine(),
                        Msg.mm(""),
                        Msg.mm("Map: <green>" + StringUtils.toUC(Config.mapName)),
                        Msg.mm(""),
                        Msg.aqua("<bold>FROZEN"),
                        Msg.mm("Mode: <green>" + StringUtils.toUC(Config.mode)),
                        Msg.mm("Version: <gray>" + CytonicBedWars.version),
                        Msg.mm(""),
                        Msg.yellow("www.cytonic.net")
                );
                case PLAY -> {
                    List<Component> scoreboardArgs = new ArrayList<>();
                    scoreboardArgs.add(topLine());
                    scoreboardArgs.add(Msg.mm(""));
                    scoreboardArgs.add(Msg.mm("Time: coming soon:tm:"));
                    scoreboardArgs.add(Msg.mm(""));
                    CytonicBedWars.getGameManager().getTeamlist().forEach(team -> {
                        // todo: check if team is eliminated, or has final kills
                        String s = "";
                        s += team.prefix() + "<reset>" + team.displayName();
                        if (CytonicBedWars.getGameManager().getBeds().get(team)) {
                            s += " <green>✔";
                        } else {
                            if (CytonicBedWars.getGameManager().getPlayerTeams().get(team).isEmpty()) {
                                s += " <red>✘";
                            } else {
                                if (CytonicBedWars.getGameManager().getPlayerTeam(player.getUuid()).isPresent() && CytonicBedWars.getGameManager().getPlayerTeam(player.getUuid()).orElseThrow() != team) {
                                    s += " <grey>" + CytonicBedWars.getGameManager().getPlayerTeams().get(team).size();
                                }
                            }
                        }
                        if (CytonicBedWars.getGameManager().getPlayerTeam(player.getUuid()).isPresent() && CytonicBedWars.getGameManager().getPlayerTeam(player.getUuid()).orElseThrow() == team) {
                            s += " <gray>YOU";
                        }
                        scoreboardArgs.add(Msg.mm(s));
                    });
                    scoreboardArgs.add(Msg.mm(""));
                    scoreboardArgs.add(Msg.yellow("www.cytonic.net"));
                    list = scoreboardArgs;
                }
                case ENDED -> list = List.of(
                        topLine(),
                        Msg.mm(""),
                        Msg.mm("Map: <green>" + StringUtils.toUC(Config.mapName)),
                        Msg.mm(""),
                        Msg.mm("The game has ended!"),
                        Msg.mm(""),
                        Msg.mm("Mode: <green>" + StringUtils.toUC(Config.mode)),
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
        return Msg.grey("%s <dark_gray>%s", new SimpleDateFormat("M/d/y").format(Calendar.getInstance().getTime()), Cytosis.getRawID());
    }
}