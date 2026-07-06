package net.cytonic.cytonicbedwars.itemAbility;

import java.util.Map;

import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.tag.Tag;

import net.cytonic.cytonicbedwars.game.BedwarsWorld;
import net.cytonic.cytonicbedwars.itemAbility.fireball.FireballItemAbility;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;

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
