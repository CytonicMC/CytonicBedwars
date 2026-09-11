package net.cytonic.bedwars.itemAbility;

import java.util.Map;

import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.tag.Tag;

import net.cytonic.bedwars.game.BedwarsWorld;
import net.cytonic.bedwars.itemAbility.fireball.FireballItemAbility;
import net.cytonic.bedwars.player.BedwarsPlayer;

public interface ItemAbility {

    Tag<String> TAG = Tag.String("item_ability");
    Map<String, ItemAbility> REGISTRY = Map.of(
        "fireball", new FireballItemAbility(),
        "tnt", new TntItemAbility(),
        "lobby_request", new LobbyRequestItemAbility(),
        "spectator_compass", new SpectatorCompassItemAbility(),
        "spectator_speed", new SpectatorSpeedItemAbility()
    );

    default void use(BedwarsPlayer player, BedwarsWorld world, PlayerUseItemEvent event) {
    }

    default void place(BedwarsPlayer player, BedwarsWorld world, PlayerBlockPlaceEvent event) {
    }
}
