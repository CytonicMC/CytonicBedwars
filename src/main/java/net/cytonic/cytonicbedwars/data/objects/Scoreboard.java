package net.cytonic.cytonicbedwars.data.objects;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.CytonicBedwarsSettings;
import net.cytonic.cytonicbedwars.runnables.WaitingRunnable;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.sideboard.Sideboard;
import net.cytonic.cytosis.sideboard.SideboardCreator;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.text.Component;
import org.jooq.tools.StringUtils;

import java.util.ArrayList;
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
                        Msg.mm("<dark_grey><i>" + CytonicBedWars.version),
                        Msg.mm(""),
                        Msg.mm("Map: <green>" + StringUtils.toUC(CytonicBedwarsSettings.mapName)),
                        Msg.mm("Players: <green>(" + Cytosis.getOnlinePlayers().size() + "/" + CytonicBedwarsSettings.maxPlayers + ")"),
                        Msg.mm(""),
                        Msg.mm("Waiting..."),
                        Msg.mm("Mode: <green> " + StringUtils.toUC(CytonicBedwarsSettings.mode)),
                        Msg.mm(""),
                        Msg.mm("      <yellow>Cytonic.net")
                );

                case STARTING -> list = List.of(
                        Msg.mm("<dark_grey>" + CytonicBedWars.version),
                        Msg.mm(""),
                        Msg.mm("Map: <green>" + StringUtils.toUC(CytonicBedwarsSettings.mapName)),
                        Msg.mm("Players: <green>(" + Cytosis.getOnlinePlayers().size() + "/" + CytonicBedwarsSettings.maxPlayers + ")"),
                        Msg.mm(""),
                        Msg.mm("Starting in <red>" + WaitingRunnable.timeLeft),
                        Msg.mm("Mode: <green> " + StringUtils.toUC(CytonicBedwarsSettings.mode)),
                        Msg.mm(""),
                        Msg.mm("      <yellow>Cytonic.net")
                );

                case FROZEN -> list = List.of(
                        Msg.mm("<dark_grey><i>" + CytonicBedWars.version),
                        Msg.mm(""),
                        Msg.mm("Map: <green>" + StringUtils.toUC(CytonicBedwarsSettings.mapName)),
                        Msg.mm(""),
                        Msg.mm("<aqua><bold>FROZEN"),
                        Msg.mm("Mode: <green>" + StringUtils.toUC(CytonicBedwarsSettings.mode)),
                        Msg.mm(""),
                        Msg.mm("      <yellow>Cytonic.net")
                );
                case PLAY -> {
                    List<Component> scoreboardArgs = new ArrayList<>();
                    scoreboardArgs.add(Msg.mm("<dark_grey><i>" + CytonicBedWars.version));
                    scoreboardArgs.add(Msg.mm(""));
                    scoreboardArgs.add(Msg.mm("Time: idk"));
                    scoreboardArgs.add(Msg.mm("Map: <green>" + StringUtils.toUC(CytonicBedwarsSettings.mapName)));
                    scoreboardArgs.add(Msg.mm(""));
                    scoreboardArgs.add(Msg.mm("Mode: <green>" + StringUtils.toUC(CytonicBedwarsSettings.mode)));
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
                                if (CytonicBedWars.getGameManager().getPlayerTeam(player.getUuid()).orElseThrow() != team) {
                                    s += " <grey>" + CytonicBedWars.getGameManager().getPlayerTeams().get(team).size();
                                }
                            }
                        }
                        if (CytonicBedWars.getGameManager().getPlayerTeam(player.getUuid()).orElseThrow() == team) {
                            s += " <gray>YOU";
                        }
                        scoreboardArgs.add(Msg.mm(s));
                    });
                    scoreboardArgs.add(Msg.mm(""));
                    scoreboardArgs.add(Msg.mm("      <yellow>Cytonic.net"));
                    list = scoreboardArgs;
                }
                case ENDED -> {
                    List<Component> scoreboardArgs = new ArrayList<>();
                    scoreboardArgs.add(Msg.mm("<dark_grey><i>" + CytonicBedWars.version));
                    scoreboardArgs.add(Msg.mm(""));
                    scoreboardArgs.add(Msg.mm("<red>The game has ended."));
                    scoreboardArgs.add(Msg.mm("Map: <green>" + StringUtils.toUC(CytonicBedwarsSettings.mapName)));
                    scoreboardArgs.add(Msg.mm(""));
                    scoreboardArgs.add(Msg.mm("Mode: <green>" + StringUtils.toUC(CytonicBedwarsSettings.mode)));
                    scoreboardArgs.add(Msg.mm(""));
                    scoreboardArgs.add(Msg.mm("      <yellow>Cytonic.net"));
                    list = scoreboardArgs;
                }
            }
        } catch (Exception e) {
            Logger.error("error", e);
        }
        return list;
    }

    @Override
    public Component title(CytosisPlayer player) {
        return Msg.mm("<yellow><bold>Cytonic Bedwars");
    }
}