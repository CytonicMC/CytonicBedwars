package net.cytonic.cytonicbedwars.server.sideboard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.kyori.adventure.text.Component;

import net.cytonic.cytonicbedwars.game.Game;
import net.cytonic.cytonicbedwars.game.Team;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.runnables.GameRunnable;
import net.cytonic.cytonicbedwars.runnables.WaitingRunnable;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.sideboard.Sideboard;
import net.cytonic.cytosis.sideboard.SideboardCreator;
import net.cytonic.cytosis.utils.Msg;

public class SideboardImpl implements SideboardCreator<BedwarsPlayer> {

    @Override
    public Sideboard sideboard(BedwarsPlayer player) {
        Sideboard sideboard = new Sideboard(player);
        sideboard.updateLines(lines(player));
        return sideboard;
    }

    @Override
    public Component title(BedwarsPlayer player) {
        return Msg.yellow("<bold>Bedwars");
    }

    @Override
    public List<Component> lines(BedwarsPlayer player) {
        Game game = player.getGame();
        return lines(game, switch (game.getState()) {
            case WAITING -> List.of(
                Msg.mm("Map: <green>%s", game.getMap().getHumanName()),
                Msg.mm("Players: <green>%s/%s", game.getPlayers().size(), game.getMaxPlayers()),
                Msg.mm(""),
                Msg.mm("Waiting...")
            );

            case STARTING -> List.of(
                Msg.mm("Map: <green>%s", game.getMap().getHumanName()),
                Msg.mm("Players: <green>%s/%s", game.getPlayers().size(), game.getMaxPlayers()),
                Msg.mm(""),
                Msg.mm("Starting in <green>%ds", WaitingRunnable.getTimeLeft())
            );
            case ENDED -> List.of(
                Msg.mm("Map: <green>", game.getMap().getHumanName()),
                Msg.mm(""),
                Msg.mm("The game has ended!")
            );

            default -> {
                List<Component> lines = new ArrayList<>();

                lines.add(Msg.mm("%s in: <green>%s", game.getState().getNext().getDisplayName(),
                    GameRunnable.getFormattedTimeLeft()));
                lines.add(Msg.mm(""));

                for (Team team : game.getTeams().values()) {
                    Component component = team.getPrefix().appendSpace();
                    if (player.getBedwarsTeam().equals(team)) {
                        component = component.append(Msg.grey("YOU "));
                    }
                    if (!team.isAlive()) {
                        component = component.append(Msg.red("✘"));
                        lines.add(component);
                        continue;
                    }
                    if (team.hasBed()) {
                        component = component.append(Msg.green("✔"));
                    } else {
                        component = component.append(Msg.grey(String.valueOf(team.getAlivePlayers().size())));
                    }
                    lines.add(component);
                }

                yield lines;
            }
        });
    }

    private List<Component> lines(Game game, List<Component> lines) {
        List<Component> components = new ArrayList<>();
        components.add(
            Msg.grey("%s <dark_gray>%s", new SimpleDateFormat("M/d/yy").format(Calendar.getInstance().getTime()),
                Cytosis.CONTEXT.SERVER_ID));
        components.add(Msg.mm(""));
        components.addAll(lines);
        components.add(Msg.mm(""));
        Msg.mm("Mode: <green>%s", game.getMode().getHumanName());
        //todo
        Msg.mm("Version: <gray>0.1");
        Msg.mm("");
        Msg.yellow("www.cytonic.net");
        return components;
    }
}
