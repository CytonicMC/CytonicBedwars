package net.cytonic.cytonicbedwars.data.objects;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.cytonic.cytosis.playerlist.Column;
import net.cytonic.cytosis.playerlist.PlayerListEntry;
import net.cytonic.cytosis.playerlist.PlayerListFavicon;
import net.cytonic.cytosis.playerlist.PlayerlistCreator;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.network.packet.server.play.PlayerInfoUpdatePacket;
import org.apache.logging.log4j.util.Cast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
public class PlayerList implements PlayerlistCreator {

    @Override
    public List<Column> createColumns(CytosisPlayer player) {
        if (!CytonicBedWars.getGameManager().STARTED) {
            return List.of(PlayerList.PLAYER_COLUMN.apply(player));
        }
        List<PlayerListEntry> players = new ArrayList<>();
        Cytosis.getOnlinePlayers().stream().map(Cast::<BedwarsPlayer>cast).forEach(p -> {
            if (CytonicBedWars.getGameManager().getPlayerTeam(p).isEmpty() && (CytonicBedWars.getGameManager().getSpectators().contains(player.getUuid()) || player.isStaff())) {
                players.add(new PlayerListEntry(p.getRank().getPrefix().color(NamedTextColor.GRAY).append(p.getName()), p.getRank().ordinal(),
                        new PlayerInfoUpdatePacket.Property("textures", Objects.requireNonNull(p.getSkin()).textures(), p.getSkin().signature())));
                return;
            }
            if (p.isVanished()) {
                if (!player.isStaff()) return;
                players.add(new PlayerListEntry(p.getRank().getPrefix().color(NamedTextColor.GRAY)
                        .decorate(TextDecoration.STRIKETHROUGH, TextDecoration.ITALIC).append(p.getName()), p.getRank().ordinal(),
                        new PlayerInfoUpdatePacket.Property("textures", Objects.requireNonNull(p.getSkin()).textures(), p.getSkin().signature())));
                return;
            }
            Team team = CytonicBedWars.getGameManager().getPlayerTeam(p).orElseThrow();
            if (p.isNicked()) {
                if (player.getUuid().equals(p.getUuid())) {
                    players.add(new PlayerListEntry(Msg.mm("%s%s", team.getPrefix(), p.getTrueUsername()),
                            p.getTrueRank().ordinal(),
                            new PlayerInfoUpdatePacket.Property("textures", Objects.requireNonNull(p.getTrueSkin()).textures(), p.getTrueSkin().signature())));
                    return;
                }

                if (player.isStaff()) {
                    players.add(new PlayerListEntry(Msg.mm("%s %s \uD83C\uDFAD", team.getPrefix(), p.getUsername()),
                            p.getRank().ordinal(),
                            new PlayerInfoUpdatePacket.Property("textures", Objects.requireNonNull(p.getTrueSkin()).textures(), p.getTrueSkin().signature())));
                    return;
                }
            }
            players.add(new PlayerListEntry(Msg.mm("%s%s", team.getPrefix(), p.getUsername()),
                    p.getRank().ordinal(),
                    new PlayerInfoUpdatePacket.Property("textures", Objects.requireNonNull(p.getSkin()).textures(), p.getSkin().signature())));
        });

        Column playerCol = new Column(Msg.purple("<b>        Players    "), PlayerListFavicon.PURPLE);
        playerCol.setEntries(new ArrayList<>(players));
        return List.of(playerCol);
    }

    /**
     * Creates the header for the player
     *
     * @param player the player for personalization
     * @return the component to be displayed as the header
     */
    @Override
    public Component header(CytosisPlayer player) {
        return Msg.aqua("<b>Cytonic Bedwars");
    }

    /**
     * Creates the footer for the player
     *
     * @param player the player for personalization
     * @return the component to be displayed as the footer
     */
    @Override
    public Component footer(CytosisPlayer player) {
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
