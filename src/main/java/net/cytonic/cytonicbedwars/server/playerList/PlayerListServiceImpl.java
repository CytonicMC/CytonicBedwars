package net.cytonic.cytonicbedwars.server.playerList;

import org.jspecify.annotations.Nullable;

import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.playerlist.PlayerlistCreator;
import net.cytonic.cytosis.server.playerList.PlayerListService;

public class PlayerListServiceImpl implements PlayerListService<BedwarsPlayer> {

    private static final PlayerList PLAYER_LIST = new PlayerList();

    @Override
    public boolean supportsPlayerList() {
        return true;
    }

    @Override
    public @Nullable PlayerlistCreator<BedwarsPlayer> creator() {
        return PLAYER_LIST;
    }
}
