package net.cytonic.cytonicbedwars.server.playerList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import net.cytonic.cytonicbedwars.game.Game;
import net.cytonic.cytonicbedwars.game.Team;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.playerlist.Column;
import net.cytonic.cytosis.playerlist.PlayerListEntry;
import net.cytonic.cytosis.playerlist.PlayerListFavicon;
import net.cytonic.cytosis.playerlist.PlayerlistCreator;
import net.cytonic.cytosis.utils.Msg;

public class PlayerList implements PlayerlistCreator<BedwarsPlayer> {

    @Override
    public List<Column> createColumns(BedwarsPlayer player) {
        if (player.getKicking() != null) {
            return List.of(new Column(Msg.mm("You should not be able to see this."), PlayerListFavicon.BLACK));
        }
        Game game = player.getGame();
        if (!game.isStarted()) {
            return List.of(PLAYER_COLUMN.apply(player));
        }
        List<PlayerListEntry> players = new ArrayList<>();
        AtomicInteger index = new AtomicInteger();
        for (BedwarsPlayer teamPlayer : player.getBedwarsTeam().getPlayers()) {
            players.add(getEntry(teamPlayer, index));
        }

        for (Team team : game.getTeams().values()) {
            if (player.getBedwarsTeam().equals(team)) continue;
            for (BedwarsPlayer teamPlayer : team.getPlayers()) {
                players.add(getEntry(teamPlayer, index));
            }
        }

        return List.of(
            new Column(Msg.mm("<dark_purple><b>          Players     "), PlayerListFavicon.GREY, players));
    }

    private PlayerListEntry getEntry(BedwarsPlayer player, AtomicInteger index) {
        if (player.isSpectator()) {
            return new PlayerListEntry(
                Component.empty().color(NamedTextColor.GRAY).append(player.getBedwarsFormattedName()),
                index.getAndIncrement(),
                player.getHeadProperty()
            );
        }
        return new PlayerListEntry(
            player.getBedwarsFormattedName(),
            index.getAndIncrement(),
            player.getHeadProperty()
        );
    }

    /**
     * Creates the header for the player
     *
     * @param player the player for personalization
     * @return the component to be displayed as the header
     */
    @Override
    public Component header(BedwarsPlayer player) {
        return Msg.aqua("<b>Cytonic Bedwars");
    }

    /**
     * Creates the footer for the player
     *
     * @param player the player for personalization
     * @return the component to be displayed as the footer
     */
    @Override
    public Component footer(BedwarsPlayer player) {
        return Msg.yellow("<b>Playing on <red>MC.CYTONIC.NET");
    }

    /**
     * Gets the column count
     *
     * @return the number of columns, between 1 and 4 inclusive
     */
    @Override
    public int getColumnCount() {
        return 1;
    }
}
