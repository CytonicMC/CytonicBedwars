package net.cytonic.cytonicbedwars.data.objects;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.CytonicBedwarsSettings;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.sideboard.Sideboard;
import net.cytonic.cytosis.sideboard.SideboardCreator;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static net.cytonic.utils.MiniMessageTemplate.MM;

@NoArgsConstructor
public class Scoreboard implements SideboardCreator {
    @Override
    public Sideboard sideboard(Player player) {
        Sideboard sideboard = new Sideboard(player);
        sideboard.updateLines(lines(player));
        return sideboard;
    }

    @Override
    public List<Component> lines(Player player) {
        List<Component> list = new ArrayList<>();
        switch (CytonicBedWars.getGameManager().getGameState()) {
            case WAITING -> list = List.of(
                    MM."<gray>\{CytonicBedWars.version}",
                    MM."",
                    MM."Map: <green>\{StringUtils.capitalize(CytonicBedwarsSettings.mapName)}",
                    MM."Players: <green>(\{Cytosis.getOnlinePlayers().size()}/\{CytonicBedwarsSettings.maxPlayers})",
                    MM."",
                    MM."Waiting...",
                    MM."Mode: <green> \{StringUtils.capitalize(CytonicBedwarsSettings.mode)}",
                    MM."",
                    MM."<yellow>cytonic.net"
            );

            case FROZEN -> list = List.of(
                    MM."<grey>\{CytonicBedWars.version}",
                    MM."",
                    MM."Map: <green>\{StringUtils.capitalize(CytonicBedwarsSettings.mapName)}",
                    MM."",
                    MM."<aqua><bold>FROZEN",
                    MM."Mode: <green>\{StringUtils.capitalize(CytonicBedwarsSettings.mode)}",
                    MM."",
                    MM."<yellow>cytonic.net"
            );
            case PLAY -> {
                List<Component> scoreboardArgs = new ArrayList<>();
                scoreboardArgs.add(MM."<gray>\{CytonicBedWars.version}");
                scoreboardArgs.add(MM."");
                scoreboardArgs.add(MM."Time: %TIME%");
                scoreboardArgs.add(MM."Map: <green>\{StringUtils.capitalize(CytonicBedwarsSettings.mapName)}");
                scoreboardArgs.add(MM."");
                scoreboardArgs.add(MM."Mode: <green>\{StringUtils.capitalize(CytonicBedwarsSettings.mode)}");
                scoreboardArgs.add(MM."");
                CytonicBedWars.getGameManager().getTeamlist().forEach(team -> {
                    // todo: check if team is eliminated, or has final kills
                    String s = "";
                    s += STR."\{team.prefix()}<reset>\{team.displayName()}";
                    if (CytonicBedWars.getGameManager().getBeds().get(team)) s += "<green>✔";
                    else s += "<red>✘";
                    if (CytonicBedWars.getGameManager().getPlayerTeam(player.getUuid()).orElseThrow() == team) s += "<gray> YOU";
                    scoreboardArgs.add(MM."\{s}");
                });
                scoreboardArgs.add(MM."");
                scoreboardArgs.add(MM."<yellow>cytonic.net");
                list = scoreboardArgs;
            }
        }
        return list;
    }

    @Override
    public Component title(Player player) {
        return MM."<yellow><bold>Cytonic BEDWARS";
    }
}