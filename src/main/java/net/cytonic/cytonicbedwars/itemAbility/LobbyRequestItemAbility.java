package net.cytonic.cytonicbedwars.itemAbility;

import net.minestom.server.event.player.PlayerUseItemEvent;

import net.cytonic.cytonicbedwars.game.BedwarsWorld;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;

public class LobbyRequestItemAbility implements ItemAbility {

    @Override
    public void use(BedwarsPlayer player, BedwarsWorld world, PlayerUseItemEvent event) {
        player.sendToLobby();
    }
}
