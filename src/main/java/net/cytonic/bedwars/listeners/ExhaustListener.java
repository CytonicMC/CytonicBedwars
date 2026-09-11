package net.cytonic.bedwars.listeners;

import io.github.togar2.pvp.events.PlayerExhaustEvent;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@SuppressWarnings("unused")
public class ExhaustListener {

    //todo @Listener
    private void onHunger(PlayerExhaustEvent event) {
        event.setCancelled(true);
    }
}
