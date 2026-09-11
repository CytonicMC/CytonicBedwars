package net.cytonic.bedwars.itemAbility;

import net.minestom.server.event.player.PlayerUseItemEvent;

import net.cytonic.bedwars.game.BedwarsWorld;
import net.cytonic.bedwars.player.BedwarsPlayer;

public class LobbyRequestItemAbility implements ItemAbility {

    @Override
    public void use(BedwarsPlayer player, BedwarsWorld world, PlayerUseItemEvent event) {
        player.sendToLobby();
    }
}
