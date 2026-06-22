package net.cytonic.cytonicbedwars.server.playerList;

import java.util.List;

import net.kyori.adventure.text.Component;

import net.cytonic.cytonicbedwars.game.Team;
import net.cytonic.cytonicbedwars.game.Game;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.playerlist.Column;
import net.cytonic.cytosis.playerlist.PlayerListEntry;
import net.cytonic.cytosis.playerlist.PlayerListFavicon;
import net.cytonic.cytosis.playerlist.PlayerlistCreator;
import net.cytonic.cytosis.utils.Msg;

public class PlayerList implements PlayerlistCreator<BedwarsPlayer> {

    @Override
    public List<Column> createColumns(BedwarsPlayer player) {
        Game game = player.getGame();
        if (game == null || !game.isStarted()) {
            return List.of(new Column(Msg.mm("ugh empty column"), PlayerListFavicon.RED));
        }
        Team team = player.getBedwarsTeam();
        return List.of(new Column(Msg.mm("Hello!"), PlayerListFavicon.GREEN, List.of(
            new PlayerListEntry(Msg.gold("Bello!"), 0),
            new PlayerListEntry(Msg.purple("Bello!"), 0),
            new PlayerListEntry(Msg.aqua("Bello!"), 0)
        )));

//        List<PlayerListEntry> players = new ArrayList<>();
//        Cytosis.getOnlinePlayers().forEach(forPlayer -> {
//            if (!(forPlayer instanceof BedwarsPlayer p)) return;
//            if (Cytosis.CONTEXT.getComponent(GameManager.class).getPlayerTeam(p).isEmpty() && (
//                Cytosis.CONTEXT.getComponent(GameManager.class).getSpectators().contains(player.getUuid())
//                    || player.isStaff())) {
//                players.add(new PlayerListEntry(p.getRank().getPrefix().color(NamedTextColor.GRAY).append(p.getName()),
//                    p.getRank().ordinal(),
//                    new PlayerInfoUpdatePacket.Property("textures", Objects.requireNonNull(p.getSkin()).textures(),
//                        p.getSkin().signature())));
//                return;
//            }
//            if (p.isVanished()) {
//                if (!player.isStaff()) return;
//                players.add(new PlayerListEntry(p.getRank().getPrefix().color(NamedTextColor.GRAY)
//                    .decorate(TextDecoration.STRIKETHROUGH, TextDecoration.ITALIC).append(p.getName()),
//                    p.getRank().ordinal(),
//                    new PlayerInfoUpdatePacket.Property("textures", Objects.requireNonNull(p.getSkin()).textures(),
//                        p.getSkin().signature())));
//                return;
//            }
//            Team team = Cytosis.CONTEXT.getComponent(GameManager.class).getPlayerTeam(p).orElseThrow();
//            if (p.isNicked()) {
//                if (player.getUuid().equals(p.getUuid())) {
//                    players.add(new PlayerListEntry(Msg.mm("%s%s", team.getPrefix(), p.getTrueUsername()),
//                        p.getTrueRank().ordinal(),
//                        new PlayerInfoUpdatePacket.Property("textures",
//                            Objects.requireNonNull(p.getTrueSkin()).textures(), p.getTrueSkin().signature())));
//                    return;
//                }
//
//                if (player.isStaff()) {
//                    players.add(new PlayerListEntry(Msg.mm("%s %s \uD83C\uDFAD", team.getPrefix(), p.getUsername()),
//                        p.getRank().ordinal(),
//                        new PlayerInfoUpdatePacket.Property("textures",
//                            Objects.requireNonNull(p.getTrueSkin()).textures(), p.getTrueSkin().signature())));
//                    return;
//                }
//            }
//            players.add(new PlayerListEntry(Msg.mm("%s%s", team.getPrefix(), p.getUsername()),
//                p.getRank().ordinal(),
//                new PlayerInfoUpdatePacket.Property("textures", Objects.requireNonNull(p.getSkin()).textures(),
//                    p.getSkin().signature())));
//        });
//
//        Column playerCol = new Column(Msg.purple("<b>        Players    "), PlayerListFavicon.PURPLE);
//        playerCol.setEntries(players);
//        return List.of(playerCol);
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
