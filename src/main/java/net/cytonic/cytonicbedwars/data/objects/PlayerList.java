package net.cytonic.cytonicbedwars.data.objects;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.data.enums.PlayerRank;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.playerlist.Column;
import net.cytonic.cytosis.playerlist.PlayerListEntry;
import net.cytonic.cytosis.playerlist.PlayerListFavicon;
import net.cytonic.cytosis.playerlist.PlayerlistCreator;
import net.cytonic.cytosis.utils.CytosisPreferences;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.network.packet.server.play.PlayerInfoUpdatePacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
public class PlayerList implements PlayerlistCreator {

    @Override
    public List<Column> createColumns(CytosisPlayer player) {
        List<Column> columns = new ArrayList<>();

        List<PlayerListEntry> players = new ArrayList<>();

        for (CytosisPlayer p : Cytosis.getOnlinePlayers()) {
            Team team = !CytonicBedWars.getGameManager().getPlayerTeams().isEmpty() ? CytonicBedWars.getGameManager().getPlayerTeam(p.getUuid()).orElse(null) : null;
            if (p.getPreference(CytosisPreferences.VANISHED)) {
                if (!player.isStaff()) continue;
                players.add(new PlayerListEntry(p.getRank().getPrefix().color(NamedTextColor.GRAY)
                        .decorate(TextDecoration.STRIKETHROUGH, TextDecoration.ITALIC).append(p.getName()), p.getRank().ordinal(),
                        new PlayerInfoUpdatePacket.Property("textures", Objects.requireNonNull(p.getSkin()).textures(), p.getSkin().signature())));
                continue;
            }
            PlayerRank rank = Cytosis.getRankManager().getPlayerRank(p.getUuid()).orElse(PlayerRank.DEFAULT);
            players.add(new PlayerListEntry(team == null ? rank.getPrefix().append(p.getName()) : Msg.mm("%s<reset>%s", team.prefix(), p.getUsername()),
                    rank.ordinal(),
                    new PlayerInfoUpdatePacket.Property("textures", Objects.requireNonNull(p.getSkin()).textures(), p.getSkin().signature())))
            ;
        }

        Column playerCol = new Column(Msg.mm("<white><b>        Players    "), PlayerListFavicon.WHITE);
        if (players.size() >= 19) {
            int extra = players.size() - 19;
            players = new ArrayList<>(players.subList(0, 18));
            players.add(new PlayerListEntry(Msg.mm("<italic> + " + extra + " more"), 100));
        } else {
            playerCol.setEntries(new ArrayList<>(players));
            players.clear();
        }
        columns.add(playerCol);
        return columns;
    }

    /**
     * Creates the header for the player
     *
     * @param player the player for personalization
     * @return the component to be displayed as the header
     */
    @Override
    public Component header(CytosisPlayer player) {
        return Msg.mm("<aqua><b>Cytonic Bedwars");
    }

    /**
     * Creates the footer for the player
     *
     * @param player the player for personalization
     * @return the component to be displayed as the footer
     */
    @Override
    public Component footer(CytosisPlayer player) {
        return Msg.mm("<yellow><b>Playing on <red>MC.CYTONIC.NET");
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
